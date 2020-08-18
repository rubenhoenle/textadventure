package de.dhbw.project.nls.commands;

import java.util.List;

public class PutCommand extends AutoCommand {

    @PatternName(key = "item")
    private List<String> item;
    @PatternName(key = "target")
    private List<String> target;

    public List<String> getItem() {
        return item;
    }

    public List<String> getTarget() {
        return target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    @Override
    public void execute() {
        System.out.println("TEST PUT");
        System.out.println(item.get(0));
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(put|lay)[ down] <item>+ on <target>+"/*
                                                                      * , "(put down|lay down|drop) <item>+"
                                                                      */
        };
        return patterns;
    }
}