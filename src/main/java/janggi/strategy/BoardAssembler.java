package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;

public class BoardAssembler {

    private static final int DEFAULT_ROWS = 10;
    private static final int DEFAULT_COLS = 9;

    private final ArrangementStrategy hanStrategy;
    private final ArrangementStrategy choStrategy;

    private BoardAssembler(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        this.hanStrategy = hanStrategy;
        this.choStrategy = choStrategy;
    }

    public static BoardAssembler of(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        return new BoardAssembler(hanStrategy, choStrategy);
    }

    public Piece[][] assemble() {
        Piece[][] arrangement = new Piece[DEFAULT_ROWS][DEFAULT_COLS];

        hanStrategy.place(arrangement, Side.HAN);
        choStrategy.place(arrangement, Side.CHO);

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
