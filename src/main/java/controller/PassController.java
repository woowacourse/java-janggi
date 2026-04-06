package controller;

import domain.Game;
import view.InputView;
import view.OutputView;

public class PassController implements GameCommand {

    @Override
    public void execute(InputView inputView, OutputView outputView, Game game) {
        outputView.printTurnPassMessage(game.getSide());
        game.pass();
    }
}
