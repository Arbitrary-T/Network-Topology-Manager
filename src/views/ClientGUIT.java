package views;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import java.awt.*;

/**
 * Created by arbitrary on 2/29/16.
 */
public class ClientGUIT extends JFrame
{
    JPanel northJPanel;
    JLabel nFilterJLabel;
    static JTextField nFilterTextField;
    static JButton nFilterJButton;
    //////////////////////////////////////
    JPanel southPanel;
    JButton addButton;
    JButton loadButton;
    JButton deleteButton;
    static JTable databaseTableView;
    public ClientGUIT()
    {
        super();
        databaseTableView = new JTable();
        JButton k = new JButton("EEEE");
        k.setSize(200, 20);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        initialiseNorthRegion();
        initialiseSouthRegion();
        setLayout(new BorderLayout());
        add(northJPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(databaseTableView, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    private void initialiseNorthRegion()
    {
        northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());
        nFilterJLabel = new JLabel("Filter Database");
        nFilterTextField = new JTextField();
        nFilterJButton = new JButton("Filter");
        northJPanel.add(nFilterTextField, BorderLayout.CENTER);
        northJPanel.add(nFilterJLabel, BorderLayout.WEST);
        northJPanel.add(nFilterJButton, BorderLayout.EAST);
    }
    private void initialiseSouthRegion()
    {
        FlowLayout flowLayout = new FlowLayout();

        JPanel buttonHBox = new JPanel();
        buttonHBox.setLayout(new BoxLayout(buttonHBox, BoxLayout.X_AXIS));
        southPanel = new JPanel();
        southPanel.setLayout(flowLayout);

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
    public static void main(String args[])
    {
        new ClientGUIT();
    }
}
