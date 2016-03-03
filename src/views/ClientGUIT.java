package views;

import sun.java2d.windows.GDIRenderer;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Created by arbitrary on 2/29/16.
 */
public class ClientGUIT extends JFrame
{
    ///////////////////////////////////////////////////////////////////////////////
    private JPanel northJPanel;
    private JLabel nFilterJLabel;
    private JTextField nFilterTextField;
    private JButton nFilterJButton;
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private JPanel eastJPanel;
    private JTextField networkIDTextField = new JTextField();
    private JTextField numberOfNodesTextField = new JTextField();
    private JTextField numberOfHubsTextField = new JTextField();
    private JTextField numberOfSwitchesTextField = new JTextField();
    private JTextField topologyStructureTextField = new JTextField();
    private JTextField countryTextField = new JTextField();
    private JTextField statusTextField = new JTextField();

    private JButton saveData;
    private JButton clearFields;
    private JButton printTable;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private JPanel southPanel;
    private JButton addButton;
    private JButton loadButton;
    private JButton deleteButton;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    Component mainFrame;
    private JTable databaseTableView;

    public ClientGUIT()
    {
        super();
        mainFrame = this;
        BorderLayout mainBorderLayout = new BorderLayout();

        setTitle("Network Topology Manager");
        mainBorderLayout.setVgap(5);

        databaseTableView = new JTable();
        //noinspection MagicConstant
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 480));
        initialiseNorthRegion();
        //initialiseSouthRegion();
        initialiseEastRegion();
        setLayout(mainBorderLayout);
        add(northJPanel, BorderLayout.NORTH);
        add(eastJPanel, BorderLayout.EAST);
        //add(southPanel, BorderLayout.SOUTH);
        add(new JScrollPane(databaseTableView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
        WindowStateListener listener = new WindowAdapter() {
            public void windowStateChanged(WindowEvent evt)
            {
                //http://www.java2s.com/Code/Java/Swing-JFC/DeterminingWhenaFrameorWindowIsIconizedorMaximized.htm
                int oldState = evt.getOldState();
                int newState = evt.getNewState();
                if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0)
                {
                    eastJPanel.setPreferredSize(new Dimension((int)(mainFrame.getWidth()/4.5), 0));

                }
                else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0)
                {
                    eastJPanel.setPreferredSize(new Dimension((int)(mainFrame.getWidth()/4.5), 0));

                }
            }
        };
        addWindowStateListener(listener);
        ComponentListener listenerRei = new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                super.componentResized(e);
                eastJPanel.setPreferredSize(new Dimension((int)(mainFrame.getWidth()/4.5), 0));
            }
        };
        addComponentListener(listenerRei);
        pack();
    }

    private void initialiseNorthRegion()
    {
        northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());
        nFilterJLabel = new JLabel("Filter Database:");
        nFilterJLabel.setBorder(new EmptyBorder(3, 3, 3, 3));
        nFilterTextField = new JTextField();

        nFilterJButton = new JButton("Filter");
        nFilterJButton.setPreferredSize(new Dimension(100, 25));
        northJPanel.add(nFilterTextField, BorderLayout.CENTER);
        northJPanel.add(nFilterJLabel, BorderLayout.WEST);
        northJPanel.add(nFilterJButton, BorderLayout.EAST);
    }
    private void initialiseEastRegion()
    {
        JPanel nestedNorthJPanel = new JPanel();
        JPanel nestedSouthJPanel = new JPanel();

        eastJPanel = new JPanel();
        saveData = new JButton("Save");

        clearFields = new JButton("Clear");
        printTable = new JButton("Print");
        BoxLayout verticalBoxLayout = new BoxLayout(nestedNorthJPanel, BoxLayout.Y_AXIS);

        BorderLayout mainPanelLayout = new BorderLayout();

        nestedNorthJPanel.setLayout(verticalBoxLayout);
        nestedNorthJPanel.add(new JLabel("Network ID:"));
        nestedNorthJPanel.add(networkIDTextField);
        nestedNorthJPanel.add(new JLabel("Number Of Nodes:"));
        nestedNorthJPanel.add(numberOfNodesTextField);
        nestedNorthJPanel.add(new JLabel("Number Of Hubs:"));
        nestedNorthJPanel.add(numberOfHubsTextField);
        nestedNorthJPanel.add(new JLabel("Number Of Switches:"));
        nestedNorthJPanel.add(numberOfSwitchesTextField);
        nestedNorthJPanel.add(new JLabel("Topology Structure:"));
        nestedNorthJPanel.add(topologyStructureTextField);
        nestedNorthJPanel.add(new JLabel("Country:"));
        nestedNorthJPanel.add(countryTextField);
        nestedNorthJPanel.add(new JLabel("Status:"));
        nestedNorthJPanel.add(statusTextField);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        nestedSouthJPanel.setLayout(new GridBagLayout());
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        nestedSouthJPanel.add(saveData, gridBagConstraints);

        gridBagConstraints.gridx++;
        nestedSouthJPanel.add(clearFields, gridBagConstraints);

        gridBagConstraints.gridx++;
        nestedSouthJPanel.add(printTable, gridBagConstraints);

        eastJPanel.setLayout(mainPanelLayout);
        eastJPanel.setBorder(new EmptyBorder(0, 3, 0, 3));
        eastJPanel.setPreferredSize(new Dimension((int)(this.getWidth()/4.5), 0));
        eastJPanel.add(nestedNorthJPanel, BorderLayout.NORTH);
        eastJPanel.add(nestedSouthJPanel, BorderLayout.SOUTH);



    }
    private void initialiseSouthRegion()
    {
        southPanel = new JPanel();

        addButton = new JButton("Add row");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton = new JButton("Load Database");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton = new JButton("Delete row");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        southPanel.add(addButton);
        southPanel.add(loadButton);
        southPanel.add(deleteButton);
    }
    private Component q()
    {
        JPanel test = new JPanel();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        test.setLayout(new GridBagLayout());
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        test.add(saveData, gridBagConstraints);

        gridBagConstraints.gridx++;
        test.add(clearFields, gridBagConstraints);

        gridBagConstraints.gridx++;
        test.add(printTable, gridBagConstraints);
        return test;
    }
}
