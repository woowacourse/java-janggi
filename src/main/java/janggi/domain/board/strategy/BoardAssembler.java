package janggi.domain.board.strategy;

import janggi.domain.board.GungSeong;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import java.util.Arrays;
import java.util.List;

public class BoardAssembler {

    private static final int DEFAULT_ROWS = 10;
    private static final int DEFAULT_COLS = 9;

    private final List<ArrangementStrategy> strategies;

    private BoardAssembler(List<ArrangementStrategy> strategies) {
        this.strategies = strategies;
    }

    public static BoardAssembler from(List<ArrangementStrategy> strategies) {
        return new BoardAssembler(strategies);
    }

    public Piece[][] assemble() {
        Piece[][] arrangement = new Piece[DEFAULT_ROWS][DEFAULT_COLS];

        GungSeong gungSeong = GungSeong.of(DEFAULT_ROWS, DEFAULT_COLS);
        PieceFactory pieceFactory = PieceFactory.of(gungSeong);

        setupEmptyPieces(arrangement);

        for (ArrangementStrategy strategy : strategies) {
            strategy.place(arrangement, pieceFactory);
        }

        return arrangement;
    }

    private void setupEmptyPieces(Piece[][] arrangement) {
        for (Piece[] row : arrangement) {
            Arrays.fill(row, EmptyPiece.getInstance());
        }
    }
}
