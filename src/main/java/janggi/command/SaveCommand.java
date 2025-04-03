package janggi.command;

import janggi.GameContext;
import janggi.service.JanggiService;

public class SaveCommand implements Command {

    @Override
    public void execute(final GameContext context, final JanggiService service) {
        service.saveGameWithPieces(context);
    }

    @Override
    public boolean isExitCommand() {
        return true;
    }

    @Override
    public CommandType getType() {
        return CommandType.SAVE;
    }
}


