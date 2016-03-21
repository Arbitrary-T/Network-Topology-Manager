package controllers;

import models.*;
import views.IntField;
import views.MainView;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.print.PrinterException;
import java.util.ArrayList;

/**
 * Created by Talal Mahmood on 05/03/2016.
 * SID 5296251
 * Coventry University
 */

public class MainViewController implements MainViewListener, ConnectionListener
{
    private MainView mainView;
    private Connection clientConnection;

    /**
     * The Controller class, responsible for aggregating data from the model, and providing it to the view.
     * @param mainView MainView reference, the view to be controlled.
     * @param clientConnection Connection reference, the model that provides the data.
     */
    public MainViewController(MainView mainView, Connection clientConnection)
    {
        //Initialise variables, and activate agents (listeners).
        this.mainView = mainView;
        this.clientConnection = clientConnection;
        this.mainView.activateAgent(this);
        this.clientConnection.activateAgent(this);
        clientConnection.setData("Refresh", null); //Communicate with server in order to send the latest database.
    }

    /**
     * Invoked when the clear button is clicked, the method simply clears the JTextField(s).
     * @param textFields representing the user's input
     */
    @Override
    public void onClearButtonClick(JTextField[] textFields)
    {
        for (JTextField textField : textFields)
        {
            textField.setText("");
        }
    }

    /**
     * Invoked when the save button is clicked, the method converts the user's input to a Network object through the
     * networkObjectParser method and sends the object to the server either to add or modify the data.
     * @param textFields representing the user's input
     */
    @Override
    public void onSaveButtonClick(JTextField[] textFields)
    {
        Network userInput = networkObjectParser(textFields);
        ArrayList<Network> networks = clientConnection.getNetworkArrayList();
        for(Network network:networks)
        {
            if (userInput != null && userInput.getId() == network.getId())
            {
                if (clientConnection.getSocket() != null)
                {
                    clientConnection.setData("Modify", userInput);
                    userInput = null;
                    return;
                }
            }
        }
        if(clientConnection.getSocket() != null && userInput != null)
        {
            clientConnection.setData("Add", userInput);
        }
    }

    /**
     * Invoked when the delete button is clicked, the method converts the user's input into a Network object via the
     * networkObjectParser method and sends the object to the server in order to delete it.
     * @param textFields representing the user's input
     */
    @Override
    public void onDeleteButtonClick(JTextField[] textFields)
    {
        Network userInput = networkObjectParser(textFields);
        if(clientConnection.getSocket() != null && userInput != null)
        {
            clientConnection.setData("Delete", userInput);
            onClearButtonClick(textFields);
        }
    }

    /**
     * Invoked when the print button is clicked, the method checks if the Table view has any data, if so, it tries to
     * print the records, else it will display a (logical) error message to the user.
     * @param contextTable representing the table, where the data to be printed is populated
     */
    @Override
    public void onPrintButtonClick(JTable contextTable)
    {
        if(contextTable.getModel().getRowCount() > 0)
        {
            try
            {
                contextTable.print();
            }
            catch (PrinterException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            mainView.alertMessage("Print error!", "The table is empty, please add data before printing.");
        }
    }

    /**
     * Invoked when the text in the filter JTextField changes, the query is then processed in order to determine the
     * sort the table.
     * @param query the user's query for example 1:>5, or 4:10 and so on...
     * @param table representing the table, where the data to be sorted is populated
     */
    @Override
    public void onFilterTextChange(String query, JTable table)
    {
        TableRowSorter<NetworkTableModel> tableSorter = (TableRowSorter<NetworkTableModel>) table.getRowSorter();

        if(query.matches("^[1-4]:>\\d+"))
        {
            tableSorter.setRowFilter(setupRowFilter(RowFilter.ComparisonType.AFTER, query, 3));
        }
        else if(query.matches("^[1-4]:<\\d+"))
        {
            tableSorter.setRowFilter(setupRowFilter(RowFilter.ComparisonType.BEFORE, query, 3));
        }
        else if(query.matches("^[1-4]:\\d+"))
        {
            tableSorter.setRowFilter(setupRowFilter(RowFilter.ComparisonType.EQUAL, query, 2));
        }
        else if(query.matches("^[5-7]:[a-zA-Z0-9]+"))
        {
            try
            {
                tableSorter.setRowFilter(RowFilter.regexFilter("^(?i)" + query.substring(2, query.length()), Integer.parseInt(query.substring(0, 1))-1));
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            tableSorter.setRowFilter(null);
        }
    }

    /**
     * Invoked when the JTable's row focus changes, the method simply loads the new values in the JTextField(s)
     * Note the use of the Table's model in order to set the correct values in case the table is filtered.
     * @param table representing the table where the data is populated
     * @param textFields  representing the user's input
     * @param tableModel the table model where the table's data resides.
     */
    @Override
    public void onSelectedIndexChange(JTable table, JTextField[] textFields, TableModel tableModel)
    {
        int selectedRowInTable = table.getSelectedRow();
        if(selectedRowInTable != -1)
        {
            int selectedRowInModel = table.getRowSorter().convertRowIndexToModel(selectedRowInTable);
            for(int i = 0; i < 7; i++)
            {
                textFields[i].setText(tableModel.getValueAt(selectedRowInModel, i)+"");
            }
        }
    }

    /**
     * Invoked when the thread polling the server receives a new update, the method simply replaces the data in the
     * list model with the updated data from the server.
     *
     * @param updatedList a list of networks from the server
     */
    @Override
    public void notifyUpdate(ArrayList<Network> updatedList)
    {
        mainView.setNetworkDefaultListModel(updatedList);
    }

    /**
     * A method that sets up a row filter depending on the parameters passed.
     * @param comparisonType the type of comparison in terms of the row filter
     * @param query the user's query
     * @param index the index of the column to be filtered
     * @return returns a RowFilter specific to the user's query
     */
    private RowFilter setupRowFilter(RowFilter.ComparisonType comparisonType, String query, int index)
    {
        int userInput = 0;
        int columnIndex = 0;
        try
        {
            userInput = Integer.parseInt(query.substring(index, query.length()));
            columnIndex = Integer.parseInt(query.substring(0,1))-1;
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return RowFilter.numberFilter(comparisonType, userInput, columnIndex);
    }

    /**
     * A method that returns a Network object from data inputted into the JTextField(s) by the user.
     * @param textFields representing the user's input
     * @return returns a Network object
     */
    private Network networkObjectParser(JTextField[] textFields)
    {
        for(JTextField textField : textFields)
        {
            if(textField.getText().isEmpty())
            {
                mainView.alertMessage("Invalid Input!", "You must provide input to all fields!");
                return null;
            }
        }
        IntField networkID = (IntField) textFields[0];
        IntField numberOfNodes = (IntField) textFields[1];
        IntField numberOfHubs = (IntField) textFields[2];
        IntField numberOfSwitches = (IntField) textFields[3];
        return new Network(networkID.getValue(), numberOfNodes.getValue(),numberOfHubs.getValue(),numberOfSwitches.getValue(), textFields[4].getText(),textFields[5].getText(), textFields[6].getText());
    }
}