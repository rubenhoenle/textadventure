package de.dhbw.project.levelEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.project.Quest;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.item.ItemList;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

//TODO: create Tests and adapt Tests for RoomEditor
public class EnemyEditor {

    private Editor editor;

    public EnemyEditor(Editor editor) {
        this.editor = editor;
    }

    public Editor getEditor() {
        return editor;
    }

    // TODO: write Tests
    public List<Enemy> startEnemyEditor(List<Enemy> enemys) {
        System.out.println("Enemy-Editor:");
        boolean goOn = false;
        if (null == enemys) {
            enemys = new ArrayList<>();
        }
        do {
            goOn = menu(enemys);
        } while (goOn);
        return enemys;
    }

    // TODO: write Tests
    public boolean menu(List<Enemy> enemys) {
        System.out.println(
                "Enter 'list' to list all Enemys in the current room 'add' to add new ones, 'edit' to edit one, 'inspect' to inspect a Enemy and 'delete' to delete one:");
        String input = SimpleUserInput.scan();
        switch (input) {
        case "list":
            listEnemys(enemys);
            return true;
        case "inspect":
            inspectEnemy(enemys);
            return true;
        case "add":
            Enemy result = createEnemy();
            if (null != result) {
                enemys.add(result);
            }
            return true;
        case "edit":
            enemys = editEnemyMenu(enemys);
            return true;
        case "delete":
            enemys = deleteEnemy(enemys);
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
    public void listEnemys(List<Enemy> enemys) {
        if ((null != enemys) && (enemys.size() > 0)) {
            System.out.println("Enemys in this room: ");
            enemys.forEach(e -> System.out.println("  -" + e.getName()));
        } else {
            System.out.println("There are no enemys in this room yet!");
        }
    }

    // TODO: write Tests
    public void inspectEnemy(List<Enemy> enemys) {
        if ((null != enemys) && (enemys.size() > 0)) {
            System.out.println("Please enter the name of the enemy you want to inspect:");
            String input = SimpleUserInput.scan();
            List<Enemy> resultList = enemys.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Enemy result = resultList.get(0);
                System.out.println("Name: " + result.getName());
                System.out.println("Where: " + result.getWhere());
                System.out.println("Health: " + result.getHealth());
                System.out.println("Strength: " + result.getStrength());
                System.out.println("StartStatement: " + result.getStartStatement());
                System.out.println("KillStatement: " + result.getKillStatement());
                System.out.println("Is killed: " + result.isKilled());
                System.out.println("Points: " + result.getPoints());
                System.out.println("DropItems:");
                getEditor().getItemListEditor().listItems(result.getDropItemList());
                System.out.println("Autoattack: " + result.isAutoAttack());
            } else {
                System.out.println("There are no enemys with this name in this room!");
            }
        } else {
            System.out.println("There are no enemys in this room yet!");
        }
    }

    // TODO: write Tests
    public Enemy createEnemy() {
        String name, where, startStatement, killStatement;
        int health, strength, points;
        boolean killed, autoAttack;
        ItemList dropItems;
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
            dropItems = getEditor().getItemListEditor().startItemListEditor(null);
            autoAttack = SimpleUserInput.addMethodBoolean("AutoAttack");
            d = SimpleUserInput.storeDialogue("Enemy");
        } while (d == Decision.AGAIN);
        if (d == Decision.SAVE) {
            return new Enemy(name, where, health, strength, startStatement, killStatement, killed, points, dropItems,
                    autoAttack);
        }
        return null;
    }

    // TODO: write Tests
    public List<Enemy> editEnemyMenu(List<Enemy> enemys) {
        if ((null != enemys) && (enemys.size() > 0)) {
            boolean goOn = false;
            System.out.println("Please enter the name of the enemy you want to edit: ");
            String input = SimpleUserInput.scan();
            List<Enemy> resultList = enemys.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Enemy result = resultList.get(0);
                Enemy edit = editEnemy(result);
                if (null != edit) {
                    enemys.remove(result);
                    enemys.add(edit);
                }
            } else {
                System.out.println("There is no enemy with this name in this room!");
            }
            return enemys;
        } else {
            System.out.println("There ar no enemys to edit in this room yet!");
            if (null == enemys) {
                return new ArrayList<Enemy>();
            } else {
                return enemys;
            }
        }
    }

    // TODO: write Tests
    public Enemy editEnemy(Enemy enemy) {
        if (null != enemy) {
            String name, where, startStatement, killStatement;
            int health, strength, points;
            boolean killed, autoAttack;
            ItemList dropItems;
            Decision d = Decision.ABBORT;
            do {
                name = SimpleUserInput.editMethod("Name", enemy.getName());
                where = SimpleUserInput.editMethod("Where", enemy.getWhere());
                health = SimpleUserInput.editMethod("Health", enemy.getHealth());
                strength = SimpleUserInput.editMethod("Strength", enemy.getStrength());
                startStatement = SimpleUserInput.editMethod("StartStatement", enemy.getStartStatement());
                killStatement = SimpleUserInput.editMethod("KillStatement", enemy.getKillStatement());
                killed = SimpleUserInput.editMethod("Is killed", enemy.isKilled());
                points = SimpleUserInput.editMethod("Points", enemy.getPoints());
                dropItems = getEditor().getItemListEditor().startItemListEditor(enemy.getDropItemList());
                autoAttack = SimpleUserInput.editMethod("AutoAttack", enemy.isAutoAttack());
                d = SimpleUserInput.storeDialogue("Enemy");
            } while (d == Decision.AGAIN);
            if (d == Decision.SAVE) {
                return new Enemy(name, where, health, strength, startStatement, killStatement, killed, points,
                        dropItems, autoAttack);
            } else {
                System.out.println("Editing aborted");
                return enemy;
            }
        }
        return enemy;
    }

    // TODO: write Tests
    public List<Enemy> deleteEnemy(List<Enemy> enemys) {
        if ((null != enemys) && (enemys.size() > 0)) {
            System.out.println("Please enter the name of the enemy you want to delete: ");
            String input = SimpleUserInput.scan();
            List<Enemy> resultList = enemys.stream().filter(e -> e.getName().equals(input))
                    .collect(Collectors.toList());
            if (resultList.size() > 0) {
                Enemy result = resultList.get(0);
                boolean delete = SimpleUserInput.deleteDialogue(result.getName(), result.getWhere());
                if (delete) {
                    enemys.remove(result);
                }
            } else {
                System.out.println("Deleteion aborted");
            }
            return enemys;
        } else if (null == enemys) {
            enemys = new ArrayList<>();
        }
        System.out.println("There are no enemys in this room to delete yet!");
        return enemys;
    }
}
