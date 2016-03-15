package views;

import javax.swing.*;
import javax.swing.text.*;


/**
 * Created by Talal Mahmood on 03/03/2016.
 * SID 5296251
 * Coventry University
 */
public class IntField extends JTextField
{
    public IntField()
    {
        super();
    }

    protected Document createDefaultModel()
    {
        return new IntTextDocument();
    }

    /**
     * Checks whether the integer is valid
     * @return return true if the integer is valid.
     */
    public boolean isValidInt()
    {
        try
        {
            Integer.parseInt(getText());
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Converts text to an integer
     * @return the integer value inputted by the user, if its not valid (should not happen) returns -1
     */
    public int getValue()
    {
        try
        {
            return Integer.parseInt(getText());
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }
    class IntTextDocument extends PlainDocument
    {
        /**
         * Inserts content into a document, allows for integers only.
         * @param offs the starting offset &gt;= 0
         * @param str the string to insert; ignoring characters
         * @param attributeSet the attributes for the inserted content
         * @throws BadLocationException
         */
        public void insertString(int offs, String str, AttributeSet attributeSet) throws BadLocationException
        {
            if(str == null)
            {
                return;
            }
            String oldString = getText(0, getLength());
            String newString = oldString.substring(0, offs) + str + oldString.substring(offs);
            try
            {
                Integer.parseInt(newString);
                super.insertString(offs, str, attributeSet);
            }
            catch (NumberFormatException e)
            {
            }
        }
    }
}
