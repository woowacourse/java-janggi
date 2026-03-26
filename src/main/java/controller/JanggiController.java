package controller;

import domain.piece.Team;
import view.InputView;

public class JanggiController {
    private final InputView inputView;

    public JanggiController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        int choFormationNumber = inputView.initialFormation(Team.CHO);
        int hanFormationNumber = inputView.initialFormation(Team.HAN);

    }
}
