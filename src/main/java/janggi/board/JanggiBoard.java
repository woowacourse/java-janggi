package janggi.board;

import janggi.piece.Piece;
import janggi.view.OutputView;

import java.util.Map;

public class JanggiBoard {

    private final Map<Position, Piece> board;

    private JanggiBoard(final Map<Position, Piece> board) {
        this.board = board;
    }

    public static JanggiBoard initialize() {
        Map<Position, Piece> board = BoardInitializer.initialPieces();
        return new JanggiBoard(board);
    }

    public void printBoard() {
        OutputView outputView = new OutputView();
        outputView.printBoard(board);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

}
