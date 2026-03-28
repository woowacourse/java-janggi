package controller;

import domain.position.Position;
import domain.game.Turn;
import domain.board.Board;
import domain.board.LeftGwimaFactory;
import domain.board.RightGwimaFactory;
import domain.board.WonangmaFactory;
import domain.board.YanggwimaFactory;
import domain.piece.Piece;
import domain.game.Team;
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
        int choFormationNumber = inputView.initialFormation(Team.CHO);
        int hanFormationNumber = inputView.initialFormation(Team.HAN);

        Board board = createBoard(choFormationNumber, hanFormationNumber);
        outputView.printBoard(board);

        Turn turn = Turn.first();
        while (true) {
            turn = playTurn(board, turn);
        }
    }

    private Board createBoard(int choFormationNumber, int hanFormationNumber) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(initialBoard(choFormationNumber, Team.CHO));
        pieces.putAll(initialBoard(hanFormationNumber, Team.HAN));
        return new Board(pieces);
    }

    private Turn playTurn(Board board, Turn turn) {
        List<String> movePositions = inputView.askMovePiecePoisiton(turn.current());
        Position src = Position.from(movePositions.get(0), movePositions.get(1));
        Position dest = Position.from(movePositions.get(2), movePositions.get(3));
        board.move(src, dest);
        outputView.printBoard(board);
        return turn.next();
    }

    private Map<Position, Piece> initialBoard(int input, Team team) {
        if (input == 1) {
            return new LeftGwimaFactory().createFormation(team);
        }

        if (input == 2) {
            return new RightGwimaFactory().createFormation(team);
        }

        if (input == 3) {
            return new WonangmaFactory().createFormation(team);
        }

        return new YanggwimaFactory().createFormation(team);
    }
}
