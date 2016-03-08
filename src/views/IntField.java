package views;

import javax.swing.*;
import javax.swing.text.*;

/**
 * Created by T on 03/03/2016.
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
