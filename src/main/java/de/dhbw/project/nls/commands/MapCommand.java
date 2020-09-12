package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Room;
import de.dhbw.project.Way;

import java.util.HashMap;

public class MapCommand extends AutoCommand {

    // unicode to draw the boxes
    private static final String HORIZONTAL = "\u2500";
    private static final String VERTICAL = "\u2502";
    private static final String CORNER_TL = "\u250c";
    private static final String CORNER_BL = "\u2514";
    private static final String CORNER_TR = "\u2510";
    private static final String CORNER_BR = "\u2518";
    private static final String CORNER_R = "\u2524";
    private static final String CROSSING_HU = "\u2534";
    private static final String CROSSING_HD = "\u252c";
    private static final String CROSSING_VR = "\u251c";
    private static final String CROSSING_VL = "\u2524";

    private Game game;

    public MapCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        // each box is 3 lines high and there are 2 lines to connect the 3 box rows -> 11 lines
        String[] map = { "", "", "", "", "", "", "", "", "", "", "" };
        Room currentRoom = game.getCurrentRoom();
        int longestName = currentRoom.getName().length();
        HashMap<String, String> d = new HashMap<>();
        // get longest room name for the box size and fill hashmap
        for (Way w : currentRoom.getRoomWayList()) {
            String name = game.getRoom(w.getTo()).isVisited() ? w.getTo() : "?";
            d.put(w.getDirection(), name);
            if (name.length() > longestName) {
                longestName = name.length();
            }
        }
        // get placeholder for an empty box
        String empty = createNoBoxSpace(longestName + 4);
        // create north and up lines
        for (int i = 0; i < 3; i++) {
            map[i] += " " + empty;
            map[8 + i] += " " + empty;
        }

        // add boxes for top row
        for (String direction : new String[] { "north", "up" }) {
            if (d.get(direction) != null) {
                String[] box = createBox(d.get(direction), longestName + 4, false, true, false, false, false, false);
                for (int i = 0; i < 3; i++) {
                    map[i] += " " + box[i];
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    map[i] += " " + empty;
                }
            }
        }
        // add boxes for bottom row
        for (String direction : new String[] { "south", "down" }) {
            if (d.get(direction) != null) {
                String[] box = createBox(d.get(direction), longestName + 4, true, false, false, false, false, false);
                for (int i = 0; i < 3; i++) {
                    map[8 + i] += " " + box[i];
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    map[8 + i] += " " + empty;
                }
            }
        }

        // create west, current room, east lines without loop because of different connector directions
        if (d.get("west") != null) {
            String[] box = createBox(d.get("west"), longestName + 4, false, false, false, true, false, false);
            for (int i = 0; i < 3; i++) {
                map[4 + i] += " " + box[i];
            }
        } else {
            for (int i = 0; i < 3; i++) {
                map[4 + i] += " " + empty;
            }
        }

        String[] center = createBox(currentRoom.getName(), longestName + 4, d.get("north") != null,
                d.get("south") != null, d.get("west") != null, d.get("east") != null, d.get("up") != null,
                d.get("down") != null);
        map[4] += " " + center[0];
        map[5] += (d.get("west") != null) ? HORIZONTAL : " ";
        map[5] += center[1];
        map[6] += " " + center[2];

        if (d.get("east") != null) {
            String[] box = createBox(d.get("east"), longestName + 4, false, false, true, false, false, false);
            map[4] += " " + box[0];
            map[5] += HORIZONTAL + box[1];
            map[6] += " " + box[2];
        } else {
            for (int i = 0; i < 3; i++) {
                map[4 + i] += " " + empty;
            }
        }

        // create vertical connections between boxes
        map[3] = createConnection(longestName + 4, d.get("up") != null, false, d.get("north") != null);
        map[7] = createConnection(longestName + 4, false, d.get("down") != null, d.get("south") != null);

        // print map
        for (String row : map) {
            System.out.println(row);
        }

    }

    private String lenString(String str, int length) {
        String line = "";
        for (int i = 0; i < length; i++) {
            line += str;
        }
        return line;
    }

    private String createNoBoxSpace(int boxSize) {
        boxSize = (boxSize % 2 != 0) ? boxSize : boxSize + 1;
        return lenString(" ", boxSize);
    }

    // create string the is between the top & middle and middle & bottom row
    private String createConnection(int boxSize, boolean up, boolean down, boolean center) {
        boxSize = (boxSize % 2 != 0) ? boxSize : boxSize + 1;
        String line = lenString(" ", boxSize + 2 + (boxSize / 2));
        line += center ? VERTICAL : " ";
        if (!(up || down))
            return line;
        line += lenString(" ", boxSize / 2 - 1);
        line += up ? CORNER_TL : CORNER_BL;
        line += lenString(HORIZONTAL, boxSize / 2 + 1);
        line += up ? CORNER_BR : CORNER_TR;
        return line;
    }

    private String[] createBox(String label, int boxSize, boolean top, boolean bottom, boolean left, boolean right,
            boolean up, boolean down) {
        boxSize = (boxSize % 2 != 0) ? boxSize : boxSize + 1;
        String[] lines = { "", "", "" };
        // corners left side
        lines[0] += CORNER_TL;
        lines[2] += CORNER_BL;
        // lines on top top & bottom with connectors if needed in the center of the box
        for (int i = 0; i < boxSize - 2; i++) {
            if (((boxSize) / 2) - 1 == i) { // connectors
                lines[0] += top ? CROSSING_HU : HORIZONTAL;
                lines[2] += bottom ? CROSSING_HD : HORIZONTAL;
            } else { // normal line
                lines[0] += HORIZONTAL;
                lines[2] += HORIZONTAL;
            }
        }
        // corners right side
        lines[0] += up ? CORNER_R : CORNER_TR;
        lines[2] += down ? CORNER_R : CORNER_BR;
        // center of box with label
        lines[1] = left ? CROSSING_VL : VERTICAL;
        lines[1] += lenString(" ", (boxSize - 1 - label.length()) / 2);
        lines[1] += label;
        lines[1] += lenString(" ", (boxSize - 2 - label.length()) / 2);
        lines[1] += right ? CROSSING_VR : VERTICAL;
        return lines;
    }

    @Override
    public String[] getPattern() {
        return new String[] { "map", "m" };
    }
}
