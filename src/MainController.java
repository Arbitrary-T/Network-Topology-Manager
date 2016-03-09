import controllers.MainViewController;
import models.Connection;
import views.MainView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by T on 03/03/2016.
 */
public class MainController
{
    public static void main(String args[])
    {
        /*try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
        }*/
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.getLookAndFeelDefaults().put("ScrollBar.minimumThumbSize", new Dimension(30, 30));
                    break;
                }
            }
        }
        catch (Exception e)
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        MainView mainView = new MainView();
        Connection clientConnection = new Connection("localhost", 64000);
        MainViewController mainViewController = new MainViewController(mainView, clientConnection);

    }
}
/*
       try
       {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
               if ("Nimbus".equals(info.getName()))
               {
                   UIManager.setLookAndFeel(info.getClassName());
                   UIManager.getLookAndFeelDefaults().put("ScrollBar.minimumThumbSize", new Dimension(30, 30));
                   break;
               }
            }
        }
        catch (Exception e)
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
 */
