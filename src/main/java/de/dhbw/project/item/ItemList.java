package de.dhbw.project.item;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    @SerializedName("cloths")
    private List<Clothing> clothsItemList = new ArrayList<>();
    @SerializedName("foods")
    private List<Food> foodsItemList = new ArrayList<>();
    @SerializedName("resources")
    private List<Resource> resourcesItemList = new ArrayList<>();
    @SerializedName("tools")
    private List<Tool> toolsItemList = new ArrayList<>();
    @SerializedName("weapons")
    private List<Weapon> weaponsItemList = new ArrayList<>();
    @SerializedName("books")
    private List<Book> booksItemList = new ArrayList<>();

    public ItemList() {
    }

    public ItemList(List<Clothing> clothsItemList, List<Food> foodsItemList, List<Resource> resourcesItemList,
            List<Tool> toolsItemList, List<Weapon> weaponsItemList, List<Book> booksItemList) {
        this.clothsItemList = clothsItemList;
        this.foodsItemList = foodsItemList;
        this.resourcesItemList = resourcesItemList;
        this.toolsItemList = toolsItemList;
        this.weaponsItemList = weaponsItemList;
        this.booksItemList = booksItemList;
    }

    public List<Item> getAllItemList() {
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(clothsItemList);
        items.addAll(foodsItemList);
        items.addAll(resourcesItemList);
        items.addAll(toolsItemList);
        items.addAll(weaponsItemList);
        items.addAll(booksItemList);
        return items;
    }

    public void addItem(Item i) {
        if (i instanceof Clothing) {
            clothsItemList.add((Clothing) i);
        } else if (i instanceof Food) {
            foodsItemList.add((Food) i);
        } else if (i instanceof Resource) {
            resourcesItemList.add((Resource) i);
        } else if (i instanceof Weapon) {
            weaponsItemList.add((Weapon) i);
        } else if (i instanceof Tool) {
            toolsItemList.add((Tool) i);
        } else if (i instanceof Book) {
            booksItemList.add((Book) i);
        } else {
            System.out.println("The item type" + i.getClass() + " is not supported by Mehtod addItem()!");
        }
    }

    public Item getItem(String itemName) {
        for (Item i : clothsItemList) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        for (Item i : foodsItemList) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        for (Item i : resourcesItemList) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        for (Item i : weaponsItemList) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        for (Item i : toolsItemList) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        for (Item i : booksItemList) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        if (item instanceof Clothing) {
            clothsItemList.remove(item);
        } else if (item instanceof Food) {
            foodsItemList.remove(item);
        } else if (item instanceof Resource) {
            resourcesItemList.remove(item);
        } else if (item instanceof Weapon) {
            weaponsItemList.remove(item);
        } else if (item instanceof Tool) {
            toolsItemList.remove(item);
        } else if (item instanceof Book) {
            booksItemList.remove(item);
        } else {
            System.out.println("The item type" + item.getClass() + " is not supported by Method removeItem()!");
        }

    }

    public int getNumberOfItem(String itemName) {
        int count = 0;
        List<Item> temp = new ArrayList<>();
        temp.addAll(clothsItemList);
        temp.addAll(foodsItemList);
        temp.addAll(resourcesItemList);
        temp.addAll(weaponsItemList);
        temp.addAll(toolsItemList);
        temp.addAll(booksItemList);

        for (Item i : temp) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                count++;
            }
        }

        if (count > 0) {
            return count;
        }

        return -1;
    }
}
