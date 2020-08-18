package de.dhbw.project.nls.commands;

import de.dhbw.project.Constants;

public class HelpCommand extends AutoCommand {

    @Override
    public void execute() {
        System.out.println("Available commands: " + Constants.COMMAND_LIST);
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(info|help)" };
        return patterns;
    }

}
