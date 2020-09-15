package de.dhbw.project.levelEditor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.time.format.SignStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.Game;
import de.dhbw.project.Room;
import de.dhbw.project.WayState;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(SimpleUserInput.class)
public class SimpleUserInputTest {
    
    @Mock
    PrintStream out;


    @Before
    public void prepareTest(){
        System.setOut(out);
    }

    @Test
    public void test1EditMethodString(){
        //before
        String testName = "testName";
        String testValue  = "testValue";
        String testInput = "testInput";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, testValue)).thenCallRealMethod();

        //when
        String feedback = SimpleUserInput.editMethod(testName, testValue);

        //then
        assertEquals(testInput, feedback);
        verify(out).println("Current " + testName + ": " + testValue);
        verify(out).println("Please enter the new " + testName + ":");
        
    }

    @Test
    public void test2EditMethodBooleanTrue(){
        //before
        String testName = "Boolean";
        Boolean testValue = true;
        String testInput = "true";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, testValue)).thenCallRealMethod();

        //when
        Boolean feedback = SimpleUserInput.editMethod(testName, testValue);


        //then
        assertEquals(true, feedback);
        verify(out).println("Current " + testName + ": " + testValue.toString());
        verify(out).println("Please enter the new " + testName + ":");
    }

    @Test
    public void test3EditMethodBooleanFalse(){
        //before
        String testName = "Boolean";
        Boolean testValue = true;
        String testInput = "false";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, testValue)).thenCallRealMethod();

        //when
        Boolean feedback = SimpleUserInput.editMethod(testName, testValue);

        //then
        assertEquals(false, feedback);
        verify(out).println("Current " + testName + ": " + testValue.toString());
        verify(out).println("Please enter the new " + testName + ":");
    }

    @Test
    public void test4EditMethodBoolean_currentValue(){
        //before
        String testName = "Boolean";
        Boolean testValue = true;
        String testInput = "falseTrueFalse";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, testValue)).thenCallRealMethod();

        //when
        Boolean feedback = SimpleUserInput.editMethod(testName, testValue);

        //then
        assertEquals(true, feedback);
        verify(out).println("Current " + testName + ": " + testValue.toString());
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the new " + testName + ":");
        verify(out,times(SimpleUserInput.maxAttempts)).println("You're input '"+ testInput +"' isn't a valid boolean!");
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter 'false' or 'true'.");
    }

    @Test
    public void test5EditMethodEquipmentTypeSuccess(){
        //before
        String testName = "testEquipmentType";
        EquipmentType currentValue = EquipmentType.HEAD;
        String testInput  = "UPPER_BODY";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, currentValue)).thenCallRealMethod();

        //when
        EquipmentType result = SimpleUserInput.editMethod(testName, currentValue);

        //then
        assertEquals(testInput, result.toString());
        verify(out).println("Current " + testName + ": " + currentValue.toString());
        verify(out,never()).println("The following types are allowed: ");
    }

    @Test
    public void test6EditMethodEquipmentTypeFailure(){
        //before
        String testName = "testEquipmentType";
        EquipmentType currentValue = EquipmentType.HEAD;
        String testInput  = "upperDingens";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, currentValue)).thenCallRealMethod();

        //when
        EquipmentType result = SimpleUserInput.editMethod(testName, currentValue);

        //then
        assertEquals(currentValue.toString(), result.toString());
        verify(out).println("Current " + testName + ": " + currentValue.toString());
        verify(out,times(SimpleUserInput.maxAttempts)).println("The following types are allowed: ");
    }

    @Test
    public void test7EditMethodIntegerSuccess(){
        //before
        String testName = "testInt";
        Integer currentValue = 5;
        String testInput  = "9999";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, currentValue)).thenCallRealMethod();

        //when
        Integer result = SimpleUserInput.editMethod(testName, currentValue);

        //then
        assertEquals(testInput.toString(), result.toString());
        verify(out).println("Current " + testName + ": " + currentValue.toString());
        verify(out,never()).println("You have to enter an integer! You've entered '" + testInput + "'.");
    }

    @Test
    public void test8EditMethodIntegerFailure(){
        //before
        String testName = "testInt";
        Integer currentValue = 5;
        String testInput  = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.editMethod(testName, currentValue)).thenCallRealMethod();

        //when
        Integer result = SimpleUserInput.editMethod(testName, currentValue);

        //then
        assertEquals(currentValue.toString(), result.toString());
        verify(out).println("Current " + testName + ": " + currentValue.toString());
        verify(out,times(SimpleUserInput.maxAttempts)).println("You have to enter an integer! You've entered '" + testInput + "'.");
    }

    @Test
    public void test9AddMethodIntSuccess(){
        //before
        String testName = "testInt";
        String testInput  = "9876";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodInt(testName)).thenCallRealMethod();

        //when
        Integer result = SimpleUserInput.addMethodInt(testName);

        //then
        assertEquals(testInput.toString(), result.toString());
        verify(out).println("Please enter the " + testName + ":");
        verify(out,never()).println("You have to enter an integer! You've entered '" + testInput + "'.");
    }

    @Test
    public void test10AddMethodIntFailure(){
        //before
        String testName = "testInt";
        String testInput  = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodInt(testName)).thenCallRealMethod();

        //when
        Integer result = SimpleUserInput.addMethodInt(testName);

        //then
        assertEquals("0", result.toString());
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the " + testName + ":");
        verify(out,times(SimpleUserInput.maxAttempts)).println("You have to enter an integer! You've entered '" + testInput + "'.");
    }

    @Test
    public void test11AddMethodBooleanSuccessTrue(){
        //before
        String testName = "testInt";
        String testInput  = "true";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodBoolean(testName)).thenCallRealMethod();

        //when
        Boolean result = SimpleUserInput.addMethodBoolean(testName);

        //then
        assertEquals(true, result);
        verify(out).println("Please enter the new " + testName + ":");
        verify(out,never()).println("Please enter 'false' or 'true'.");
    }

    @Test
    public void test12AddMethodBooleanSuccessFalse(){
        //before
        String testName = "testInt";
        String testInput  = "false";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodBoolean(testName)).thenCallRealMethod();

        //when
        Boolean result = SimpleUserInput.addMethodBoolean(testName);

        //then
        assertEquals(false, result);
        verify(out).println("Please enter the new " + testName + ":");
        verify(out,never()).println("Please enter 'false' or 'true'.");
    }

    @Test
    public void test13AddMethodBooleanFailure(){
        //before
        String testName = "testInt";
        String testInput  = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodBoolean(testName)).thenCallRealMethod();

        //when
        Boolean result = SimpleUserInput.addMethodBoolean(testName);

        //then
        assertEquals(false, result);
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the new " + testName + ":");
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter 'false' or 'true'.");
    }

    @Test
    public void test14AddMethod(){
        //before
        String testName = "testName";
        String testInput = "testInputText";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethod(testName)).thenCallRealMethod();

        //when
        String result = SimpleUserInput.addMethod(testName);

        //then
        assertEquals(testInput, result);
        verify(out).println("Please enter the " + testName + ":");
    }

    @Test
    public void test15AddMethodEquipmentTypeSuccess(){
        //before
        String testName = "testName";
        String testInput = "HEAD";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodEquipmentType(testName)).thenCallRealMethod();

        //when
        EquipmentType result = SimpleUserInput.addMethodEquipmentType(testName);

        //then
        assertEquals(testInput, result.toString());
        verify(out).println("Please enter the " + testName + ":");
        verify(out, never()).println("The following rooms are available:");
    }

    @Test
    public void test16AddMethodEquipmentTypeFailure(){
        //before
        String testName = "testName";
        String testInput = "HEAD,Shoulder,ShoesAndToes";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodEquipmentType(testName)).thenCallRealMethod();

        //when
        EquipmentType result = SimpleUserInput.addMethodEquipmentType(testName);

        //then
        assertEquals(null, result);
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the " + testName + ":");
        verify(out, times(SimpleUserInput.maxAttempts)).println("The following types are allowed: ");
    }

    @Test
    public void test17AddMethodRoomSuccess(){
        //before
        List<Room> rooms = new ArrayList<>();
        Game g = mock(Game.class);
        Room r = mock(Room.class);
        rooms.add(r);
        String testName = "testName";
        String testInput = "aRoomName";
        when(r.getName()).thenReturn(testInput);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodRoom(testName, rooms)).thenCallRealMethod();

        //then
        Room result = SimpleUserInput.addMethodRoom(testName, rooms);

        //then
        assertEquals(r.getName(), result.getName());
        verify(out).println("Please enter the " + testName + ":");
        verify(out,never()).println("The following rooms are available:");
    }

    @Test
    public void test18AddMethodRoomFailureNoRoom(){
        //before
        List<Room> rooms = new ArrayList<>();
        Room r = mock(Room.class);
        String testName = "testName";
        String testInput = "aRoomName";
        when(r.getName()).thenReturn(testInput);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodRoom(testName, rooms)).thenCallRealMethod();

        //then
        Room result = SimpleUserInput.addMethodRoom(testName, rooms);

        //then
        assertEquals(null, result);
        verify(out).println("There are no rooms available in this game.");
    }

    @Test
    public void test19AddMethodRoomFailureNoGame(){
        //before
        String testName = "testName";
        String testInput = "aRoomName";

        //then
        Room result = SimpleUserInput.addMethodRoom(testName, null);

        //then
        assertEquals(null, result);
        verify(out).println("Error: no rooms found!");
    }

    @Test
    public void test20AddMethodRoomFailureNoValidRoom(){
        //before
        List<Room> rooms = new ArrayList<>();
        Room r = mock(Room.class);
        String testName = "testName";
        String testInput = "aRoomName";
        String anotherName = "anotherRoomName";
        rooms.add(r);
        when(r.getName()).thenReturn(anotherName);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodRoom(testName, rooms)).thenCallRealMethod();

        //then
        Room result = SimpleUserInput.addMethodRoom(testName, rooms);

        //then
        assertEquals(null, result);
        verify(out,times(SimpleUserInput.maxAttempts)).println("There is no room available with the name '" + testInput + "'.");
        verify(out,times(SimpleUserInput.maxAttempts)).println("The following rooms are available:");
        verify(out,times(SimpleUserInput.maxAttempts)).println("  -" + r.getName());
    }

    @Test
    public void test21AddMethodWayStateSuccess(){
        //before
        String testName = "testName";
        String testInput = "ACTIVE";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodWayState(testName)).thenCallRealMethod();

        //then
        WayState result = SimpleUserInput.addMethodWayState(testName);

        assertEquals(testInput, result.toString());
        verify(out).println("Please enter the " + testName + ":");
        verify(out, never()).println("'" + testInput + "' isn't a valid WayState!");
    }

    @Test
    public void test22AddMethodWayStateFailure(){
        //before
        String testName = "testName";
        String testInput = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.addMethodWayState(testName)).thenCallRealMethod();

        //then
        WayState result = SimpleUserInput.addMethodWayState(testName);

        assertEquals(null, result);
        verify(out, times(SimpleUserInput.maxAttempts)).println("Please enter the " + testName + ":");
        verify(out, times(SimpleUserInput.maxAttempts)).println("'" + testInput + "' isn't a valid WayState!");
        verify(out, times(SimpleUserInput.maxAttempts)).println("Valid WayStates are: " + String.join(", ", WayState.getAllNames()));
    }

    @Test
    public void test23StoreDialogueStore(){
        //before
        String testName = "testName";
        String testInput = "store";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.storeDialogue(testName)).thenCallRealMethod();

        //when
        Decision d = SimpleUserInput.storeDialogue(testName);

        //then
        assertEquals("SAVE", d.toString());
        verify(out).println("Enter 'store' to store the " + testName
        + ", 'edit' to enter the details again or 'q' to this operation");
    }

    @Test
    public void test24StoreDialogueEdit(){
        //before
        String testName = "testName";
        String testInput = "edit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.storeDialogue(testName)).thenCallRealMethod();

        //when
        Decision d = SimpleUserInput.storeDialogue(testName);

        //then
        assertEquals("AGAIN", d.toString());
        verify(out).println("Enter 'store' to store the " + testName
        + ", 'edit' to enter the details again or 'q' to this operation");
    }

    @Test
    public void test25StoreDialogueQuit(){
        //before
        String testName = "testName";
        String testInput = "q";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.storeDialogue(testName)).thenCallRealMethod();

        //when
        Decision d = SimpleUserInput.storeDialogue(testName);

        //then
        assertEquals("ABBORT", d.toString());
        verify(out).println("Enter 'store' to store the " + testName
        + ", 'edit' to enter the details again or 'q' to this operation");
    }

    @Test
    public void test26StoreDialogueFailure(){
        //before
        String testName = "testName";
        String testInput = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.storeDialogue(testName)).thenCallRealMethod();

        //when
        Decision d = SimpleUserInput.storeDialogue(testName);

        //then
        assertEquals("ABBORT", d.toString());
        verify(out,times(SimpleUserInput.maxAttempts)).println("Enter 'store' to store the " + testName
        + ", 'edit' to enter the details again or 'q' to this operation");
    }

    @Test
    public void test27DeleteDialogueYes(){
        //before
        String testName = "testName";
        String testDescription = "testDescription";
        String testInput = "yes";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.deleteDialogue(testName, testDescription)).thenCallRealMethod();

        //then
        Boolean result = SimpleUserInput.deleteDialogue(testName, testDescription);

        assertEquals(true, result);
        verify(out).println("Are you sure, you want delete the " + testName + " " + testDescription + "? (yes/no)");
    }

    @Test
    public void test28DeleteDialogueNo(){
        //before
        String testName = "testName";
        String testDescription = "testDescription";
        String testInput = "no";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.deleteDialogue(testName, testDescription)).thenCallRealMethod();

        //then
        Boolean result = SimpleUserInput.deleteDialogue(testName, testDescription);

        assertEquals(false, result);
        verify(out).println("Are you sure, you want delete the " + testName + " " + testDescription + "? (yes/no)");
    }

    @Test
    public void test29DeleteDialogueFailure(){
        //before
        String testName = "testName";
        String testDescription = "testDescription";
        String testInput = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.deleteDialogue(testName, testDescription)).thenCallRealMethod();

        //then
        Boolean result = SimpleUserInput.deleteDialogue(testName, testDescription);

        assertEquals(false, result);
        verify(out,times(SimpleUserInput.maxAttempts)).println("Are you sure, you want delete the " + testName + " " + testDescription + "? (yes/no)");
        verify(out,times(SimpleUserInput.maxAttempts)).println("You've entered '" + testInput + "'.");
    }

    @Test
    public void test30EditMethodRoomNameSuccess(){
        //before
        List<Room> rooms = new ArrayList<>();
        String input = "testRoom";
        String name = "testName";
        String currentValue = "oldTestRoom";
        Game game = mock(Game.class);
        Room room = mock(Room.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        rooms.add(room);
        when(room.getName()).thenReturn(input);
        when(SimpleUserInput.editMethodRoomName(name, currentValue, rooms)).thenCallRealMethod();

        //when
        String feedback = SimpleUserInput.editMethodRoomName(name, currentValue, rooms);

        //then
        assertEquals(input, feedback);
        verify(out).println("Current " + name + ": "+ currentValue);
        verify(out).println("Please enter the new " + name + ":");
        verify(out,never()).println("In this game there is no room with the name: "+ input);
    }

    @Test
    public void test31EditMethodRoomNameFailure(){
        //before
        List<Room> roomList = new ArrayList<>();
        String input = "testRoomIrgendwas";
        String name = "testName";
        String currentValue = "oldTestRoom";
        Game game = mock(Game.class);
        Room room = mock(Room.class);
        Room r2 = mock(Room.class);
        when(r2.getName()).thenReturn("irgendEtwas");
        roomList.add(r2);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(room.getName()).thenReturn(input);
        when(SimpleUserInput.editMethodRoomName(name, currentValue, roomList)).thenCallRealMethod();

        //when
        String feedback = SimpleUserInput.editMethodRoomName(name, currentValue, roomList);

        //then
        assertEquals(currentValue, feedback);
        verify(out).println("Current " + name + ": "+ currentValue);
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the new " + name + ":");
        verify(out, times(SimpleUserInput.maxAttempts)).println("In this game there is no room with the name: "+ input);
    }

    @Test
    public void test32EditmethodItemStateSuccess(){
        //before
        String input = "ACTIVE";
        String name = "testName";
        ItemState currentVal = ItemState.INACTIVE;
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.editMethod(name, currentVal)).thenCallRealMethod();

        //when
        ItemState result = SimpleUserInput.editMethod(name, currentVal);

        //then
        assertEquals(input, result.toString());
        verify(out).println("Current "+ name + ": " + currentVal.toString());
        verify(out).println("Please enter the "+ name + ":");
    }

    @Test 
    public void test33EditmethodItemStateFailure(){
        //before
        String input = "ACTIVEasdf";
        String name = "testName";
        ItemState currentVal = ItemState.INACTIVE;
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.editMethod(name, currentVal)).thenCallRealMethod();

        //when
        ItemState result = SimpleUserInput.editMethod(name, currentVal);

        //then
        assertEquals(currentVal.toString(), result.toString());
        verify(out).println("Current "+ name + ": " + currentVal.toString());
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the "+ name + ":");
        verify(out,times(SimpleUserInput.maxAttempts)).println("The following types are allowed: ");
    }

    @Test 
    public void test34EditmethodItemStateNull(){
        //before
        String input = null;
        String name = null;
        ItemState currentVal = null;
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.editMethod(name, currentVal)).thenCallRealMethod();

        //when
        ItemState result = SimpleUserInput.editMethod(name, currentVal);

        //then
        assertEquals(currentVal, result);
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the "+ name + ":");
        verify(out,times(SimpleUserInput.maxAttempts)).println("The following types are allowed: ");
    }
    
    @Test 
    public void test35AddmethodItemStateSuccess(){
        //before
        String input = "ACTIVE";
        String name = "testName";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.addMethodItemState(name)).thenCallRealMethod();

        //when
        ItemState result = SimpleUserInput.addMethodItemState(name);

        //then
        assertEquals(input, result.toString());
        verify(out).println("Please enter the "+ name + ":");
    }

    @Test 
    public void test36AddmethodItemStateFailure(){
        //before
        String input = "ACTIVEasdf";
        String name = "testName";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.addMethodItemState(name)).thenCallRealMethod();

        //when
        ItemState result = SimpleUserInput.addMethodItemState(name);

        //then
        assertEquals(null, result);
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the "+ name + ":");
        verify(out,times(SimpleUserInput.maxAttempts)).println("The following types are allowed: ");
    }

    @Test
    public void test37AddmethodItemStateNull(){
        //before
        String input = null;
        String name = null;
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.addMethodItemState(name)).thenCallRealMethod();

        //when
        ItemState result = SimpleUserInput.addMethodItemState(name);

        //then
        assertEquals(null, result);
        verify(out,times(SimpleUserInput.maxAttempts)).println("Please enter the "+ name + ":");
        verify(out,times(SimpleUserInput.maxAttempts)).println("The following types are allowed: ");
    }

    @Test
    public void test38yesNoQuestionYes(){
        //before
        String question = "testQuestion?";
        String input = "yes";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.yesNoQuestion(question)).thenCallRealMethod();

        //when
        Boolean feedback = SimpleUserInput.yesNoQuestion(question);

        //then
        assertEquals(true, feedback);
        verify(out).println(question + " (yes/no)");
    }

    @Test
    public void test39yesNoQuestionNo(){
        //before
        String question = "testQuestion?";
        String input = "no";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.yesNoQuestion(question)).thenCallRealMethod();

        //when
        Boolean feedback = SimpleUserInput.yesNoQuestion(question);

        //then
        assertEquals(false, feedback);
        verify(out).println(question + " (yes/no)");
    }

    @Test
    public void test40yesNoQuestionFailure(){
        //before
        String question = "testQuestion?";
        String input = "nadfao";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.yesNoQuestion(question)).thenCallRealMethod();

        //when
        Boolean feedback = SimpleUserInput.yesNoQuestion(question);

        //then
        assertEquals(false, feedback);
        verify(out,times(SimpleUserInput.maxAttempts)).println(question + " (yes/no)");
    }
}
