package models;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

/**
 * Created by T on 04/03/2016.
 */
public class NetworkTableModel extends AbstractTableModel
{
    private ListModel<Network> listOfNetworks = new DefaultListModel<>();
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public NetworkTableModel()
    {

    }
    public final void setListModel(ListModel<Network> listOfNetworks)
    {
        if(this.listOfNetworks != null)
        {
            this.listOfNetworks.removeListDataListener(listModelChangeListener);
        }
        this.listOfNetworks = listOfNetworks;
        if (listOfNetworks != null)
        {
            listOfNetworks.addListDataListener(listModelChangeListener);
        }
        fireTableDataChanged();
    }
    private enum Columns
    {
        ID, NUMBER_OF_NODES, NUMBER_OF_HUBS, NUMBER_OF_SWITCHES, TOPOLOGY_STRUCTURE, COUNTRY, STATUS
    }

    @Override
    public int getRowCount()
    {
        return listOfNetworks.getSize();
    }

    @Override
    public int getColumnCount()
    {
        return Columns.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Object valueAt = null;
        Network network = listOfNetworks.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column)
        {
            case ID:
                valueAt = network.getId();
                break;
            case NUMBER_OF_NODES:
                valueAt = network.getNodes();
                break;
            case NUMBER_OF_HUBS:
                valueAt = network.getHubs();
                break;
            case NUMBER_OF_SWITCHES:
                valueAt = network.getSwitches();
                break;
            case TOPOLOGY_STRUCTURE:
                valueAt = network.getTopologyStructure();
                break;
            case COUNTRY:
                valueAt = network.getCountryOfOrigin();
                break;
            case STATUS:
                valueAt = network.getCurrentStatus();
                break;
        }
        return valueAt;
    }

    @Override
    public String getColumnName(int column)
    {
        Columns[] columns = Columns.values();
        Columns columnsObj = columns[column];

        String columnName = null;
        switch (columnsObj)
        {
            case ID:
                columnName = "Network ID";
                break;
            case NUMBER_OF_NODES:
                columnName = "Number Of Nodes";
                break;
            case NUMBER_OF_HUBS:
                columnName = "Number Of Hubs";
                break;
            case NUMBER_OF_SWITCHES:
                columnName = "Number Of Switches";
                break;
            case TOPOLOGY_STRUCTURE:
                columnName = "Topology Structure";
                break;
            case COUNTRY:
                columnName = "Country";
                break;
            case STATUS:
                columnName = "Status";
                break;
        }
        return columnName;
    }
    private class ListModelChangeListener implements ListDataListener
    {
        @Override
        public void intervalAdded(ListDataEvent e) {
            fireTableDataChanged();
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            fireTableDataChanged();

        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            fireTableDataChanged();

        }
    }
}
