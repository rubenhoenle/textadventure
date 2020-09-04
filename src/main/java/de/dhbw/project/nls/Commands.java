package de.dhbw.project.nls;

import de.dhbw.project.nls.commands.Command;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

public class Commands {
    private LanguageProcessor processor = new LanguageProcessor();
    private HashMap<FSA, Command> commands = new HashMap<FSA, Command>();

    public void register(Command command) {
        String[] patterns = command.getPattern();
        OrCompositeFsa topLevelFsa = OrCompositeFsa.create(processor);
        for (String pattern : patterns) {
            CompositeFsa.buildCompositeFsaFrom(pattern, topLevelFsa);
        }
        commands.put(topLevelFsa, command);
    }

    public void execute(String input) {
        boolean knownCommand = false;
        for (Entry<FSA, Command> entry : commands.entrySet()) {
            processor.setFsa(entry.getKey());
            Optional<DataStorage> result = processor.getCommandFrom(input);
            if (result.isPresent()) {
                Command command = entry.getValue();
                command.setData(result.get());
                command.execute();
                entry.getKey().reset();

                knownCommand = true;
                break;
            }
            entry.getKey().reset();
        }
        if (!knownCommand)
            System.out.println("Unknown command.");
    }
}
