package de.dhbw.project.levelEditor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.Quest;
import de.dhbw.project.QuestItem;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SimpleUserInput.class, Quest.class })
public class QuestsEditorTest {
    @Mock
    PrintStream out;

    @Mock
    Game game;

    @Mock
    QuestItemEditor questItemEditor;

    @Mock
    Player player;

    @Mock
    ItemListEditor itemListEditor;

    @Mock
    Editor editor;

    @Mock
    Quest quest;

    @Mock
    Quest q1;
    @Mock 
    Quest q2;

    @Mock
    List<QuestItem> questItems;

    List<Quest> quests;

    @Spy
    QuestsEditor spyQuestsEditor = new QuestsEditor(editor);

    @Before
    public void prepareTest() {
        System.setOut(out);
        quests = new ArrayList<>();
        quests.add(q1);
        quests.add(q2);
    }

    @Test
    public void test1StartQuestsEditorSuccess(){
        //before 
        doReturn(false).when(spyQuestsEditor).menu(quests);

        //when
        List<Quest> feedback = spyQuestsEditor.startQuestsEditor(quests);

        //then
        assertEquals(quests, feedback);
        verify(spyQuestsEditor).menu(quests);
    }

    @Test
    public void test2StartQuestsEditorNull(){
        //before 
        doReturn(false).when(spyQuestsEditor).menu(new ArrayList<Quest>());
        doReturn(editor).when(spyQuestsEditor).getEditor();

        //when
        List<Quest> feedback = spyQuestsEditor.startQuestsEditor(null);

        //then
        assertEquals(new ArrayList<Quest>(), feedback);
        verify(spyQuestsEditor).menu(new ArrayList<Quest>());
    }

    @Test
    public void test3MenuList(){
        //before
        String input = "list";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doNothing().when(spyQuestsEditor).listQuests(quests);

        //when
        boolean feedback = spyQuestsEditor.menu(quests);

        //then
        assertEquals(true, feedback);
        verify(spyQuestsEditor).listQuests(quests);
        verify(out).println( "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
    }

    @Test
    public void test4MenuInspect(){
        //before
        String input = "inspect";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doNothing().when(spyQuestsEditor).inspect(quests);

        //when
        boolean feedback = spyQuestsEditor.menu(quests);

        //then
        assertEquals(true, feedback);
        verify(spyQuestsEditor).inspect(quests);
        verify(out).println( "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
    }

    @Test
    public void test5MenuAdd(){
        //before
        String input = "add";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(quest).when(spyQuestsEditor).createQuest();
        doReturn(editor).when(spyQuestsEditor).getEditor();

        //when
        boolean feedback = spyQuestsEditor.menu(quests);

        //then
        assertEquals(true, feedback);
        assertEquals(true, quests.contains(quest));
        assertEquals(3, quests.size());
        verify(spyQuestsEditor).createQuest();
        verify(out).println( "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
    }

    @Test
    public void test6MenuEdit(){
        //before
        String input = "edit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(quests).when(spyQuestsEditor).editQuestMenu(quests);
        doReturn(editor).when(spyQuestsEditor).getEditor();

        //when
        boolean feedback = spyQuestsEditor.menu(quests);

        //then
        assertEquals(true, feedback);
        assertEquals(2, quests.size());
        verify(spyQuestsEditor).editQuestMenu(quests);
        verify(out).println( "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
    }

    @Test
    public void test7MenuDelete(){
        //before
        String input = "delete";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doNothing().when(spyQuestsEditor).delete(quests);
        doReturn(editor).when(spyQuestsEditor).getEditor();

        //when
        boolean feedback = spyQuestsEditor.menu(quests);

        //then
        assertEquals(true, feedback);
        assertEquals(2, quests.size());
        verify(spyQuestsEditor).delete(quests);
        verify(out).println( "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
    }

    @Test
    public void test8MenuQuit(){
        //before
        String input = "quit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyQuestsEditor.menu(quests);

        //then
        assertEquals(false, feedback);
        verify(out).println( "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
    }

    @Test
    public void test10MenuFailure(){
        //before
        String input = "quitsdfa";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyQuestsEditor.menu(quests);

        //then
        assertEquals(true, feedback);
        verify(out).println("Please enter 'quit' to quit the menu.");
        verify(out).println( "Please enter 'list' to list all Quests, 'inspect' to inspect, 'add' to add, 'edit' to edit Quests and 'delete' to delete quests.");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.printUserInput(input);
    }

    @Test
    public void test11ListQuestsSuccess(){
        //before
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");

        //when
        spyQuestsEditor.listQuests(quests);

        //then
        verify(out).println("QuestList:");
        verify(out).println("  -q1Name");
        verify(out).println("  -q2Name");
    }

    @Test
    public void test12ListQuestsNoItems(){
        //before

        //when
        spyQuestsEditor.listQuests(new ArrayList<Quest>());

        //then
        verify(out).println("QuestList:");
        verify(out).println("There are no Quests yet!");
    }

    @Test
    public void test13InspectSuccess(){
        //before
        String input = "q2Name";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(editor).when(spyQuestsEditor).getEditor();
        when(editor.getQuestItemEditor()).thenReturn(questItemEditor);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getTextStart()).thenReturn("textStart");
        when(q2.getTextAccept()).thenReturn("textAccept");
        when(q2.getTextMid()).thenReturn("textMid");
        when(q2.getTextEnd()).thenReturn("textEnd");
        when(q2.isCompleted()).thenReturn(false);
        when(q2.getReward()).thenReturn(questItems);
        when(q2.getFulfillmentItems()).thenReturn(questItems);
        when(q2.getFulfillmentKill()).thenReturn("Django unchained");
        when(q2.isAccepted()).thenReturn(false);
        when(q2.isTalkedOnce()).thenReturn(false);
        when(q2.isAutoComplete()).thenReturn(true);
        when(q2.isMainQuest()).thenReturn(false);
        when(q2.getPoints()).thenReturn(9000);
        when(q2.isRemoveFulfillmentItems()).thenReturn(true);

        //when
        spyQuestsEditor.inspect(quests);

        //then
        verify(out).println("Please enter the name of the quest you want to inspect!");
        verify(out).println("Name: " + q2.getName());
        verify(out).println("TextStart: "+ q2.getTextStart());
        verify(out).println("TestAccept: "+ q2.getTextAccept());
        verify(out).println("TextMid: "+q2.getTextMid());
        verify(out).println("TextEnd: "+ q2.getTextEnd());
        verify(out).println("Completed: "+q2.isCompleted());
        verify(out).println("Rewards: ");
        verify(questItemEditor,times(2)).listQuestItems(questItems);
        verify(out).println("FulfillmentItems: ");
        verify(out).println("FulfillmentKill: "+ q2.getFulfillmentKill());
        verify(out).println("Accepted: "+ q2.isAccepted());
        verify(out).println("TalkedOnce: "+q2.isTalkedOnce());
        verify(out).println("AutoComplete: "+ q2.isAutoComplete());
        verify(out).println("Is main Quest: "+ q2.isMainQuest());
        verify(out).println("Points: "+ q2.getPoints());
        verify(out).println("Remove FulFillmentItems: "+ q2.isRemoveFulfillmentItems());
    }

    @Test
    public void test14InspectFailure(){
        //before
        String input = "q2Nameaf";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(editor).when(spyQuestsEditor).getEditor();
        when(editor.getQuestItemEditor()).thenReturn(questItemEditor);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");

        //when
        spyQuestsEditor.inspect(quests);

        //then
        verify(out).println("Please enter the name of the quest you want to inspect!");
        verify(out).println("There is no quest with this name!");
        verify(out).println("The following quests are available:");
        verify(spyQuestsEditor).listQuests(quests);
    }

    @Test
    public void test15InspectNoQuests(){
        //before
        String input = "q2Nameaf";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        spyQuestsEditor.inspect(null);

        //then
        verify(out).println("There are not Quests to inspect!");
    }

    @Test
    public void test16DeleteYes(){
        //before
        String input = "q2Name";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(editor).when(spyQuestsEditor).getEditor();
        when(editor.getQuestItemEditor()).thenReturn(questItemEditor);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getTextStart()).thenReturn("q2TextStart");
        when(SimpleUserInput.deleteDialogue(q2.getName(), q2.getTextStart())).thenReturn(true);

        //when
        spyQuestsEditor.delete(quests);;

        //then
        assertEquals(1, quests.size());
        verify(out).println("Please enter the name of the Quest you want to delete: ");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.deleteDialogue(q2.getName(), q2.getTextStart());
    }

    @Test
    public void test16DeleteNo(){
        //before
        String input = "q2Name";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(editor).when(spyQuestsEditor).getEditor();
        when(editor.getQuestItemEditor()).thenReturn(questItemEditor);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getTextStart()).thenReturn("q2TextStart");
        when(SimpleUserInput.deleteDialogue(q2.getName(), q2.getTextStart())).thenReturn(false);

        //when
        spyQuestsEditor.delete(quests);;

        //then
        assertEquals(2, quests.size());
        verify(out).println("Please enter the name of the Quest you want to delete: ");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.deleteDialogue(q2.getName(), q2.getTextStart());
    }

    @Test
    public void test17DeleteNoSuitableItem(){
        //before
        String input = "q2Nsfame";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(editor).when(spyQuestsEditor).getEditor();
        when(editor.getQuestItemEditor()).thenReturn(questItemEditor);
        when(q1.getName()).thenReturn("q1Name");
        when(q2.getName()).thenReturn("q2Name");
        when(q2.getTextStart()).thenReturn("q2TextStart");
        when(SimpleUserInput.deleteDialogue(q2.getName(), q2.getTextStart())).thenReturn(false);

        //when
        spyQuestsEditor.delete(quests);;

        //then
        assertEquals(2, quests.size());
        verify(out).println("Please enter the name of the Quest you want to delete: ");
        verify(out).println("There is no Quest with this name!");
    }

    @Test
    public void test18DeleteNoQuestList(){
        //before
        String input = "q2Nsfame";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(editor).when(spyQuestsEditor).getEditor();

        //when
        spyQuestsEditor.delete(null);;

        //then
        verify(out,never()).println("Please enter the name of the Quest you want to delete: ");
        verify(out).println("There are no Quests to delete!");
    }

    /*
    @Test
    public void test19CreateQuestSave(){
        //before
        // TODO: add when-Then to QuestItemEditor
        PowerMockito.mockStatic(SimpleUserInput.class);
        String name = "testName";
        String textStart = "testTextStart";
        String textAccept = "testTextAccpet";
        String textMid = "testTextMid";
        String textEnd = "testTextEnd";
        String textFulFillmentKill = "testTextFulFillmentKill";
        boolean completed = false;
        boolean accepted = false;
        boolean talkedOnce = false;
        boolean autoComplete = true;
        boolean mainQuest = false;
        boolean isRemoveFulfillmentItems = true;
        int points = 9834;
        when(SimpleUserInput.storeDialogue("Quest")).thenReturn(Decision.SAVE);
        when(SimpleUserInput.addMethod("Name")).thenReturn(name);
        when(SimpleUserInput.addMethod("Text-Start")).thenReturn(textStart);
        when(SimpleUserInput.addMethod("Text-Accept")).thenReturn(textAccept);
        when(SimpleUserInput.addMethod("Text-Mid")).thenReturn(textMid);
        when(SimpleUserInput.addMethod("Text-End")).thenReturn(textEnd);
        when(SimpleUserInput.addMethodBoolean("Is-Completed")).thenReturn(completed);
        when(SimpleUserInput.addMethod("CharacterName for fulFillmentKill")).thenReturn(textFulFillmentKill);
        when(SimpleUserInput.addMethodBoolean("Accepted")).thenReturn(accepted);
        when(SimpleUserInput.addMethodBoolean("Talked-Once")).thenReturn(talkedOnce);
        when(SimpleUserInput.addMethodBoolean("Auto-Complete")).thenReturn(autoComplete);
        when(SimpleUserInput.addMethodBoolean("Main-Quest")).thenReturn(mainQuest);
        when(SimpleUserInput.addMethodInt("Points")).thenReturn(points);
        when(SimpleUserInput.addMethodBoolean("Remove fulFillmentItems")).thenReturn(isRemoveFulfillmentItems);
        doReturn(editor).when(spyQuestsEditor).getEditor();
        when(editor.getQuestItemEditor()).thenReturn(questItemEditor);
        when(questItemEditor.startQuestItemEditor(null)).thenReturn(questItems);
        Quest compareQuest =new Quest(name, textStart, textAccept, textMid, textEnd, completed, questItems, questItems, accepted, talkedOnce, mainQuest, textFulFillmentKill, autoComplete, isRemoveFulfillmentItems, points);
        
        //when
        Quest feedback = spyQuestsEditor.createQuest();
        boolean comparison =Objects.equals(compareQuest, feedback);

        //then
        assertEquals(true, comparison);
        //assertEquals(compareQuest, feedback);
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.storeDialogue("Quest");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.storeDialogue("Quest");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethod("Name");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethod("Text-Start");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethod("Text-Accept");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethod("Text-Mid");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethod("Text-End");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethodBoolean("Is-Completed");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethod("CharacterName for fulFillmentKill");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethodBoolean("Accepted");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethodBoolean("Talked-Once");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethodBoolean("Auto-Complete");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethodBoolean("Main-Quest");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethodInt("Points");
        PowerMockito.verifyStatic(SimpleUserInput.class);
        SimpleUserInput.addMethodBoolean("Remove fulFillmentItems");
        verify(questItemEditor,times(2)).startQuestItemEditor(null);

    }
    */
}
