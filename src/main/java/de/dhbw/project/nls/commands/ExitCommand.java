package de.dhbw.project.nls.commands;

public class ExitCommand extends AutoCommand {

    @Override
    public void execute() {
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(quit|exit)" };
        return patterns;
    }

}
