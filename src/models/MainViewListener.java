package models;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Created by Talal Mahmood on 04/03/2016.
 * SID 5296251
 * Coventry University
 */

/**
 * Interface between MainView, and the MainViewController for the actions performed.
 */
public interface MainViewListener
{
    void onClearButtonClick(JTextField[] textFields);
    void onSaveButtonClick(JTextField[] textFields);
    void onDeleteButtonClick(JTextField[] textFields);
    void onPrintButtonClick(JTable contextTable);
    void onFilterTextChange(String query, JTable table);
    void onSelectedIndexChange(JTable table, JTextField[] textFields, TableModel tableModel);
}