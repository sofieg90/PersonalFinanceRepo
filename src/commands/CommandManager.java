package commands;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<Integer, Command> commandMap = new HashMap<>();

    public void registerCommand(int option, Command command) {

        commandMap.put(option, command);
    }

    public void executeCommand(int option) {
        Command command = commandMap.get(option);

        if (command != null) {
            command.execute();
        } else {
            System.out.println("Ogiltigt val. Försök igen.");
        }
    }
}
