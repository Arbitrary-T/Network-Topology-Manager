package models;

import javax.swing.*;

/**
 * Created by T on 04/03/2016.
 */
public interface MainViewListener
{
    void onClearButtonClick(JTextField[] textFields);
    void onSaveButtonClick(JTextField[] textFields);
    void onDeleteButtonClick(JTextField[] textFields);
    void onPrintButtonClick(JTable contextTable);
}
