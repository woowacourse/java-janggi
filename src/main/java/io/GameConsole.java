package io;

import domain.game.JanggiGame;
import domain.vo.Arrangement;
import domain.vo.Team;

public class GameConsole {
    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiGame janggiGame;

    public GameConsole() {
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.janggiGame = new JanggiGame();
    }

    public void run() {
        outputView.printSetupTable(Team.HAN);
        Arrangement hanArrangement = Arrangement.toArrangement(inputView.readSetupCommand());

        outputView.printSetupTable(Team.CHO);
        Arrangement choArrangement = Arrangement.toArrangement(inputView.readSetupCommand());

    }
}
