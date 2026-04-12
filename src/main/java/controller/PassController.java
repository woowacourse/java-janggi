package controller;

import domain.Game;
import view.OutputView;

public class PassController implements GameCommand {

    private final OutputView outputView;

    public PassController(OutputView outputView) {
        this.outputView = outputView;
    }

    @Override
    public void execute(Game game) {
        outputView.printTurnPassMessage(game.getSide());
        game.pass();
    }
}
