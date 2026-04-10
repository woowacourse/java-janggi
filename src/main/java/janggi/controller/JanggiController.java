package janggi.controller;

import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;
import janggi.repositiory.game.GameRepository;
import janggi.repositiory.piece.PieceRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final JanggiService janggiService;

    public JanggiController(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.janggiService = JanggiService.startGame(gameRepository, pieceRepository);
    }

    public void run() {
        if (janggiService.isResumed()) {
            outputView.printResumed();
        } else {
            outputView.printIntroduce();
        }

        outputView.printBoard(janggiService.getBoard());

        while (!janggiService.isFinished()) {
            Team currentTeam = janggiService.getCurrentTeam();

            int option = inputView.readTurnBehavior(currentTeam);

            if (option == 1) {
                MoveCommand moveCommand = inputView.readMovePositions(currentTeam);
                janggiService.playTurn(moveCommand);
                outputView.printBoard(janggiService.getBoard());
            }

            if (option == 2) {
                janggiService.skipTurn();
                outputView.skipTurn();
            }

            if (option == 3) {
                janggiService.resign();
                outputView.printResign(currentTeam);
            }
        }

        outputView.printWinner(janggiService.decideWinner());
    }
}


