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
    private ConnectionListener agent;

    public Connection(String host, int socket)
    {
        setupSocket(host, socket);
        Thread pollingThread = new Thread(()->
        {
            while(true)
            {
                try
                {
                    assert objectInputStream != null;
                    if(objectInputStream.readObject().equals("Refresh"))
                    {
                        agent.notifyUpdate(getData());
                    }
                }
                catch(IOException | ClassNotFoundException e)
                {
                    try
                    {
                        socketConnection.close();
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                    return;
                }
        }});
        pollingThread.setDaemon(true);
        pollingThread.start();
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

    public void setData(String command, Network userInput)
    {
        try
        {
            objectOutputStream.writeObject(command);
            objectOutputStream.writeObject(userInput);
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
            int size = objectInputStream.readInt();
            System.out.println(size);
            for(int i = 0; i < size; i++)
            {
                networkArrayList.add((Network) objectInputStream.readObject());
            }
            System.out.println(networkArrayList);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return networkArrayList;
    }

    public ArrayList<Network> getNetworkArrayList()
    {
        return  networkArrayList;
    }

    public Socket getSocket()
    {
        return socketConnection;
    }

    public void activateAgent(ConnectionListener mainAgent)
    {
        this.agent = mainAgent;
    }
}