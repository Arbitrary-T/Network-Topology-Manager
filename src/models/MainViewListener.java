package models;

import javax.swing.*;

/**
 * Created by T on 04/03/2016.
 */
public interface MainViewListener
{
    void onClearButtonClick(JTextField[] textFields);
    void onSaveButtonClick();
    void onDeleteButtonClick();
    void onPrintButtonClick();
    void onTableFocusChange();
}
