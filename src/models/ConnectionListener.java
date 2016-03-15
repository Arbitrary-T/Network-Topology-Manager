package models;

import java.util.ArrayList;

/**
 * Created by Talal Mahmood on 03/10/2016.
 * SID 5296251
 * Coventry University
 */
public interface ConnectionListener
{
    void notifyUpdate(ArrayList<Network> updatedList);
}
