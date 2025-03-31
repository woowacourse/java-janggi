package janggi.view.command;

public class GameQuitCommand implements Command {

    @Override
    public CommandType getType() {
        return CommandType.QUIT;
    }
}
