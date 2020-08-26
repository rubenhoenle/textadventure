package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InteractiveObject extends Thing {
    @SerializedName("state")
    private String state;
    @SerializedName("place")
    private String place;
    @SerializedName("usage")
    private String usage;
    @SerializedName("action")
    private String action;

    @SerializedName("createables")
    private List<Creatable> interactiveObjectCreatablesList = new ArrayList<>();

    public InteractiveObject(String name, String description, String state, String place, String usage, String action) {
        super(name, description);
        this.state = state;
        this.place = place;
        this.usage = usage;
        this.action = action;
    }

    public String getWhere() {
        return place;
    }

    public String getUsage() {
        return usage;
    }

    public List<Creatable> getInteractiveObjectCreatablesList() {
        return interactiveObjectCreatablesList;
    }

    public void print() {
        if (interactiveObjectCreatablesList.isEmpty()) {
            System.out.print("This " + this.getName() + " can not be used.");
        }

        else {
            System.out.println("This " + this.getName() + " can be used for " + this.action + " following things:");

            TableList tl = new TableList(2, "name", "materials").sortBy(0).withUnicode(true);
            // from a list
            for (Creatable creatable : interactiveObjectCreatablesList) {
                tl.addRow(creatable.getName(), creatable.getNeededMaterialAsString());
            }
            tl.print();
        }
    }
}
