package models;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by T on 05/03/2016.
 */
public class Connection
{
    private Socket socketConnection;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ArrayList<Network> networkArrayList = new ArrayList<>();
    /*public enum ConnectionState
    {
        CONNECTED, BUSY, DISCONNECTED
    }*/

    public Connection(String host, int socket)
    {
        setupSocket(host, socket);
    }


    private void setupSocket(String host, int socket)
    {
        try
        {
            socketConnection = new Socket(host, socket);
            objectOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
            objectInputStream = new ObjectInputStream(socketConnection.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void setData(String command, Network selectedIndex)
    {
        try
        {
            objectOutputStream.writeObject(command);
            objectOutputStream.writeObject(selectedIndex);
            objectOutputStream.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Network> getData()
    {
        networkArrayList.clear();
        try
        {
            //System.out.println((ArrayList<Network>) objectInputStream.readObject());
            networkArrayList.addAll((ArrayList<Network>) objectInputStream.readObject());
            //networkArrayList.addAll((ArrayList<Network>) objectInputStream.readObject());

            System.out.println(networkArrayList);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return networkArrayList;
    }

    public Socket getSocket()
    {
        return socketConnection;
    }
}