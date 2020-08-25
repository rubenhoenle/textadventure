package de.dhbw.project.alienfont;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.dhbw.project.Constants;

public class AlienFont {
	static Map<String, String> alienltrs = new HashMap<String, String>(148, 0.75f);
	static {
		
		alienltrs.put("a-1","   ");    alienltrs.put("b-1","###");    alienltrs.put("c-1","###");    alienltrs.put("d-1","###");
		alienltrs.put("a-2","###");    alienltrs.put("b-2","   ");    alienltrs.put("c-2","   ");    alienltrs.put("d-2","   ");
		alienltrs.put("a-3","   ");    alienltrs.put("b-3","   ");    alienltrs.put("c-3","   ");    alienltrs.put("d-3","   ");
		alienltrs.put("a-4","   ");    alienltrs.put("b-4","   ");    alienltrs.put("c-4","   ");    alienltrs.put("d-4","   ");

		alienltrs.put("e-1","   ");    alienltrs.put("f-1","###");    alienltrs.put("g-1","###");    alienltrs.put("h-1","###");
		alienltrs.put("e-2","###");    alienltrs.put("f-2","   ");    alienltrs.put("g-2","   ");    alienltrs.put("h-2","   ");
		alienltrs.put("e-3","###");    alienltrs.put("f-3","   ");    alienltrs.put("g-3","   ");    alienltrs.put("h-3","   ");
		alienltrs.put("e-4","###");    alienltrs.put("f-4","   ");    alienltrs.put("g-4","   ");    alienltrs.put("h-4","   ");

		alienltrs.put("i-1","   ");    alienltrs.put("j-1","###");    alienltrs.put("k-1","###");    alienltrs.put("l-1","###");
		alienltrs.put("i-2","###");    alienltrs.put("j-2","   ");    alienltrs.put("k-2","   ");    alienltrs.put("l-2","   ");
		alienltrs.put("i-3","#  ");    alienltrs.put("j-3","   ");    alienltrs.put("k-3","   ");    alienltrs.put("l-3","   ");
		alienltrs.put("i-4","#  ");    alienltrs.put("j-4","   ");    alienltrs.put("k-4","   ");    alienltrs.put("l-4","   ");

		alienltrs.put("m-1","###");    alienltrs.put("n-1","###");    alienltrs.put("o-1","   ");    alienltrs.put("p-1","###");
		alienltrs.put("m-2","   ");    alienltrs.put("n-2","   ");    alienltrs.put("o-2","###");    alienltrs.put("p-2","   ");
		alienltrs.put("m-3","   ");    alienltrs.put("n-3","   ");    alienltrs.put("o-3","# #");    alienltrs.put("p-3","   ");
		alienltrs.put("m-4","   ");    alienltrs.put("n-4","   ");    alienltrs.put("o-4","###");    alienltrs.put("p-4","   ");

		alienltrs.put("q-1","###");    alienltrs.put("r-1","###");    alienltrs.put("s-1","###");    alienltrs.put("t-1","###");
		alienltrs.put("q-2","   ");    alienltrs.put("r-2","   ");    alienltrs.put("s-2","   ");    alienltrs.put("t-2","   ");
		alienltrs.put("q-3","   ");    alienltrs.put("r-3","   ");    alienltrs.put("s-3","   ");    alienltrs.put("t-3","   ");
		alienltrs.put("q-4","   ");    alienltrs.put("r-4","   ");    alienltrs.put("s-4","   ");    alienltrs.put("t-4","   ");

		alienltrs.put("u-1","   ");    alienltrs.put("v-1","###");    alienltrs.put("w-1","###");    alienltrs.put("x-1","###");
		alienltrs.put("u-2","###");    alienltrs.put("v-2","   ");    alienltrs.put("w-2","   ");    alienltrs.put("x-2","   ");
		alienltrs.put("u-3","#  ");    alienltrs.put("v-3","   ");    alienltrs.put("w-3","   ");    alienltrs.put("x-3","   ");
		alienltrs.put("u-4","###");    alienltrs.put("v-4","   ");    alienltrs.put("w-4","   ");    alienltrs.put("x-4","   ");

		alienltrs.put("y-1","###");    alienltrs.put("z-1","###");    alienltrs.put("0-1","# #");    alienltrs.put("1-1","# #");
		alienltrs.put("y-2","   ");    alienltrs.put("z-2","   ");    alienltrs.put("0-2","   ");    alienltrs.put("1-2","   ");
		alienltrs.put("y-3","   ");    alienltrs.put("z-3","   ");    alienltrs.put("0-3","   ");    alienltrs.put("1-3","   ");
		alienltrs.put("y-4","   ");    alienltrs.put("z-4","   ");    alienltrs.put("0-4","   ");    alienltrs.put("1-4","#  ");

		alienltrs.put("2-1","# #");    alienltrs.put("3-1","# #");    alienltrs.put("4-1","# #");    alienltrs.put("5-1","# #");
		alienltrs.put("2-2","   ");    alienltrs.put("3-2","   ");    alienltrs.put("4-2","   ");    alienltrs.put("5-2","   ");
		alienltrs.put("2-3","   ");    alienltrs.put("3-3","   ");    alienltrs.put("4-3","#  ");    alienltrs.put("5-3","# #");
		alienltrs.put("2-4","# #");    alienltrs.put("3-4","###");    alienltrs.put("4-4","###");    alienltrs.put("5-4","###");

		alienltrs.put("6-1","# #");    alienltrs.put("7-1","# #");    alienltrs.put("8-1","# #");    alienltrs.put("9-1","# #");
		alienltrs.put("6-2","   ");    alienltrs.put("7-2","#  ");    alienltrs.put("8-2","# #");    alienltrs.put("9-2","###");
		alienltrs.put("6-3","###");    alienltrs.put("7-3","###");    alienltrs.put("8-3","###");    alienltrs.put("9-3","###");
		alienltrs.put("6-4","###");    alienltrs.put("7-4","###");    alienltrs.put("8-4","###");    alienltrs.put("9-4","###");
		
		alienltrs.put("--1","   ");    alienltrs.put("ä-1"," # ");    alienltrs.put("ö-1"," # ");    alienltrs.put("ü-1"," # ");
		alienltrs.put("--2","   ");    alienltrs.put("ä-1","###");    alienltrs.put("ö-1","###");    alienltrs.put("ü-1","###");
		alienltrs.put("--3","###");    alienltrs.put("ä-1","   ");    alienltrs.put("ö-1","# #");    alienltrs.put("ü-1","#  ");
		alienltrs.put("--4","   ");    alienltrs.put("ä-1","   ");    alienltrs.put("ö-1","###");    alienltrs.put("ü-1","###");
		}

		public static void toAlien(String input) {AlienFont.toAlien(input, false);}
		public static void toAlien(String input, boolean displaychars) {
			String printstring = input.toLowerCase(Locale.ROOT);
			int i = 0;
			while (i < printstring.length()) {
				String description = "";
				String line1 = "";
				String line2 = "";
				String line3 = "";
				String line4 = "";
				
				for (int j = 0; j < Constants.ANCIENTFONT_AUTONL && i< printstring.length(); j++) {
					if (printstring.charAt(i) == '\n') {i++;break;}
					else if (j != 0 && printstring.charAt(i) == ' ')
					{
						description = description.concat("    ");
						line1 = line1.concat("    ");    line2 = line2.concat("    ");
						line3 = line3.concat("    ");	line4 = line4.concat("    ");
						i++;
					}
					
					else if (alienltrs.containsKey(printstring.charAt(i) + "-1")) {
						description = description.concat(" " + printstring.toUpperCase().charAt(i) + "   ");
						line1 = line1.concat(alienltrs.get(printstring.charAt(i) + "-1") + "  ");
						line2 = line2.concat(alienltrs.get(printstring.charAt(i) + "-2") + "  ");
						line3 = line3.concat(alienltrs.get(printstring.charAt(i) + "-3") + "  ");
						line4 = line4.concat(alienltrs.get(printstring.charAt(i) + "-4") + "  ");
						i++;
					}
				}
				if (displaychars) {System.out.println(description);}
				System.out.println(line1);
				System.out.println(line2);
				System.out.println(line3);
				System.out.println(line4);
				System.out.println();
			
			}
	
		}

}
