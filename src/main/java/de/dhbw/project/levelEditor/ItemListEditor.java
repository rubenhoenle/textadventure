package de.dhbw.project.levelEditor;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.project.item.*;

public class ItemListEditor {

    private Editor editor;

    public Editor getEditor() {
        return editor;
    }

    ItemListEditor(Editor editor) {
        this.editor = editor;
    }

    public ItemList startItemListEditor(ItemList liste) {
        boolean goOn = false;
        if (null == liste) {
            liste = new ItemList();
        }
        do {
            goOn = editItemList(liste);
        } while (goOn);
        return liste;
    }

    public boolean editItemList(ItemList liste) {
        if (liste == null) {
            return false;
        }
        boolean goOn = true;
        String input;
        System.out.println(
                "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
        input = SimpleUserInput.scan().toLowerCase();
        switch (input) {
        case "list":
            System.out.println("All items:");
            listItems(liste);
            return true;
        case "add":
            do {
                goOn = addItemToItemList(liste);
            } while (goOn);
            return true;
        case "edit":
            editItem(liste);
            return true;
        case "delete":
            deleteItem(liste);
            return true;
        case "quit":
            return false;
        case "q":
            return false;
        default:
            SimpleUserInput.printUserInput(input);
            return true;
        }

    }

    // TODO: write Test
    public void editItem(ItemList liste) {
        System.out.println("Please enter the name of the item you want to edit:");
        String input = SimpleUserInput.scan();
        Item item = liste.getItem(input);
        if (null != item) {
            String typ = item.getClass().getSimpleName();
            switch (typ) {
            case "Book":
                Book book = Book.edit((Book) item);
                if (null != book) {
                    liste.removeItem(item);
                    liste.addItem(book);
                    getEditor().setChanged();
                }
                break;
            case "Clothing":
                Clothing clothing = Clothing.edit((Clothing) item);
                if (null != clothing) {
                    liste.removeItem(item);
                    liste.addItem(clothing);
                    getEditor().setChanged();
                }
                break;
            case "Food":
                Food food = Food.edit((Food) item);
                if (null != food) {
                    liste.removeItem(item);
                    liste.addItem(food);
                    getEditor().setChanged();
                }
                break;
            case "Resource":
                Resource resource = Resource.edit((Resource) item);
                if (null != resource) {
                    liste.removeItem(item);
                    liste.addItem(resource);
                    getEditor().setChanged();
                }
                break;
            case "Tool":
                Tool tool = Tool.edit((Tool) item);
                if (null != tool) {
                    liste.removeItem(item);
                    liste.addItem(tool);
                    getEditor().setChanged();
                }
                break;
            case "Weapon":
                Weapon weapon = Weapon.edit((Weapon) item);
                if (null != weapon) {
                    liste.removeItem(item);
                    liste.addItem(weapon);
                    getEditor().setChanged();
                }
                break;
            default:
                System.out.println("The class '" + typ + "' isn't available in the editor yet.");
            }
        } else {
            System.out.println("There is no item with the name '" + input + "'.");
        }
    }

    public void listItems(ItemList itemList) {
        if (null != itemList) {
            List<Item> liste = itemList.getAllItemList();
            if (0 == liste.size()) {
                System.out.println("There are no items in this list!");
            } else {
                liste.forEach(element -> {
                    System.out.println("  -" + element.getName());
                });
            }
        } else {
            System.out.println("Error: no ItemList found!!");
        }
    }

    public boolean addItemToItemList(ItemList list) {
        String input;
        System.out.println(
                "Enter the type of item you want add (Book/Clothing/Food/Resource/Tool/Weapon) or enter 'quit' to quit");
        input = SimpleUserInput.scan().toLowerCase();
        switch (input) {
        case "book":
            Book book = Book.create();
            if (null != book) {
                list.addItem(book);
                getEditor().setChanged();
            }
            return true;
        case "clothing":
            Clothing clothing = Clothing.create();
            if (null != clothing) {
                list.addItem(clothing);
                getEditor().setChanged();
            }
            return true;
        case "food":
            Item food = Food.create();
            if (null != food) {
                list.addItem(food);
                getEditor().setChanged();
            }
            return true;
        case "resource":
            Resource resource = Resource.create();
            if (null != resource) {
                list.addItem(resource);
                getEditor().setChanged();
            }
            return true;
        case "tool":
            Tool tool = Tool.create();
            if (null != tool) {
                list.addItem(tool);
                getEditor().setChanged();
            }
            return true;
        case "weapon":
            Weapon weapon = Weapon.create();
            if (null != weapon) {
                list.addItem(weapon);
                getEditor().setChanged();
            }
            return true;
        case "quit":
            return false;
        case "q":
            return false;
        default:
            SimpleUserInput.printUserInput(input);
            return true;
        }
    }

    // TODO: write Test
    public void deleteItem(ItemList liste) {
        System.out.println("Please enter the name of the item you want to delete:");
        String input = SimpleUserInput.scan();
        Item item = liste.getItem(input);
        if (null != item) {
            boolean d = SimpleUserInput.deleteDialogue("Item", item.getName());
            if (d) {
                liste.removeItem(item);
                getEditor().setChanged();
            }
        } else {
            System.out.println("There is no item with the name '" + input + "'.");
        }
    }

}
