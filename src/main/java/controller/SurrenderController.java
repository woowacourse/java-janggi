package controller;

import domain.Game;
import view.OutputView;

public class SurrenderController implements GameCommand {

    private final OutputView outputView;

    public SurrenderController(OutputView outputView) {
        this.outputView = outputView;
    }

    @Override
    public void execute(Game game) {
        outputView.printSurrenderMessage(game.getSide());
        game.end();
    }
}
