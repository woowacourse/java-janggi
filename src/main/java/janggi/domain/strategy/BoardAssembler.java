package janggi.domain.strategy;

import janggi.domain.Side;
import janggi.domain.board.Intersection;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import janggi.domain.strategy.intersection.IntersectionInitializer;
import java.util.List;

public class BoardAssembler {

    private static final int DEFAULT_ROWS = 10;
    private static final int DEFAULT_COLS = 9;

    private final List<ArrangementStrategy> strategies;
    private final IntersectionInitializer intersectionInitializer;

    private BoardAssembler(List<ArrangementStrategy> strategies, IntersectionInitializer intersectionInitializer) {
        this.strategies = strategies;
        this.intersectionInitializer = intersectionInitializer;
    }

    public static BoardAssembler of(List<ArrangementStrategy> strategies,
                                    IntersectionInitializer intersectionInitializer) {
        return new BoardAssembler(strategies, intersectionInitializer);
    }

    public Intersection[][] assemble() {
        Piece[][] arrangement = new Piece[DEFAULT_ROWS][DEFAULT_COLS];
        setupCommonPieces(arrangement);
        applyStrategies(arrangement);

        Intersection[][] intersections = new Intersection[DEFAULT_ROWS][DEFAULT_COLS];
        intersectionInitializer.initialize(intersections);

        placePiecesOnIntersection(intersections, arrangement);

        return intersections;
    }

    private void placePiecesOnIntersection(Intersection[][] intersections, Piece[][] arrangement) {
        for (int row = 0; row < DEFAULT_ROWS; row++) {
            for (int col = 0; col < DEFAULT_COLS; col++) {
                Piece piece = arrangement[row][col];
                if (piece != null) {
                    intersections[row][col].place(piece);
                }
            }
        }
    }

    private void setupCommonPieces(Piece[][] arrangement) {
        setUpOneSide(arrangement, Side.HAN);
        setUpOneSide(arrangement, Side.CHO);
    }

    private void applyStrategies(Piece[][] arrangement) {
        strategies.forEach(strategy -> strategy.place(arrangement));
    }

    private void setUpOneSide(Piece[][] arrangement, Side side) {
        for (DefaultPieceFactory factory : DefaultPieceFactory.values()) {
            setUpPiece(arrangement, side, factory);
        }
    }

    private void setUpPiece(Piece[][] arrangement, Side side, DefaultPieceFactory factory) {
        for (int col : factory.getCols()) {
            int row = factory.getRow(side);
            arrangement[row][col] = factory.createPiece(side);
        }
    }

    private enum DefaultPieceFactory {
        CHA(0, List.of(0, 8), PieceType.CHA),
        MA(0, List.of(1, 7), PieceType.MA),
        SANG(0, List.of(2, 6), PieceType.SANG),
        SA(0, List.of(3, 5), PieceType.SA),
        GUNG(1, List.of(4), PieceType.GUNG),
        PO(2, List.of(1, 7), PieceType.PO),
        JOLBYEOUNG(3, List.of(0, 2, 4, 6, 8), PieceType.JOLBYEOUNG);

        private final int row;
        private final List<Integer> cols;
        private final PieceType pieceType;

        DefaultPieceFactory(int row, List<Integer> cols, PieceType pieceType) {
            this.row = row;
            this.cols = cols;
            this.pieceType = pieceType;
        }

        public Piece createPiece(Side side) {
            return pieceType.createPiece(side);
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
