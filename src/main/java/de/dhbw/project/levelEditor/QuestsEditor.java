package de.dhbw.project.levelEditor;

import java.time.format.SignStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.project.Quest;
import de.dhbw.project.QuestItem;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class QuestsEditor {
    // plan: gets a List of quests and provides the menu for view, delete, edit and
    // add

    Editor editor;

    public QuestsEditor(Editor editor) {
        this.editor = editor;
    }

    public Editor getEditor() {
        return editor;
    }

    public List<Quest> startQuestsEditor(List<Quest> quests) {
        boolean goOn = false;
        if (null == quests) {
            quests = new ArrayList<>();
            getEditor().setChanged();
        }
        do {
            goOn = menu(quests);
        } while (goOn);
        return quests;
    }

    public boolean menu(List<Quest> quests) {
        System.out.println();
        String input = SimpleUserInput.scan().toLowerCase();
        System.out.println(
                "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
        switch (input) {
        case "list":
            listQuests(quests);
            return true;
        case "inspect":
            inspect(quests);
            return true;
        case "add":
            Quest result = createQuest();
            if (null != result) {
                quests.add(result);
                getEditor().setChanged();
            }
            return true;
        case "edit":
            quests = editQuestMenu(quests);
            return true;
        case "delete":
            delete(quests);
            return true;
        case "quit":
            return false;
        case "q":
            return false;
        default:
            SimpleUserInput.printUserInput(input);
            System.out.println("Please enter 'quit' to quit the menu.");
            return true;
        }
    }

    public void listQuests(List<Quest> quests) {
        if (quests != null) {
            System.out.println("QuestList:");
            if (quests.size() > 0) {
                quests.forEach(element -> System.out.println("  -" + element.getName()));
            } else {
                System.out.println("There are no Quests yet!");
            }
        }
    }

    public void inspect(List<Quest> quests) {
        if ((quests != null) && (quests.size() > 0)) {
            System.out.println("Please enter the name of the quest you want to inspect!");
            String input = SimpleUserInput.scan();
            List<Quest> resultList = quests.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Quest result = resultList.get(0);
                System.out.println("Name: " + result.getName());
                System.out.println("TextStart: " + result.getTextStart());
                System.out.println("TestAccept: " + result.getTextAccept());
                System.out.println("TextMid: " + result.getTextMid());
                System.out.println("TextEnd: " + result.getTextEnd());
                System.out.println("Completed: " + result.isCompleted());
                System.out.println("Rewards: ");
                getEditor().getQuestItemEditor().listQuestItems(result.getReward());
                System.out.println("FulfillmentItems: ");
                getEditor().getQuestItemEditor().listQuestItems(result.getFulfillmentItems());
                System.out.println("FulfillmentKill: " + result.getFulfillmentKill());
                System.out.println("Accepted: " + result.isAccepted());
                System.out.println("TalkedOnce: " + result.isTalkedOnce());
                System.out.println("AutoComplete: " + result.isAutoComplete());
                System.out.println("Is main Quest: " + result.isMainQuest());
                System.out.println("Points: " + result.getPoints());
                System.out.println("Remove FulFillmentItems: " + result.isRemoveFulfillmentItems());
            } else {
                System.out.println("There is no quest with this name!");
                System.out.println("The following quests are available:");
                listQuests(quests);
            }
        } else {
            System.out.println("There are not Quests to inspect!");
        }
    }

    public void delete(List<Quest> quests) {
        if ((quests != null) && (quests.size() > 0)) {
            System.out.println("Please enter the name of the Quest you want to delete: ");
            String input = SimpleUserInput.scan();
            List<Quest> resultList = quests.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Quest result = resultList.get(0);
                boolean delete = SimpleUserInput.deleteDialogue(result.getName(), result.getTextStart());
                if (delete == true) {
                    quests.remove(result);
                } else {
                    System.out.println("Deletion aborted!");
                }
            } else {
                System.out.println("There is no Quest with this name!");
            }
        } else {
            System.out.println("There are no Quests to delete!");
        }
    }

    // TODO: add Tests
    public Quest createQuest() {
        Decision d = Decision.ABBORT;
        String name, textStart, textAccept, textMid, textEnd, fulfillmentKill;
        Boolean completed, accepted, talkedOnce, autoComplete, mainQuest, removeFulfillmentItems;
        List<QuestItem> reward, fulfillmentItems;
        int points;
        do {
            name = SimpleUserInput.addMethod("Name");
            textStart = SimpleUserInput.addMethod("Text-Start");
            textAccept = SimpleUserInput.addMethod("Text-Accept");
            textMid = SimpleUserInput.addMethod("Text-Mid");
            textEnd = SimpleUserInput.addMethod("Text-End");
            completed = SimpleUserInput.addMethodBoolean("Is-Completed");
            System.out.println("Rewards: ");
            reward = getEditor().getQuestItemEditor().startQuestItemEditor(null);
            System.out.println("FulFillmentItems: ");
            fulfillmentItems = getEditor().getQuestItemEditor().startQuestItemEditor(null);
            fulfillmentKill = SimpleUserInput.addMethod("CharacterName for fulFillmentKill");
            accepted = SimpleUserInput.addMethodBoolean("Accepted");
            talkedOnce = SimpleUserInput.addMethodBoolean("Talked-Once");
            autoComplete = SimpleUserInput.addMethodBoolean("Auto-Complete");
            mainQuest = SimpleUserInput.addMethodBoolean("Main-Quest");
            points = SimpleUserInput.addMethodInt("Points");
            removeFulfillmentItems = SimpleUserInput.addMethodBoolean("Remove fulFillmentItems");
            d = SimpleUserInput.storeDialogue("Quest");
        } while (d == Decision.AGAIN);
        if (d == Decision.SAVE) {
            return new Quest(name, textStart, textAccept, textMid, textEnd, completed, reward, fulfillmentItems,
                    accepted, talkedOnce, mainQuest, fulfillmentKill, autoComplete, removeFulfillmentItems, points);
        } else {
            return null;
        }
    }

    // TODO: add Tests
    public List<Quest> editQuestMenu(List<Quest> quests) {
        if (null == quests) {
            quests = new ArrayList<>();
        }
        if (quests.size() > 0) {
            System.out.println("Please enter the name of the Quest you want to edit: ");
            String input = SimpleUserInput.scan();
            List<Quest> resultList = quests.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Quest result = resultList.get(0);
                Quest edit = editQuest(result);
                if (null != edit) {
                    quests.remove(result);
                    quests.add(edit);
                    getEditor().setChanged();
                } else {
                    System.out.println("Editing aborted!");
                }
            } else {
                System.out.println("There is no Quest with this name!");
            }
        } else {
            System.out.println("There are no Quests to edit!");
        }
        return quests;
    }

    // TODO: add Tests
    public Quest editQuest(Quest quest) {
        if (null != quest) {
            Decision d = Decision.ABBORT;
            String name, textStart, textAccept, textMid, textEnd, fulfillmentKill;
            Boolean completed, accepted, talkedOnce, autoComplete, mainQuest, removeFulfillmentItems;
            List<QuestItem> reward, fulfillmentItems;
            int points;
            do {
                name = SimpleUserInput.editMethod("Name", quest.getName());
                textStart = SimpleUserInput.editMethod("TextStart", quest.getTextStart());
                textAccept = SimpleUserInput.editMethod("TextAccept", quest.getTextAccept());
                textMid = SimpleUserInput.editMethod("TextMid", quest.getTextMid());
                textEnd = SimpleUserInput.editMethod("TextEnd", quest.getTextEnd());
                completed = SimpleUserInput.editMethod("Is-Completed", quest.isCompleted());
                System.out.println("Edit Rewart QuestItems: ");
                reward = getEditor().getQuestItemEditor().startQuestItemEditor(quest.getReward());
                System.out.println("Edit Quest FulFillmentItems: ");
                fulfillmentItems = getEditor().getQuestItemEditor().startQuestItemEditor(quest.getFulfillmentItems());
                fulfillmentKill = SimpleUserInput.editMethod("FulfillmentKill", quest.getFulfillmentKill());
                accepted = SimpleUserInput.editMethod("Accepted", quest.isAccepted());
                talkedOnce = SimpleUserInput.editMethod("Talked-Once", quest.isTalkedOnce());
                autoComplete = SimpleUserInput.editMethod("Auto-Complete", quest.isAutoComplete());
                mainQuest = SimpleUserInput.editMethod("Main-Quest", quest.isMainQuest());
                points = SimpleUserInput.editMethod("Points", quest.getPoints());
                removeFulfillmentItems = SimpleUserInput.editMethod("Remove fulFillmentItems",
                        quest.isRemoveFulfillmentItems());
                d = SimpleUserInput.storeDialogue("Quest");
            } while (Decision.AGAIN == d);
            if (Decision.SAVE == d) {
                return new Quest(name, textStart, textAccept, textMid, textEnd, completed, reward, fulfillmentItems,
                        accepted, talkedOnce, mainQuest, fulfillmentKill, autoComplete, removeFulfillmentItems, points);
            } else {
                return quest;
            }
        }
        return quest;
    }

}