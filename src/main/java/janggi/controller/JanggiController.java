package janggi.controller;

import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.dto.MoveCommand;
import janggi.domain.janggiGame.StartGameResponse;
import janggi.domain.piece.Team;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final JanggiService janggiService;

    public JanggiController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public void run() {
        StartGameResponse response = janggiService.startGame();
        JanggiGame janggiGame = response.game();

        if (response.isResumed()) {
            outputView.printResumed();
        } else {
            outputView.printIntroduce();
        }

        outputView.printBoard(janggiGame.getBoard());

        while (!janggiGame.isFinished()) {
            Team currentTeam = janggiGame.getCurrentTeam();

            int option = inputView.readTurnBehavior(currentTeam);

            if (option == 1) {
                MoveCommand moveCommand = inputView.readMovePositions(currentTeam);
                janggiService.playTurn(moveCommand, janggiGame);
                outputView.printBoard(janggiGame.getBoard());
            }

            if (option == 2) {
                janggiGame.skipTurn();
                outputView.skipTurn();
            }

            if (option == 3) {
                janggiGame.resign();
                outputView.printResign(currentTeam);
            }
        }

        janggiGame.decideWinner();
        outputView.printWinner(janggiGame.getWinner());
    }
}


