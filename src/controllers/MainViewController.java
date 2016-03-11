package controllers;

import models.Connection;
import models.ConnectionListener;
import models.MainViewListener;
import models.Network;
import views.IntField;
import views.MainView;
import javax.swing.*;
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

    @Override
    public void onClearButtonClick(JTextField[] textFields)
    {
        for (JTextField textField : textFields)
        {
            textField.setText("");
        }
    }

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

    @Override
    public void onPrintButtonClick(JTable contextTable)
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

    @Override
    public void onFilterButtonClick(String query)
    {
        if(query.matches("^[1-7]:[a-zA-Z0-9]+"))
        {
            System.out.println("Matches");
            System.out.println(query.substring(2, query.length()));
        }
        else
        {
            System.out.println("Does not match");
        }
    }

    private Network networkObjectParser(JTextField[] textFields)
    {
        for(JTextField textField : textFields)
        {
            if(textField.getText().isEmpty())
            {
                mainView.alertMessage("Invalid Input!", "You must provide input to all fields before saving!");
                return null;
            }
        }
        IntField networkID = (IntField) textFields[0];
        IntField numberOfNodes = (IntField) textFields[1];
        IntField numberOfHubs = (IntField) textFields[2];
        IntField numberOfSwitches = (IntField) textFields[3];
        return new Network(networkID.getValue(), numberOfNodes.getValue(),numberOfHubs.getValue(),numberOfSwitches.getValue(), textFields[4].getText(),textFields[5].getText(), textFields[6].getText());
    }

    @Override
    public void notifyUpdate(ArrayList<Network> updatedList)
    {
        mainView.setNetworkListAdapterListModel(updatedList);
    }
}