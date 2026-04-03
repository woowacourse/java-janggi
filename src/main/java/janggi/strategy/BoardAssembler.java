package janggi.strategy;

import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
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

        for (ArrangementStrategy strategy : strategies) {
            strategy.place(arrangement);
        }

        setupEmptyPieces(arrangement);

        return arrangement;
    }

    private void setupEmptyPieces(Piece[][] arrangement) {
        for (int row = 0; row < DEFAULT_ROWS; row++) {
            for (int col = 0; col < DEFAULT_COLS; col++) {
                if (arrangement[row][col] == null) {
                    arrangement[row][col] = EmptyPiece.getInstance();
                }
            }
        }
    }
}
