import controllers.MainViewController;
import views.MainView;

import javax.swing.*;

/**
 * Created by T on 03/03/2016.
 */
public class MainController
{
    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
        }
        MainView a = new MainView();
        new MainViewController(a);
    }
}
/*
 try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
 */
