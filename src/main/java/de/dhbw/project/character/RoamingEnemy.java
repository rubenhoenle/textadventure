package de.dhbw.project.character;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;

import java.util.ArrayList;
import java.util.List;

public class RoamingEnemy extends Enemy{

    @SerializedName("path")
    private List<String> travelPath;

    public RoamingEnemy() { }

    public RoamingEnemy(String name, String where, int health, int strength, String startStatement, String killStatement,
                        boolean killed, ItemList dropItemList, List<String> travelPath) {
        super(name, where, health, strength, startStatement, killStatement, killed, dropItemList, true );
        this.travelPath = travelPath;
    }

    public List<String> getTravelPath() {
        return travelPath;
    }

    public String getNextRoom(String currentRoom){
        if (travelPath == null) {
            travelPath= new ArrayList<>();
        }
        for (int i = 0; i < travelPath.size(); i++) {
            String r = travelPath.get(i);
            if(r.equals(currentRoom)){
                if(i+1 == travelPath.size()){
                    return travelPath.get(0);
                } else {
                    return travelPath.get(i+1);
                }
            }
        }
        return null;
    }
}
