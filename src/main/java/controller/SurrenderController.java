package controller;

import domain.Game;
import view.InputView;
import view.OutputView;

public class SurrenderController implements GameCommand {

    @Override
    public void execute(InputView inputView, OutputView outputView, Game game) {
        outputView.printSurrenderMessage(game.getSide());
        game.end();
    }
}
