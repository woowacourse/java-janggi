package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import java.util.List;
import java.util.function.Function;

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

        setupCommonPieces(arrangement);

        hanStrategy.place(arrangement, Side.HAN);
        choStrategy.place(arrangement, Side.CHO);

        setupEmptyPieces(arrangement);

        return arrangement;
    }

    private void setupCommonPieces(Piece[][] grid) {
        setUpOneSide(grid, Side.HAN);
        setUpOneSide(grid, Side.CHO);
    }

    private void setupEmptyPieces(Piece[][] arrangement) {
        for (int i = 0; i < DEFAULT_ROWS; i++) {
            for (int j = 0; j < DEFAULT_COLS; j++) {
                if (arrangement[i][j] == null) {
                    arrangement[i][j] = new EmptyPiece();
                }
            }
        }
    }

    private void setUpOneSide(Piece[][] grid, Side side) {
        for (DefaultPieceFactory factory : DefaultPieceFactory.values()) {
            setUpPiece(grid, side, factory);
        }
    }

    private void setUpPiece(Piece[][] grid, Side side, DefaultPieceFactory factory) {
        for (int col : factory.getCols()) {
            int row = factory.getRow(side);
            grid[row][col] = factory.createPiece(side);
        }
    }

    private enum DefaultPieceFactory {
        CHA(0, List.of(0, 8), Cha::new),
        MA(0, List.of(1, 7), Ma::new),
        SANG(0, List.of(2, 6), Sang::new),
        SA(0, List.of(3, 5), Sa::new),
        GUNG(1, List.of(4), Gung::new),
        PO(2, List.of(1, 7), Po::new),
        JOL(3, List.of(0, 2, 4, 6, 8), Jol::new);

        private final int row;
        private final List<Integer> cols;
        private final Function<Side, Piece> pieceClass;

        DefaultPieceFactory(int row, List<Integer> cols, Function<Side, Piece> pieceClass) {
            this.row = row;
            this.cols = cols;
            this.pieceClass = pieceClass;
        }

        public Piece createPiece(Side side) {
            return pieceClass.apply(side);
        }

        public int getRow(Side side) {
            if (side.equals(Side.CHO)) {
                return DEFAULT_ROWS - row - 1;
            }
            return row;
        }

        public List<Integer> getCols() {
            return cols;
        }
    }
}
