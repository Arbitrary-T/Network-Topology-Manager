package models;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Talal Mahmood on 05/03/2016.
 * SID 5296251
 * Coventry University
 */

public class Connection
{
    private Socket socketConnection;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ArrayList<Network> networkArrayList = new ArrayList<>();
    private ConnectionListener agent;
    private boolean stayAlive = true;
    /**
     * Connection model, responsible for communicating with the server.
     * @param host The host (server) address.
     * @param socket The socket number whereby the communication between the server and client occurs.
     */
    public Connection(String host, int socket)
    {
        setupSocket(host, socket);
        //Thread that mimics polling by checking the input stream and identifying any updates from the server.
        Thread pollingThread = new Thread(()->
        {
            while(stayAlive)
            {
                try
                {
                    if(objectInputStream != null)
                    {
                        if(objectInputStream.readObject().equals("Refresh"))
                        {
                            agent.notifyUpdate(getData());
                        }
                    }
                }
                catch(IOException | ClassNotFoundException e)
                {
                    try
                    {
                        if(socketConnection != null)
                        {
                            socketConnection.close();
                        }
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    return;
                }
            }
        });
        pollingThread.setDaemon(true);  //Thread bound to main process, dies when process ends.
        pollingThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(()->
        {
            stayAlive = false;
            setData("Shutdown", null);
        }));
    }

    /**
     * Method to initiate communication with the server.
     * @param host The host (server address)
     * @param socket The socket number whereby the communication between the server and client occurs.
     */
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

    /**
     * Sends a command and a Network object to the server
     * @param command The command to be sent, "Add", "Delete", Modify or "Refresh"
     * @param userInput The Network object to be sent, could be null with the "Refresh" command.
     */
    public void setData(String command, Network userInput)
    {
        try
        {
            if(objectOutputStream != null)
            {
                objectOutputStream.writeObject(command);
                objectOutputStream.writeObject(userInput);
                objectOutputStream.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that receives, and parses data from the server.
     * @return Updated ArrayList<Network> from server.
     */
    private ArrayList<Network> getData()
    {
        networkArrayList.clear();
        try
        {
            int size = objectInputStream.readInt();
            for(int i = 0; i < size; i++)
            {
                networkArrayList.add((Network) objectInputStream.readObject());
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return networkArrayList;
    }

    /**
     * Accessor method for the networkArrayList
     * @return ArrayList<Network> from this class.
     */
    public ArrayList<Network> getNetworkArrayList()
    {
        return  networkArrayList;
    }

    /**
     * Accessor method for the socketConnection
     * @return Socket from this class.
     */
    public Socket getSocket()
    {
        return socketConnection;
    }

    /**
     * Method to initiate contract between MainViewController and Connection class.
     * @param mainAgent The reference from the MainViewController
     */
    public void activateAgent(ConnectionListener mainAgent)
    {
        this.agent = mainAgent;
    }
}