package janggi.command;

import janggi.GameContext;
import janggi.service.JanggiService;
import janggi.view.OutputView;

public interface Command {

    void execute(GameContext context, OutputView outputView, JanggiService service);

    boolean isExitCommand();
}

