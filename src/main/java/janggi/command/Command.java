package janggi.command;

import janggi.GameContext;
import janggi.service.JanggiService;

public interface Command {

    void execute(GameContext context, JanggiService service);

    boolean isExitCommand();

    CommandType getType();
}

