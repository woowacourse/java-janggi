package janggi.command;

import janggi.GameContext;
import janggi.service.JanggiService;

public class QuitCommand implements Command {

    @Override
    public void execute(final GameContext context, final JanggiService service) {
    }

    @Override
    public boolean isExitCommand() {
        return true;
    }

    @Override
    public CommandType getType() {
        return CommandType.QUIT;
    }
}
