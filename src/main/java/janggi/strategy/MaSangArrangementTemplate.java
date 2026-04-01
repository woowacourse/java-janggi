package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jolbyeong;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import java.util.List;
import java.util.function.Function;

public abstract class MaSangArrangementTemplate implements ArrangementStrategy {

    @Override
    public void place(Piece[][] arrangement, Side side) {
        placeDefaultPieces(arrangement, side);
        placeVariablePieces(arrangement, side);
    }

    protected abstract void placeVariablePieces(Piece[][] arrangement, Side side);

    protected int calculateInitialRow(int boardMaxLength, Side side) {
        if (side.equals(Side.CHO)) {
            return boardMaxLength - 1;
        }
        return 0;
    }

    private void placeDefaultPieces(Piece[][] grid, Side side) {
        for (DefaultPieceFactory factory : DefaultPieceFactory.values()) {
            setUpPiece(grid, side, factory);
        }
    }

    private void setUpPiece(Piece[][] grid, Side side, DefaultPieceFactory factory) {
        int height = grid.length;
        for (int col : factory.getCols()) {
            int row = factory.getRow(side, height);
            grid[row][col] = factory.createPiece(side);
        }
    }

    private enum DefaultPieceFactory {
        CHA(0, List.of(0, 8), Cha::new),
        SA(0, List.of(3, 5), Sa::new),
        GUNG(1, List.of(4), Gung::new),
        PO(2, List.of(1, 7), Po::new),
        JOL(3, List.of(0, 2, 4, 6, 8), Jolbyeong::new);

        private final int row;
        private final List<Integer> cols;
        private final Function<Side, Piece> pieceClass;

        DefaultPieceFactory(int row, List<Integer> cols, Function<Side, Piece> pieceClass) {
            this.row = row;
            this.cols = cols;
            this.pieceClass = pieceClass;
        }

        private Piece createPiece(Side side) {
            return pieceClass.apply(side);
        }

        private int getRow(Side side, int height) {
            if (side.equals(Side.CHO)) {
                return height - row - 1;
            }
            return row;
        }

        private List<Integer> getCols() {
            return cols;
        }
    }
}
