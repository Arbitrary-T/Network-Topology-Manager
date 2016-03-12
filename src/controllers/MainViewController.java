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
 * Created by T on 05/03/2016.
 */

public class MainViewController implements MainViewListener, ConnectionListener
{
    private MainView mainView;
    private Connection clientConnection;

    public MainViewController(MainView mainView, Connection clientConnection)    //model as well
    {
        this.mainView = mainView;
        this.clientConnection = clientConnection;
        this.mainView.activateAgent(this);
        this.clientConnection.activateAgent(this);
        clientConnection.setData("Refresh", null);
    }

    /**
     * Invoked when the clear button is clicked, the method simply clears the JTextField(s).
     * @param textFields
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
     * @param textFields
     */
    @Override
    public void onSaveButtonClick(JTextField[] textFields)
    {
        ArrayList<Network> networks = clientConnection.getNetworkArrayList();
        Network userInput = networkObjectParser(textFields);
        for(Network network:networks)
        {
            if (userInput != null && userInput.getId() == network.getId())
            {
                if (clientConnection.getSocket() != null)
                {
                    clientConnection.setData("Modify", userInput);
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
     * @param textFields
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
     * @param contextTable
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
     * @param query
     * @param table
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
            tableSorter.setRowFilter(RowFilter.regexFilter("^(?i)" + query.substring(2, query.length()), Integer.parseInt(query.substring(0, 1))-1));
        }
        else
        {
            tableSorter.setRowFilter(null);
        }
    }

    /**
     * Invoked when the JTable's row focus changes, the method simply loads the new values in the JTextField(s)
     * Note the use of the Table's model in order to set the correct values in case the table is filtered.
     * @param table
     * @param textFields
     * @param tableModel
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
     * @param updatedList
     */
    @Override
    public void notifyUpdate(ArrayList<Network> updatedList)
    {
        mainView.setNetworkDefaultListModel(updatedList);
    }

    /**
     * A method that sets up a row filter depending on the parameters passed.
     * @param comparisonType
     * @param query
     * @param index
     * @return
     */
    private RowFilter setupRowFilter(RowFilter.ComparisonType comparisonType, String query, int index)
    {
        int userInput = Integer.parseInt(query.substring(index, query.length()));
        int columnIndex = Integer.parseInt(query.substring(0,1))-1;
        return RowFilter.numberFilter(comparisonType, userInput, columnIndex);
    }

    /**
     * A method that returns a Network object from data inputted into the JTextField(s) by the user.
     * @param textFields
     * @return
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