package models;

import java.util.ArrayList;

/**
 * Created by arbitrary on 3/10/16.
 */
public interface ConnectionListener
{
    void notifyUpdate(ArrayList<Network> updatedList);
}
