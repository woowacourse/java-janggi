package controller;

import domain.JanggiGame;
import domain.Player;
import domain.Team;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        JanggiGame game = new JanggiGame(new Player(Team.HAN), new Player(Team.CHO));
        outputView.print(game.getBoard());
    }
}
