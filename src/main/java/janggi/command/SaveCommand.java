package janggi.command;

import janggi.GameContext;
import janggi.service.JanggiService;
import janggi.view.OutputView;

public class SaveCommand implements Command {

    @Override
    public void execute(final GameContext context, final OutputView outputView, final JanggiService service) {
        service.saveGameContext(context.update(context.getPlayers()));
        displayMessage(outputView);
    }

    private void displayMessage(final OutputView outputView) {
        outputView.displaySave();
    }

    @Override
    public boolean isExitCommand() {
        return true;
    }
}


