package janggi.controller;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Board board = new Board(BoardInitializer.createBoard());
        JanggiGame janggiGame = new JanggiGame(board);

        outputView.printIntroduce();
        outputView.printBoard(board.getBoard());

        while (!janggiGame.isFinished()) {
            Team currentTeam = janggiGame.getCurrentTeam();

            int option = inputView.readTurnBehavior(currentTeam);

            if (option == 1) {
                janggiGame.playTurn();
                MoveCommand moveCommand = inputView.readMovePositions(currentTeam);
                board.move(moveCommand.getFrom(), moveCommand.getTo(), currentTeam);
                outputView.printBoard(board.getBoard());
            }

            if (option == 2) {
                janggiGame.skipTurn();
                outputView.skipTurn();
            }

            if (option == 3) {
                janggiGame.resign();
                outputView.printResign(currentTeam);
            }

            janggiGame.changeTurn();
        }

        janggiGame.decideWinner();
        outputView.printWinner(janggiGame.getWinner());
    }
}


