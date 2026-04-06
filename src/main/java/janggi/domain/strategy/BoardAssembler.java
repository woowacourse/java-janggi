package janggi.domain.strategy;

import janggi.domain.Intersection;
import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jolbyeong;
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
        CHA(0, List.of(0, 8), Cha::new),
        MA(0, List.of(1, 7), Ma::new),
        SANG(0, List.of(2, 6), Sang::new),
        SA(0, List.of(3, 5), Sa::new),
        GUNG(1, List.of(4), Gung::new),
        PO(2, List.of(1, 7), Po::new),
        JOL(3, List.of(0, 2, 4, 6, 8), Jolbyeong::new);

        private final int row;
        private final List<Integer> cols;
        private final Function<Side, Piece> pieceFactory;

        DefaultPieceFactory(int row, List<Integer> cols, Function<Side, Piece> pieceFactory) {
            this.row = row;
            this.cols = cols;
            this.pieceFactory = pieceFactory;
        }

        public Piece createPiece(Side side) {
            return pieceFactory.apply(side);
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
