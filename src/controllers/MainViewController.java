package controllers;

import models.MainViewListener;
import views.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by T on 05/03/2016.
 */
public class MainViewController implements MainViewListener
{
    private MainView mainView;

    public MainViewController(MainView mainView)    //model as well
    {
        this.mainView = mainView;
        mainView.activateAgent(this);
    }

    @Override
    public void onClearButtonClick(JTextField[] textFields)
    {
        for(int i = 0; i< textFields.length; i++)
        {
            textFields[i].setText("");
        }
    }

    @Override
    public void onSaveButtonClick() {

    }

    @Override
    public void onDeleteButtonClick() {

    }

    @Override
    public void onPrintButtonClick() {

    }

    @Override
    public void onTableFocusChange() {

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