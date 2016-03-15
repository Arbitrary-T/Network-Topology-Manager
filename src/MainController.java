import controllers.MainViewController;
import models.Connection;
import views.MainView;

import javax.swing.*;

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
        MainView mainView = new MainView(); //View
        Connection clientConnection = new Connection("localhost", 13337);   //Model
        MainViewController mainViewController = new MainViewController(mainView, clientConnection); //Controller
    }
}