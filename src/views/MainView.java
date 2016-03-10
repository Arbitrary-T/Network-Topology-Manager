package views;

import models.ListAdapterListModel;
import models.MainViewListener;
import models.Network;
import models.NetworkTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by arbitrary on 2/29/16.
 */
public class MainView extends JFrame
{
    ////////////////////////////////////CLASS SET UP////////////////////////////////////////////
    private MainViewListener agent;

    /////////////////////////NORTH///////////////////////////////////
    private JPanel northJPanel;
    private JTextField nFilterTextField = new JTextField();
    private JButton nFilterJButton = new JButton("Filter");

    ///////////////////////EAST////////////////////////////////////////////////////////////////////
    private JPanel eastJPanel;
    private JTextField[] eastPanelTextFields = {new IntField(), new IntField(), new IntField(), new IntField(),
                                                new JTextField(), new JTextField(), new JTextField()};
    private JButton saveData = new JButton("Save");
    private JButton deleteData = new JButton("Delete");
    private JButton clearFields = new JButton("Clear");
    private JButton printTable = new JButton("Print");

    ////////////////////////CENTER/////////////////////////////////////////////////////////////
    private NetworkTableModel networkTableModel = new NetworkTableModel();
    private ListAdapterListModel<Network> networkListAdapterListModel = new ListAdapterListModel<>();
    private JTable networkDatabaseTableView = new JTable(networkTableModel);
    private JScrollPane tableScrollPane = new JScrollPane(networkDatabaseTableView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    ////////////////////////SOUTH MAYBE////////////////////////////////////////////////////

    public MainView()
    {
        super();

        BorderLayout mainBorderLayout = new BorderLayout();
        mainBorderLayout.setVgap(5);
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
        setListeners();
    }

    private void setupNorthRegion()
    {
        JLabel nFilterJLabel = new JLabel("Filter Database:");
        nFilterJLabel.setBorder(new EmptyBorder(3, 3, 3, 3));
        nFilterJButton.setPreferredSize(new Dimension(100, 25));

        northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());

        northJPanel.add(nFilterTextField, BorderLayout.CENTER);
        northJPanel.add(nFilterJLabel, BorderLayout.WEST);
        northJPanel.add(nFilterJButton, BorderLayout.EAST);
    }

    public void setupCenterRegion()
    {
        networkTableModel.setListModel(networkListAdapterListModel);
        networkDatabaseTableView.setSelectionModel(new DefaultListSelectionModel());
        networkDatabaseTableView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public void setNetworkListAdapterListModel(ArrayList<Network> listFromServer)
    {
        networkListAdapterListModel.setList(listFromServer);
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

        for(int i = 0; i < 4; i++)
        {
            eastPanelTextFields[i].setToolTipText(toolTip);
        }

        nestedNorthJPanel.setLayout(verticalBoxLayout);
        nestedNorthJPanel.add(new JLabel("Network ID:"));
        nestedNorthJPanel.add(eastPanelTextFields[0]);
        nestedNorthJPanel.add(new JLabel("Number Of Nodes:"));
        nestedNorthJPanel.add(eastPanelTextFields[1]);
        nestedNorthJPanel.add(new JLabel("Number Of Hubs:"));
        nestedNorthJPanel.add(eastPanelTextFields[2]);
        nestedNorthJPanel.add(new JLabel("Number Of Switches:"));
        nestedNorthJPanel.add(eastPanelTextFields[3]);
        nestedNorthJPanel.add(new JLabel("Topology Structure:"));
        nestedNorthJPanel.add(eastPanelTextFields[4]);
        nestedNorthJPanel.add(new JLabel("Country:"));
        nestedNorthJPanel.add(eastPanelTextFields[5]);
        nestedNorthJPanel.add(new JLabel("Status:"));
        nestedNorthJPanel.add(eastPanelTextFields[6]);

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

    public void alertMessage(String frameTitle, String message)
    {
        JOptionPane.showMessageDialog(null, message, frameTitle, JOptionPane.ERROR_MESSAGE);
    }

    private void setListeners()
    {
        clearFields.addActionListener(e -> agent.onClearButtonClick(eastPanelTextFields));
        saveData.addActionListener(e -> agent.onSaveButtonClick(eastPanelTextFields));
        deleteData.addActionListener(e -> agent.onDeleteButtonClick(eastPanelTextFields));
        printTable.addActionListener(e -> agent.onPrintButtonClick(networkDatabaseTableView));
        networkDatabaseTableView.getSelectionModel().addListSelectionListener(e ->
        {
            if(!e.getValueIsAdjusting())
            {
                if(networkDatabaseTableView.getSelectedRow() != -1)
                {
                    eastPanelTextFields[0].setText((networkListAdapterListModel.getList().get(networkDatabaseTableView.getSelectedRow()).getId())+"");
                    eastPanelTextFields[1].setText((networkListAdapterListModel.getList().get(networkDatabaseTableView.getSelectedRow()).getNodes())+"");
                    eastPanelTextFields[2].setText((networkListAdapterListModel.getList().get(networkDatabaseTableView.getSelectedRow()).getHubs())+"");
                    eastPanelTextFields[3].setText((networkListAdapterListModel.getList().get(networkDatabaseTableView.getSelectedRow()).getSwitches())+"");
                    eastPanelTextFields[4].setText((networkListAdapterListModel.getList().get(networkDatabaseTableView.getSelectedRow()).getTopologyStructure()));
                    eastPanelTextFields[5].setText((networkListAdapterListModel.getList().get(networkDatabaseTableView.getSelectedRow()).getCountryOfOrigin()));
                    eastPanelTextFields[6].setText((networkListAdapterListModel.getList().get(networkDatabaseTableView.getSelectedRow()).getCurrentStatus()));
                }
            }
        });
    }

    public void activateAgent(MainViewListener mainAgent)
    {
        this.agent = mainAgent;
    }
}