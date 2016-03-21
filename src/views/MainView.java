package views;

import models.MainViewListener;
import models.Network;
import models.NetworkTableModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Talal Mahmood on 29/02/2016.
 * SID 5296251
 * Coventry University
 */
public class MainView extends JFrame
{
    ////////////////////////////////////CLASS SET UP////////////////////////////////////////////
    private MainViewListener agent;

    /////////////////////////NORTH///////////////////////////////////
    private JPanel northJPanel;
    private JTextField nFilterTextField = new JTextField();

    ///////////////////////EAST////////////////////////////////////////////////////////////////////
    private JPanel eastJPanel;
    private String eastPanelLabels[] = {"Network ID:", "Number Of Nodes:", "Number Of Hubs:", "Number Of Switches:",
                                        "Topology Structure:", "Country:", "Status:"};
    private JTextField[] eastPanelTextFields = {new IntField(), new IntField(), new IntField(), new IntField(),
                                                new JTextField(), new JTextField(), new JTextField()};
    private JButton saveData = new JButton("Save");
    private JButton deleteData = new JButton("Delete");
    private JButton clearFields = new JButton("Clear");
    private JButton printTable = new JButton("Print");

    ////////////////////////CENTER/////////////////////////////////////////////////////////////
    private NetworkTableModel networkTableModel = new NetworkTableModel();
    private DefaultListModel<Network> networkDefaultListModel = new DefaultListModel<>();
    private JTable networkDatabaseTableView = new JTable(networkTableModel);
    private JScrollPane tableScrollPane = new JScrollPane(networkDatabaseTableView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    /**
     * Construct GUI
     */
    public MainView()
    {
        super();
        BorderLayout mainBorderLayout = new BorderLayout();
        mainBorderLayout.setVgap(5);

        setIconImage(resourceToImage("/resources/application_icon.png"));
        setTitle("Network Topology Manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 680));
        setLayout(mainBorderLayout);
        setLocationRelativeTo(null);
        setupNorthRegion();
        setupCenterRegion();
        setupEastRegion();
        add(northJPanel, BorderLayout.NORTH);
        add(eastJPanel, BorderLayout.EAST);
        add(tableScrollPane, BorderLayout.CENTER);
        setVisible(true);
        pack();
        setupListeners();
    }

    private void setupNorthRegion()
    {
        JLabel nFilterJLabel = new JLabel("Filter Database:");
        nFilterJLabel.setBorder(new EmptyBorder(3, 3, 3, 3));
        nFilterTextField.setToolTipText("Filter must match this pattern column#:query for example 7:Active or 2:>50");

        northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());

        northJPanel.add(nFilterTextField, BorderLayout.CENTER);
        northJPanel.add(nFilterJLabel, BorderLayout.WEST);
        northJPanel.add(Box.createHorizontalStrut(238), BorderLayout.EAST);
    }

    public void setupCenterRegion()
    {
        networkTableModel.setListModel(networkDefaultListModel);
        networkDatabaseTableView.getTableHeader().setReorderingAllowed(false);
        networkDatabaseTableView.setSelectionModel(new DefaultListSelectionModel());
        networkDatabaseTableView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        networkDatabaseTableView.setRowSorter(new TableRowSorter<>(networkTableModel));
    }

    private void setupEastRegion()
    {
        String toolTip = "This field only accepts integers!";
        JPanel nestedNorthJPanel = new JPanel();
        JPanel nestedSouthJPanel = new JPanel();
        BoxLayout verticalBoxLayout = new BoxLayout(nestedNorthJPanel, BoxLayout.Y_AXIS);
        BorderLayout mainPanelLayout = new BorderLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        eastJPanel = new JPanel();
        nestedNorthJPanel.setLayout(verticalBoxLayout);

        for(int i = 0; i < 7; i++)
        {
            if(i < 4)
            {
                eastPanelTextFields[i].setToolTipText(toolTip);
            }
            nestedNorthJPanel.add(new JLabel(eastPanelLabels[i]));
            nestedNorthJPanel.add(eastPanelTextFields[i]);
        }

        nestedSouthJPanel.setLayout(new GridBagLayout());
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        nestedSouthJPanel.add(clearFields, gridBagConstraints);
        gridBagConstraints.gridx++;
        nestedSouthJPanel.add(saveData, gridBagConstraints);
        gridBagConstraints.gridx++;
        nestedSouthJPanel.add(deleteData, gridBagConstraints);
        gridBagConstraints.gridx++;
        nestedSouthJPanel.add(printTable, gridBagConstraints);
        eastJPanel.setLayout(mainPanelLayout);
        eastJPanel.setBorder(new EmptyBorder(0, 3, 0, 3));
        eastJPanel.add(nestedNorthJPanel, BorderLayout.NORTH);
        eastJPanel.add(nestedSouthJPanel, BorderLayout.SOUTH);
    }

    /**
     * Method that sets up the listeners and calls the appropriate method via the MainViewListener interface
     */
    private void setupListeners()
    {
        nFilterTextField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                super.keyReleased(e);
                agent.onFilterTextChange(nFilterTextField.getText(), networkDatabaseTableView);
            }
        });
        clearFields.addActionListener(e -> agent.onClearButtonClick(eastPanelTextFields));
        saveData.addActionListener(e -> agent.onSaveButtonClick(eastPanelTextFields));
        deleteData.addActionListener(e -> agent.onDeleteButtonClick(eastPanelTextFields));
        printTable.addActionListener(e -> agent.onPrintButtonClick(networkDatabaseTableView));
        networkDatabaseTableView.getSelectionModel().addListSelectionListener(e ->
        {
            if(!e.getValueIsAdjusting())
            {
                agent.onSelectedIndexChange(networkDatabaseTableView, eastPanelTextFields, networkTableModel);
            }
        });
    }

    /**
     * Method that updates the tableview, invoked when the database updates
     * @param listFromServer
     */
    public void setNetworkDefaultListModel(ArrayList<Network> listFromServer)
    {
        try {
            SwingUtilities.invokeAndWait(()->
            {
                networkDefaultListModel.clear();
                for (Network aListFromServer : listFromServer)
                {
                    networkDefaultListModel.addElement(aListFromServer);
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Notification message
     * @param frameTitle the title of the Dialog
     * @param message the message of the Dialog
     */
    public void alertMessage(String frameTitle, String message)
    {
        JOptionPane.showMessageDialog(null, message, frameTitle, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Method used to load an image (used for app icon)
     * @param path path of image
     * @return
     */
    private Image resourceToImage(String path)
    {
        URL url = getClass().getResource(path);
        return  Toolkit.getDefaultToolkit().getImage(url);
    }

    /**
     * Activate MainViewListener
     * @param mainAgent agent on the MainViewController
     */
    public void activateAgent(MainViewListener mainAgent)
    {
        this.agent = mainAgent;
    }
}