package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.JanggiGame;
import janggi.domain.board.BoardInitializer;
import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Board board = new Board(BoardInitializer.createBoard());
        JanggiGame janggiGame = new JanggiGame();

        outputView.printIntroduce();
        outputView.printBoard(board.getBoard());

        while (!janggiGame.isFinished()) {
            Team currentTeam = janggiGame.findCurrentTeam();
            MoveCommand moveCommand = inputView.readMovePositions(currentTeam);

            board.move(moveCommand.getFrom(), moveCommand.getTo(), currentTeam);
            janggiGame.changeTurn();
            outputView.printBoard(board.getBoard());
        }
    }
}


