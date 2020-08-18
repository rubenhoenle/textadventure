package de.dhbw.project.nls.commands;

import de.dhbw.project.nls.DataStorage;

public abstract class Command {

    public abstract void execute();

    public abstract void setData(DataStorage data);

    public abstract String[] getPattern();
}