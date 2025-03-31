package janggi.view.command;

public class GameSaveCommand implements Command {

    @Override
    public CommandType getType() {
        return CommandType.SAVE;
    }
}
