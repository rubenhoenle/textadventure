package de.dhbw.project.character;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;

import java.util.List;

public class Enemy extends Character {

    @SerializedName("dropItems")
    private ItemList dropItemList = new ItemList();
    @SerializedName("autoAttack")
    private boolean autoAttack;

    public Enemy() {

    }

    public Enemy(String name, String where, int health, int strength, String startStatement, String killStatement,
            boolean killed, int points, ItemList dropItemList, boolean autoAttack) {
        super(name, where, health, strength, startStatement, killStatement, killed, points);
        this.dropItemList = dropItemList;
        this.autoAttack = autoAttack;
    }

    public List<Item> getDropItemListElements() {
        return dropItemList.getAllItemList();
    }

    public ItemList getDropItemList() {
        return dropItemList;
    }

    public void setDropItemList(ItemList dropItemList) {
        this.dropItemList = dropItemList;
    }

    public boolean isAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(boolean autoAttack) {
        this.autoAttack = autoAttack;
    }
}
