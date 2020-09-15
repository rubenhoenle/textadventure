package de.dhbw.project.levelEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.project.Quest;
import de.dhbw.project.character.Friend;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

//TODO: create Tests and adapt Tests for RoomEditor
public class FriendEditor {

    private Editor editor;

    public FriendEditor(Editor editor) {
        this.editor = editor;
    }

    public Editor getEditor() {
        return editor;
    }

    // TODO: write Tests
    public List<Friend> startFriendEditor(List<Friend> friends) {
        boolean goOn = false;
        if (null == friends) {
            friends = new ArrayList<>();
        }
        do {
            goOn = menu(friends);
        } while (goOn);
        return friends;
    }

    // TODO: write Tests
    public boolean menu(List<Friend> friends) {
        System.out.println(
                "Enter 'list' to list all Friends in the current room 'add' to add new ones, 'edit' to edit one, 'inspect' to inspect a Friend and 'delete' to delete one:");
        String input = SimpleUserInput.scan();
        switch (input) {
        case "list":
            listFriends(friends);
            return true;
        case "inspect":
            inspectFriend(friends);
            return true;
        case "add":
            Friend result = createFriend();
            if (null != result) {
                friends.add(result);
            }
            return true;
        case "edit":
            friends = editFriendMenu(friends);
            return true;
        case "delete":
            friends = deleteFriend(friends);
            return true;
        case "quit":
            return false;
        case "q":
            return false;
        default:
            SimpleUserInput.printUserInput(input);
            System.out.println("Enter 'quit' to quit.");
            return true;
        }
    }

    // TODO: write Tests
    public void listFriends(List<Friend> friends) {
        if ((null != friends) && (friends.size() > 0)) {
            System.out.println("Friends in this room: ");
            friends.forEach(e -> System.out.println("  -" + e.getName()));
        } else {
            System.out.println("There are no friends in this room yet!");
        }
    }

    // TODO: write Tests
    public void inspectFriend(List<Friend> friends) {
        if ((null != friends) && (friends.size() > 0)) {
            System.out.println("Please enter the name of the friend you want to inspect:");
            String input = SimpleUserInput.scan();
            List<Friend> resultList = friends.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Friend result = resultList.get(0);
                System.out.println("Name: " + result.getName());
                System.out.println("Where: " + result.getWhere());
                System.out.println("Health: " + result.getHealth());
                System.out.println("Strength: " + result.getStrength());
                System.out.println("StartStatement: " + result.getStartStatement());
                System.out.println("KillStatement: " + result.getKillStatement());
                System.out.println("Is killed: " + result.isKilled());
                System.out.println("Points: " + result.getPoints());
                editor.getQuestEditor().listQuests(result.getQuests());
            } else {
                System.out.println("There are no friends with this name in this room!");
            }
        } else {
            System.out.println("There are no friends in this room yet!");
        }
    }

    // TODO: write Tests
    public Friend createFriend() {
        String name, where, startStatement, killStatement;
        int health, strength, points;
        boolean killed;
        List<Quest> quests;
        Decision d = Decision.ABBORT;
        do {
            name = SimpleUserInput.addMethod("Name");
            where = SimpleUserInput.addMethod("Where");
            health = SimpleUserInput.addMethodInt("Health");
            strength = SimpleUserInput.addMethodInt("Strength");
            startStatement = SimpleUserInput.addMethod("StartStatement");
            killStatement = SimpleUserInput.addMethod("KillStatement");
            killed = SimpleUserInput.addMethodBoolean("Is killed");
            points = SimpleUserInput.addMethodInt("Points");
            System.out.println("Quests:");
            quests = editor.getQuestEditor().startQuestsEditor(null);
            d = SimpleUserInput.storeDialogue("Friend");
        } while (d == Decision.AGAIN);
        if (d == Decision.SAVE) {
            return new Friend(name, where, health, strength, startStatement, killStatement, killed, points, quests);
        }
        return null;
    }

    // TODO: write Tests
    public List<Friend> editFriendMenu(List<Friend> friends) {
        if ((null != friends) && (friends.size() > 0)) {
            boolean goOn = false;
            System.out.println("Please enter the name of the friend you want to edit: ");
            String input = SimpleUserInput.scan();
            List<Friend> resultList = friends.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Friend result = resultList.get(0);
                Friend edit = editFriend(result);
                if (null != edit) {
                    friends.remove(result);
                    friends.add(edit);
                }
            } else {
                System.out.println("There is no friend with this name in this room!");
            }
            return friends;
        } else {
            System.out.println("There ar no friends to edit in this room yet!");
            if (null == friends) {
                return new ArrayList<Friend>();
            } else {
                return friends;
            }
        }
    }

    // TODO: write Tests
    public Friend editFriend(Friend friend) {
        if (null != friend) {
            String name, where, startStatement, killStatement;
            int health, strength, points;
            boolean killed;
            List<Quest> quests;
            Decision d = Decision.ABBORT;
            do {
                name = SimpleUserInput.editMethod("Name", friend.getName());
                where = SimpleUserInput.editMethod("Where", friend.getWhere());
                health = SimpleUserInput.editMethod("Health", friend.getHealth());
                strength = SimpleUserInput.editMethod("Strength", friend.getStrength());
                startStatement = SimpleUserInput.editMethod("StartStatement", friend.getStartStatement());
                killStatement = SimpleUserInput.editMethod("KillStatement", friend.getKillStatement());
                killed = SimpleUserInput.editMethod("Is killed", friend.isKilled());
                points = SimpleUserInput.editMethod("Points", friend.getPoints());
                quests = editor.getQuestEditor().startQuestsEditor(friend.getQuests());
                d = SimpleUserInput.storeDialogue("Friend");
            } while (d == Decision.AGAIN);
            if (d == Decision.SAVE) {
                return new Friend(name, where, health, strength, startStatement, killStatement, killed, points, quests);
            } else {
                System.out.println("Editing aborted");
                return friend;
            }
        }
        return friend;
    }

    // TODO: write Tests
    public List<Friend> deleteFriend(List<Friend> friends) {
        if ((null != friends) && (friends.size() > 0)) {
            System.out.println("Please enter the name of the friend you want to delete: ");
            String input = SimpleUserInput.scan();
            List<Friend> resultList = friends.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Friend result = resultList.get(0);
                boolean delete = SimpleUserInput.deleteDialogue(result.getName(), result.getWhere());
                if (delete) {
                    friends.remove(result);
                }
            } else {
                System.out.println("Deleteion aborted");
            }
            return friends;
        } else if (null == friends) {
            friends = new ArrayList<>();
        }
        System.out.println("There are no friends in this room to delete yet!");
        return friends;
    }
}
