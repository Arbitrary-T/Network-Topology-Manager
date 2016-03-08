package controllers;

import models.Connection;
import models.MainViewListener;
import models.Network;
import views.IntField;
import views.MainView;

import javax.swing.*;
import java.awt.print.PrinterException;

/**
 * Created by T on 05/03/2016.
 */
public class MainViewController implements MainViewListener
{
    private MainView mainView;
    private Connection clientConnection;

    public MainViewController(MainView mainView, Connection clientConnection)    //model as well
    {
        this.mainView = mainView;
        this.clientConnection = clientConnection;
        this.mainView.activateAgent(this);
        clientConnection.setData("Refresh", null);
        mainView.setNetworkListAdapterListModel(clientConnection.getData());
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
        Network userInput = networkObjectParser(textFields);
        if(clientConnection.getSocket() != null && userInput != null )
        {
            clientConnection.setData("Add", userInput);
            mainView.setNetworkListAdapterListModel(clientConnection.getData());
        }
    }

    @Override
    public void onDeleteButtonClick(JTextField[] textFields)
    {
        Network userInput = networkObjectParser(textFields);
        if(clientConnection.getSocket() != null && userInput != null )
        {
            clientConnection.setData("Delete", userInput);
            mainView.setNetworkListAdapterListModel(clientConnection.getData());
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
    public void onTableFocusChange() {

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

}

/*
    My Stash
    //private JTextField networkIDTextField = new IntField();
    //private JTextField numberOfNodesTextField = new IntField();
    //private JTextField numberOfHubsTextField = new IntField();
    //private JTextField numberOfSwitchesTextField = new IntField();
    //private JTextField topologyStructureTextField = new JTextField();
    //private JTextField countryTextField = new JTextField();
    //private JTextField statusTextField = new JTextField();


    this.addWindowStateListener(e -> agent.onWindowResize(eastJPanel));
    WindowStateListener listener = new WindowAdapter()
        {
            public void windowStateChanged(WindowEvent evt)
            {
                //http://www.java2s.com/Code/Java/Swing-JFC/DeterminingWhenaFrameorWindowIsIconizedorMaximized.htm
                int oldState = evt.getOldState();
                int newState = evt.getNewState();

                if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0)
                {
                    panelToAdjust.setPreferredSize(new Dimension((int)(evt.getComponent().getWidth()/4.5), 0));
                }
                else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0)
                {
                    panelToAdjust.setPreferredSize(new Dimension((int)(evt.getComponent().getWidth()/4.5), 0));
                }
            }
        };
        ComponentListener listenerRei = new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                panelToAdjust.setPreferredSize(new Dimension((int)(e.getComponent().getWidth()/4.5), 0));
            }
        };
        mainView.addComponentListener(listenerRei);
        mainView.addWindowStateListener(listener);
 */