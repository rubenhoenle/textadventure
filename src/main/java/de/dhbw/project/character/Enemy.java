package de.dhbw.project.character;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Item;
import de.dhbw.project.character.Character;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Character {

    @SerializedName("dropItems")
    private List<Item> dropItemList = new ArrayList<>();
    @SerializedName("autoAttack")
    private boolean autoAttack;

    public Enemy() {

    }

    public Enemy(String name, String where, int health, int strengh, String startStatement, String killStatement,
            boolean killed, List<Item> dropItemList, boolean autoAttack) {
        super(name, where, health, strengh, startStatement, killStatement, killed);
        this.dropItemList = dropItemList;
        this.autoAttack = autoAttack;
    }

    public List<Item> getDropItemList() {
        return dropItemList;
    }

    public void setDropItemList(List<Item> dropItemList) {
        this.dropItemList = dropItemList;
    }

    public boolean isAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(boolean autoAttack) {
        this.autoAttack = autoAttack;
    }
}
