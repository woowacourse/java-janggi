package controller;

import domain.board.AbstractBoardFactory;
import domain.board.Board;
import domain.game.Team;
import domain.game.Turn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Board board = createBoard();
        outputView.printBoard(board);
        Turn turn = Turn.first();
        while (true) {
            turn = playTurn(board, turn);
        }
    }

    private Board createBoard() {
        int choFormationNumber = inputView.initialFormation(Team.CHO);
        int hanFormationNumber = inputView.initialFormation(Team.HAN);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(AbstractBoardFactory.from(choFormationNumber).createFormation(Team.CHO));
        pieces.putAll(AbstractBoardFactory.from(hanFormationNumber).createFormation(Team.HAN));
        return new Board(pieces);
    }

    private Turn playTurn(Board board, Turn turn) {
        while (true) {
            try {
                List<String> movePositions = inputView.askMovePiecePosition(turn.current());
                Position src = Position.from(movePositions.get(0), movePositions.get(1));
                Position dest = Position.from(movePositions.get(2), movePositions.get(3));
                board.move(src, dest);
                outputView.printBoard(board);
                return turn.next();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
