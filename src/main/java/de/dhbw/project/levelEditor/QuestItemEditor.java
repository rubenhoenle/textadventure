package de.dhbw.project.levelEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.QuestItem;
import de.dhbw.project.item.ItemState;

public class QuestItemEditor {

    private Editor editor;

    public QuestItemEditor(Editor editor) {
        this.editor = editor;
    }

    public Editor getEditor() {
        return editor;
    }

    public List<QuestItem> startQuestItemEditor(List<QuestItem> questItemList) {
        List<QuestItem> returnList = questItemList;
        boolean goOn = false;
        if (null == returnList) {
            returnList = new ArrayList<>();
        }
        do {
            System.out.println(
                    "Enter 'list' to list all QuestItems 'add' to add new ones, 'edit' to edit one, 'inspect' to inspect an QuestItem and 'delete' to delete one:");
            goOn = menu(returnList);
        } while (goOn);

        return returnList;
    }

    public boolean menu(List<QuestItem> questItems) {
        String input = SimpleUserInput.scan().toLowerCase();
        QuestItem result;
        switch (input) {
        case "list":
            listQuestItems(questItems);
            return true;
        case "add":
            result = createQuestItem();
            if (null != result) {
                questItems.add(result);
                getEditor().setChanged();
            }
            return true;
        case "edit":
            questItems = editQuestItemMenu(questItems);
            return true;
        case "inspect":
            inspectQuestItem(questItems);
            return true;
        case "delete":
            deleteQuestItem(questItems);
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

    public void listQuestItems(List<QuestItem> questItems) {
        System.out.println("QuestItemList:");
        if ((null != questItems) && (questItems.size() > 0)) {
            questItems.forEach(element -> {
                System.out.println("  -" + element.getName());
            });
        } else {
            System.out.println("No QuestItems in this list!");
        }

    }

    public void inspectQuestItem(List<QuestItem> questItems) {
        if (null == questItems) {
            questItems = new ArrayList<>();
        }
        String input;
        System.out.println("Please enter the name of the QuestItem you want to inspect");
        input = SimpleUserInput.scan();
        List<QuestItem> resultList = questItems.stream().filter(e -> e.getName().equals(input))
                .collect(Collectors.toList());
        if (resultList.size() > 0) {
            QuestItem result = resultList.get(0);
            String resultState = "";
            String resultEquipment = "";
            if (null != result.getItemstate()) {
                resultState = result.getItemstate().toString();
            }
            if (null != result.getEquipmentType()) {
                resultEquipment = result.getEquipmentType().toString();
            }
            System.out.println("Name: " + result.getName());
            System.out.println("Description: " + result.getDescription());
            System.out.println("ItemState: " + resultState);
            System.out.println("Strength: " + result.getStrength());
            System.out.println("EquipmentType: " + resultEquipment);
            System.out.println("Type: " + result.getType());
            System.out.println("Add-Inventoryspace: " + result.getExpandInventorySpace());
        } else {
            System.out.println("There is no QuestItem with this name!");
        }
    }

    public void deleteQuestItem(List<QuestItem> questItems) {
        if (null != questItems) {
            System.out.println("Please enter the name of the QuestItem you want to delete: ");
            String input = SimpleUserInput.scan();
            List<QuestItem> resultList = questItems.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                QuestItem result = resultList.get(0);
                boolean delete = SimpleUserInput.deleteDialogue(result.getName(), result.getDescription());
                if (delete) {
                    questItems.remove(result);
                } else {
                    System.out.println("Deletion abborted!");
                }
            } else {
                System.out.println("There is no QuestItem with this name!");
                listQuestItems(questItems);
            }
        } else {
            System.out.println("Error: The QuestItemList is null!");
        }

    }

    public QuestItem createQuestItem() {
        String name, description, type;
        ItemState addItemState;
        EquipmentType addEquipmentType;
        int addStrenght, addInventorySpace;
        Decision d = Decision.AGAIN;
        do {
            name = SimpleUserInput.addMethod("QuestItem Name");
            description = SimpleUserInput.addMethod("Description");
            type = SimpleUserInput.addMethod("Item-Typ(book/cloth/food/resource/weapon/tool)"); // TODO: create
                                                                                                // SimpleUserInput-Method
            addStrenght = SimpleUserInput.addMethodInt("Strength");
            addInventorySpace = SimpleUserInput.addMethodInt("Add-Inventoryspace");
            addEquipmentType = SimpleUserInput.addMethodEquipmentType("Equipment-Typ");
            addItemState = SimpleUserInput.addMethodItemState("Item State");
            d = SimpleUserInput.storeDialogue("Quest Item");
        } while (d == Decision.AGAIN);
        if (d == Decision.SAVE) {
            return new QuestItem(name, description, addItemState, addStrenght, addEquipmentType, type,
                    addInventorySpace);
        } else {
            return null;
        }
    }

    public List<QuestItem> editQuestItemMenu(List<QuestItem> questItems) {
        if (null == questItems) {
            questItems = new ArrayList<>();
        }
        System.out.println("Please enter the name of the Questitem you want to edit:");
        String input = SimpleUserInput.scan();
        List<QuestItem> resultList = questItems.stream().filter(e -> e.getName().equals(input))
                .collect(Collectors.toList());
        if (resultList.size() > 0) {
            QuestItem result = resultList.get(0);
            QuestItem resultEdited = editQuestItem(result);
            if (null != resultEdited) {
                questItems.remove(result);
                questItems.add(resultEdited);
                getEditor().setChanged();
            }
            return questItems;
        } else {
            System.out.println("There is no QuestItem with this name !");
            listQuestItems(questItems);
            return questItems;
        }
    }

    public QuestItem editQuestItem(QuestItem questItem) {
        String name, description, type;
        ItemState addItemState;
        EquipmentType addEquipmentType;
        int addStrenght, addInventorySpace;
        Decision d = Decision.AGAIN;
        do {
            name = SimpleUserInput.editMethod("Questitem Name", questItem.getName());
            description = SimpleUserInput.editMethod("Description", questItem.getDescription());
            type = SimpleUserInput.editMethod("Item-Typ(book/cloth/food/resource/weapon/tool)",
                    questItem.getType().toString()); // TODO: create SimpleUserInput-Method
            addStrenght = SimpleUserInput.editMethod("Strength", questItem.getStrength());
            addInventorySpace = SimpleUserInput.editMethod("Add-Inventoryspace", questItem.getExpandInventorySpace());
            addEquipmentType = SimpleUserInput.editMethod("Equipment-Typ", questItem.getEquipmentType());
            addItemState = SimpleUserInput.editMethod("Item State", questItem.getItemstate());
            d = SimpleUserInput.storeDialogue("Quest Item");
        } while (d == Decision.AGAIN);
        if (d == Decision.SAVE) {
            return new QuestItem(name, description, addItemState, addStrenght, addEquipmentType, type,
                    addInventorySpace);
        } else {
            return null;
        }
    }
}
