package janggi.domain.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import java.util.List;
import java.util.function.Function;

public class MaSangArrangementStrategy implements ArrangementStrategy {

    private static final List<Integer> COLS_OF_MA_SANG = List.of(1, 2, 6, 7);
    private static final PieceFactory FACTORY = PieceFactory.getInstance();

    private final Side side;
    private final List<PieceType> maSangArrangement;

    private MaSangArrangementStrategy(Side side, List<PieceType> maSangArrangement) {
        this.side = side;
        this.maSangArrangement = maSangArrangement;
    }

    public static MaSangArrangementStrategy of(Side side, List<PieceType> maSangArrangement) {
        return new MaSangArrangementStrategy(side, maSangArrangement);
    }

    @Override
    public void place(Piece[][] arrangement) {
        placeDefaultPieces(arrangement, side);
        placeVariablePieces(arrangement, side);
    }

    private void placeVariablePieces(Piece[][] arrangement, Side side) {
        int boardMaxLength = arrangement.length;
        int row = calculateInitialRow(boardMaxLength, side);

        for (int index = 0; index < COLS_OF_MA_SANG.size(); index++) {
            int col = COLS_OF_MA_SANG.get(index);
            PieceType pieceType = maSangArrangement.get(index);
            arrangement[row][col] = FACTORY.createActivePiece(pieceType, side);
        }
    }

    private int calculateInitialRow(int boardMaxLength, Side side) {
        if (side.equals(Side.HAN)) {
            return 0;
        }
        if (side.equals(Side.CHO)) {
            return boardMaxLength - 1;
        }
        throw new IllegalArgumentException("진영이 존재하지 않아 시작 행을 찾을 수 없습니다.");
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
        CHA(0, List.of(0, 8), pieceSide -> PieceType.CHA),
        SA(0, List.of(3, 5), pieceSide -> PieceType.SA),
        GUNG(1, List.of(4), pieceSide -> PieceType.GUNG),
        PO(2, List.of(1, 7), pieceSide -> PieceType.PO),
        JOLBYEONG(3, List.of(0, 2, 4, 6, 8), pieceSide -> {
            if (pieceSide == Side.HAN) {
                return PieceType.BYEONG;
            }
            if (pieceSide.equals(Side.CHO)) {
                return PieceType.JOL;
            }
            throw new UnsupportedOperationException("진영이 존재하지 않아 기물명을 정할 수 없습니다.");
        });

        private final int row;
        private final List<Integer> cols;
        private final Function<Side, PieceType> pieceTypeProvider;

        DefaultPieceFactory(int row, List<Integer> cols, Function<Side, PieceType> pieceTypeProvider) {
            this.row = row;
            this.cols = cols;
            this.pieceTypeProvider = pieceTypeProvider;
        }

        private Piece createPiece(Side side) {
            return FACTORY.createActivePiece(pieceTypeProvider.apply(side), side);
        }

        private int getRow(Side side, int height) {
            if (side.equals(Side.HAN)) {
                return row;
            }
            if (side.equals(Side.CHO)) {
                return height - row - 1;
            }
            throw new UnsupportedOperationException("진영이 존재하지 않아 시작행을 선택할 수 없습니다.");
        }

        private List<Integer> getCols() {
            return cols;
        }
    }
}
