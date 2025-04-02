package janggi.command;

import janggi.GameContext;
import janggi.service.JanggiService;
import janggi.view.OutputView;

public class QuitCommand implements Command {

    @Override
    public void execute(final GameContext context, final OutputView outputView, final JanggiService service) {
        displayMessage(outputView);
    }

    private void displayMessage(final OutputView outputView) {
        outputView.displayQuit();
    }

    @Override
    public boolean isExitCommand() {
        return true;
    }
}
