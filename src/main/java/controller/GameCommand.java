package controller;

import domain.Game;
import view.InputView;
import view.OutputView;

public interface GameCommand {

    void execute(InputView inputView, OutputView outputView, Game game);
}
