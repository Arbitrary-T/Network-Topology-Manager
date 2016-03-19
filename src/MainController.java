import controllers.MainViewController;
import models.Connection;
import views.MainView;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Talal Mahmood on 03/03/2016.
 * SID 5296251
 * Coventry University
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
            e.printStackTrace();
        }
        System.out.println(System.getProperty("os.name"));

        MainView mainView = new MainView(); //View
        Connection clientConnection = new Connection("localhost", 13337);   //Model
        MainViewController mainViewController = new MainViewController(mainView, clientConnection); //Controller
    }
}
/*
 try
        {
            SwingUtilities.invokeAndWait(()->
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    mainView[0] = new MainView(); //View
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
        catch (InterruptedException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
 */