package de.dhbw.project.levelEditor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.QuestItem;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.levelEditor.Decision;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SimpleUserInput.class, QuestItem.class })
public class QuestitemEditorTest {
    @Mock
    PrintStream out;

    @Mock
    Game game;

    @Mock
    QuestItem room;

    @Mock
    Player player;

    @Mock
    ItemListEditor itemListEditor;

    @Mock
    Editor editor;

    @Mock
    QuestItem questItem;

    @Spy
    QuestItemEditor spyQuestItemEditor = new QuestItemEditor(editor);

    @Before
    public void prepareTest() {
        System.setOut(out);
    }

    @Test
    public void test1StartQuestItemEditor() {
        // before
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);
        doReturn(false).when(spyQuestItemEditor).menu(liste);

        // when
        List<QuestItem> feedback = spyQuestItemEditor.startQuestItemEditor(liste);

        // then
        assertEquals(liste, feedback);
        verify(out).println(
                "Enter 'list' to list all QuestItems 'add' to add new ones, 'edit' to edit one, 'inspect' to inspect an QuestItem and 'delete' to delete one:");
    }

    @Test
    public void test2StartQuestItemEditorNull() {
        // before
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);
        doReturn(false).when(spyQuestItemEditor).menu(new ArrayList<QuestItem>());

        // when
        List<QuestItem> feedback = spyQuestItemEditor.startQuestItemEditor(null);

        // then
        assertEquals(new ArrayList<QuestItem>(), feedback);
        verify(out).println(
                "Enter 'list' to list all QuestItems 'add' to add new ones, 'edit' to edit one, 'inspect' to inspect an QuestItem and 'delete' to delete one:");
    }

    @Test
    public void test3MenuList() {
        // before
        String input = "list";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);
        doNothing().when(spyQuestItemEditor).listQuestItems(liste);

        // when
        boolean feedback = spyQuestItemEditor.menu(liste);

        // then
        assertEquals(true, feedback);
        verify(spyQuestItemEditor).listQuestItems(liste);
    }

    @Test
    public void test4MenuAdd() {
        // before
        String input = "add";
        QuestItem newItem = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);
        doReturn(newItem).when(spyQuestItemEditor).createQuestItem();
        when(spyQuestItemEditor.getEditor()).thenReturn(editor);

        // when
        boolean feedback = spyQuestItemEditor.menu(liste);

        // then
        assertEquals(true, feedback);
        verify(editor).setChanged();
        verify(spyQuestItemEditor).createQuestItem();
    }

    @Test // Edit Test
    public void test5MenuEdit() {
        // before
        String input = "edit";
        QuestItem newItem = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);
        doReturn(liste).when(spyQuestItemEditor).editQuestItemMenu(liste);

        // when
        boolean feedback = spyQuestItemEditor.menu(liste);

        // then
        assertEquals(true, feedback);
        verify(spyQuestItemEditor).editQuestItemMenu(liste);
    }

    @Test
    public void test6MenuInspect() {
        // before
        String input = "inspect";
        QuestItem newItem = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);
        doNothing().when(spyQuestItemEditor).inspectQuestItem(liste);
        when(spyQuestItemEditor.getEditor()).thenReturn(editor);

        // when
        boolean feedback = spyQuestItemEditor.menu(liste);

        // then
        assertEquals(true, feedback);
        verify(spyQuestItemEditor).inspectQuestItem(liste);
    }

    @Test
    public void test7MenuDelete() {
        // before
        String input = "delete";
        QuestItem newItem = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);
        doNothing().when(spyQuestItemEditor).deleteQuestItem(liste);
        when(spyQuestItemEditor.getEditor()).thenReturn(editor);

        // when
        boolean feedback = spyQuestItemEditor.menu(liste);

        // then
        assertEquals(true, feedback);
        verify(spyQuestItemEditor).deleteQuestItem(liste);
    }

    @Test
    public void test8MenuQuit() {
        // before
        String input = "quit";
        QuestItem newItem = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);

        // when
        boolean feedback = spyQuestItemEditor.menu(liste);

        // then
        assertEquals(false, feedback);
    }

    @Test
    public void test9MenuFailure() {
        // before
        String input = "quiafdasdfat";
        QuestItem newItem = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        List<QuestItem> liste = new ArrayList<>();
        liste.add(questItem);

        // when
        boolean feedback = spyQuestItemEditor.menu(liste);

        // then
        assertEquals(true, feedback);
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.printUserInput(input);
    }

    @Test
    public void test10ListQuestItemsSuccess() {
        // before
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");

        // when
        spyQuestItemEditor.listQuestItems(questItems);

        // then
        verify(out).println("  -" + q1.getName());
        verify(out).println("  -" + q2.getName());
    }

    @Test
    public void test11ListQuestItemNull() {
        // before
        // when
        spyQuestItemEditor.listQuestItems(null);

        // then
        verify(out).println("No QuestItems in this list!");
    }

    @Test
    public void test12deleteQuestitemYes() {
        // before
        String input = "q2Name";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getDescription()).thenReturn("q2Description");
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.deleteDialogue(q2.getName(), q2.getDescription())).thenReturn(true);

        // when
        spyQuestItemEditor.deleteQuestItem(questItems);

        // then
        assertEquals(1, questItems.size());
        verify(out).println("Please enter the name of the QuestItem you want to delete: ");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.deleteDialogue(q2.getName(), q2.getDescription());
    }

    @Test
    public void test13deleteQuestitemNo() {
        // before
        String input = "q2Name";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getDescription()).thenReturn("q2Description");
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.deleteDialogue(q2.getName(), q2.getDescription())).thenReturn(false);

        // when
        spyQuestItemEditor.deleteQuestItem(questItems);

        // then
        assertEquals(2, questItems.size());
        verify(out).println("Please enter the name of the QuestItem you want to delete: ");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.deleteDialogue(q2.getName(), q2.getDescription());
        verify(out).println("Deletion abborted!");
    }

    @Test
    public void test14deleteQuestitemNoItem() {
        // before
        String input = "q2Nameadf";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getDescription()).thenReturn("q2Description");
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.deleteDialogue(q2.getName(), q2.getDescription())).thenReturn(false);
        doNothing().when(spyQuestItemEditor).listQuestItems(questItems);

        // when
        spyQuestItemEditor.deleteQuestItem(questItems);

        // then
        assertEquals(2, questItems.size());
        verify(out).println("Please enter the name of the QuestItem you want to delete: ");
        verify(out).println("There is no QuestItem with this name!");
        verify(spyQuestItemEditor).listQuestItems(questItems);
    }

    @Test
    public void test14deleteQuestitemNull() {
        // before
        String input = "q2Nameadf";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getDescription()).thenReturn("q2Description");
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.deleteDialogue(q2.getName(), q2.getDescription())).thenReturn(false);
        doNothing().when(spyQuestItemEditor).listQuestItems(questItems);

        // when
        spyQuestItemEditor.deleteQuestItem(null);

        // then
        verify(out).println("Error: The QuestItemList is null!");
    }

    @Test
    public void test15CreateQuestItemSave() {
        // before
        PowerMockito.mockStatic(SimpleUserInput.class);
        String name = "testName";
        String description = "testDescription";
        String type = "testType";
        ItemState state = ItemState.ACTIVE;
        EquipmentType eTyp = EquipmentType.HEAD;
        int strength = 99;
        int addInventorySpace = 0;
        when(SimpleUserInput.addMethod("QuestItem Name")).thenReturn(name);
        when(SimpleUserInput.addMethod("Description")).thenReturn(description);
        when(SimpleUserInput.addMethod("Item-Typ(book/cloth/food/resource/weapon/tool)")).thenReturn(type);
        when(SimpleUserInput.addMethodInt("Strength")).thenReturn(strength);
        when(SimpleUserInput.addMethodInt("Inventory Space")).thenReturn(addInventorySpace);
        when(SimpleUserInput.addMethodEquipmentType("Equipment-Typ")).thenReturn(eTyp);
        when(SimpleUserInput.addMethodItemState("Item State")).thenReturn(state);

        when(SimpleUserInput.storeDialogue(anyString())).thenReturn(Decision.SAVE);

        // when
        QuestItem feedback = spyQuestItemEditor.createQuestItem();

        // then
        assertEquals(new QuestItem(name, description, state, strength, eTyp, type, addInventorySpace), feedback);
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethod("QuestItem Name");
        SimpleUserInput.addMethod("Description");
        SimpleUserInput.addMethod("Item-Typ(book/cloth/food/resource/weapon/tool)");
        SimpleUserInput.addMethodEquipmentType("Equipment-Typ");
        SimpleUserInput.addMethodItemState("Item State");
        SimpleUserInput.storeDialogue("Quest Item");
    }

    @Test
    public void test16CreateQuestItemAbbort() {
        // before
        PowerMockito.mockStatic(SimpleUserInput.class);
        String name = "testName";
        String description = "testDescription";
        String type = "testType";
        ItemState state = ItemState.ACTIVE;
        EquipmentType eTyp = EquipmentType.HEAD;
        int strength = 99;
        int addInventorySpace = 0;
        when(SimpleUserInput.addMethod("Questitem Name")).thenReturn(name);
        when(SimpleUserInput.addMethod("Description")).thenReturn(description);
        when(SimpleUserInput.addMethod("Item-Typ(book/cloth/food/resource/weapon/tool)")).thenReturn(type);
        when(SimpleUserInput.addMethodInt("Strength")).thenReturn(strength);
        when(SimpleUserInput.addMethodInt("Inventory Space")).thenReturn(addInventorySpace);
        when(SimpleUserInput.addMethodEquipmentType("Equipment-Typ")).thenReturn(eTyp);
        when(SimpleUserInput.addMethodItemState("Item State")).thenReturn(state);
        when(SimpleUserInput.storeDialogue(anyString())).thenReturn(Decision.CANCEL);
        // when
        QuestItem feedback = spyQuestItemEditor.createQuestItem();

        // then
        assertEquals(null, feedback);
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.storeDialogue("Quest Item");
    }

    @Test
    public void test17EditQuestItemMenuSuccess() {
        // before
        String input = "q2Name";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        QuestItem editetQI = mock(QuestItem.class);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn(input);
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        doReturn(editetQI).when(spyQuestItemEditor).editQuestItem(q2);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(spyQuestItemEditor.getEditor()).thenReturn(editor);
        doReturn(editetQI).when(spyQuestItemEditor).editQuestItem(q2);

        // when
        List<QuestItem> feedback = spyQuestItemEditor.editQuestItemMenu(questItems);

        // then
        verify(out).println("Please enter the name of the Questitem you want to edit:");
        verify(out, never()).println("There is no QuestItem with this name !");
        verify(spyQuestItemEditor).editQuestItem(q2);
        verify(editor).setChanged();
        assertEquals(true, feedback.contains(editetQI));
        assertEquals(false, feedback.contains(q2));
    }

    @Test
    public void test18EditQuestItemMenuFailure() {
        // before
        String input = "q2Nameaf";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        QuestItem editetQI = mock(QuestItem.class);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        doReturn(editetQI).when(spyQuestItemEditor).editQuestItem(q2);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(spyQuestItemEditor.getEditor()).thenReturn(editor);
        doReturn(editetQI).when(spyQuestItemEditor).editQuestItem(q2);

        // when
        List<QuestItem> feedback = spyQuestItemEditor.editQuestItemMenu(questItems);

        // then
        verify(out).println("Please enter the name of the Questitem you want to edit:");
        verify(out).println("There is no QuestItem with this name !");
        verify(editor, never()).setChanged();
        assertEquals(false, feedback.contains(editetQI));
        assertEquals(true, feedback.contains(q2));
        verify(spyQuestItemEditor).listQuestItems(questItems);
    }

    @Test
    public void test19EditQuestItemSave() {
        // before
        QuestItem q1 = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(q1.getName()).thenReturn("oldName");
        when(q1.getDescription()).thenReturn("oldDescription");
        when(q1.getType()).thenReturn("cloth");
        when(q1.getStrength()).thenReturn(83);
        when(q1.getExpandInventorySpace()).thenReturn(0);
        when(q1.getEquipmentType()).thenReturn(EquipmentType.HEAD);
        when(q1.getItemstate()).thenReturn(ItemState.ACTIVE);
        String name = "testName";
        String description = "testDescription";
        String type = "testType";
        ItemState state = ItemState.ACTIVE;
        EquipmentType eTyp = EquipmentType.HEAD;
        int strength = 99;
        int addInventorySpace = 0;
        when(SimpleUserInput.editMethod("Questitem Name",q1.getName())).thenReturn(name);
        when(SimpleUserInput.editMethod("Description",q1.getDescription())).thenReturn(description);
        when(SimpleUserInput.editMethod("Item-Typ(book/cloth/food/resource/weapon/tool)",q1.getType())).thenReturn(type);
        when(SimpleUserInput.editMethod("Strength",q1.getStrength())).thenReturn(strength);
        when(SimpleUserInput.editMethod("Inventory Space",q1.getExpandInventorySpace())).thenReturn(addInventorySpace);
        when(SimpleUserInput.editMethod("Equipment-Typ",q1.getEquipmentType())).thenReturn(eTyp);
        when(SimpleUserInput.editMethod("Item State",q1.getItemstate())).thenReturn(state);
        when(SimpleUserInput.storeDialogue(anyString())).thenReturn(Decision.SAVE);

        // when
        QuestItem feedback = spyQuestItemEditor.editQuestItem(q1);

        // then
        assertEquals(new QuestItem(name, description, state, strength, eTyp, type, addInventorySpace), feedback);
    }

    @Test
    public void test20EditQuestItemAbbort() {
        // before
        QuestItem q1 = mock(QuestItem.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(q1.getName()).thenReturn("oldName");
        when(q1.getDescription()).thenReturn("oldDescription");
        when(q1.getType()).thenReturn("cloth");
        when(q1.getStrength()).thenReturn(83);
        when(q1.getExpandInventorySpace()).thenReturn(0);
        when(q1.getEquipmentType()).thenReturn(EquipmentType.HEAD);
        when(q1.getItemstate()).thenReturn(ItemState.ACTIVE);
        String name = "testName";
        String description = "testDescription";
        String type = "testType";
        ItemState state = ItemState.ACTIVE;
        EquipmentType eTyp = EquipmentType.HEAD;
        int strength = 99;
        int addInventorySpace = 0;
        when(SimpleUserInput.editMethod("Questitem Name",q1.getName())).thenReturn(name);
        when(SimpleUserInput.editMethod("Description",q1.getDescription())).thenReturn(description);
        when(SimpleUserInput.editMethod("Item-Typ(book/cloth/food/resource/weapon/tool)",q1.getType())).thenReturn(type);
        when(SimpleUserInput.editMethod("Strength",q1.getStrength())).thenReturn(strength);
        when(SimpleUserInput.editMethod("Inventory Space",q1.getExpandInventorySpace())).thenReturn(addInventorySpace);
        when(SimpleUserInput.editMethod("Equipment-Typ",q1.getEquipmentType())).thenReturn(eTyp);
        when(SimpleUserInput.editMethod("Item State",q1.getItemstate())).thenReturn(state);
        when(SimpleUserInput.storeDialogue(anyString())).thenReturn(Decision.CANCEL);

        // when
        QuestItem feedback = spyQuestItemEditor.editQuestItem(q1);

        // then
        assertEquals(null, feedback);
    }

    @Test
    public void test21InspectQuestItemSuccess(){
        //before
        String input = "q2Name";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getDescription()).thenReturn("testDescription");
        when(q2.getItemstate()).thenReturn(ItemState.ACTIVE);
        when(q2.getStrength()).thenReturn(87);
        when(q2.getEquipmentType()).thenReturn(EquipmentType.HEAD);
        when(q2.getType()).thenReturn("cloth");
        when(q2.getExpandInventorySpace()).thenReturn(0);
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        spyQuestItemEditor.inspectQuestItem(questItems);

        //then
        verify(out).println("Name: "+q2.getName());
        verify(out).println("Description: "+q2.getDescription());
        verify(out).println("ItemState: "+q2.getItemstate());
        verify(out).println("Strength: "+q2.getStrength());
        verify(out).println("EquipmentType: "+q2.getEquipmentType());
        verify(out).println("Type: "+q2.getType());
        verify(out).println("Add-Inventoryspace: "+q2.getExpandInventorySpace());
    }

    @Test
    public void test22InspectQuestItemFailure(){
        //before
        String input = "q2Nameafa";
        PowerMockito.mockStatic(SimpleUserInput.class);
        QuestItem q1 = mock(QuestItem.class);
        QuestItem q2 = mock(QuestItem.class);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        List<QuestItem> questItems = new ArrayList<>();
        questItems.add(q1);
        questItems.add(q2);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        spyQuestItemEditor.inspectQuestItem(questItems);

        //then
        verify(out).println("There is no QuestItem with this name!");
    }
}
