package domain.board;

import domain.board.formation.InitialFormation;
import domain.coordinate.Position;
import domain.piece.*;
import domain.state.Side;

import java.util.HashMap;
import java.util.Map;

public class BasicBoardInitializer implements BoardInitializer {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    private final InitialFormation hanSideInitialType;
    private final InitialFormation chuSideInitialType;

    public BasicBoardInitializer(InitialFormation hanSideInitialFormation, InitialFormation chuSideInitialFormation) {
        this.hanSideInitialType = hanSideInitialFormation;
        this.chuSideInitialType = chuSideInitialFormation;
    }

    public Map<Position, Piece> initialize() {
        Map<Position, Piece> pieceInitPlacements = new HashMap<>();

        initializeSidePieces(pieceInitPlacements, Side.HAN);
        hanSideInitialType.apply(pieceInitPlacements);
        initializeSidePieces(pieceInitPlacements, Side.CHU);
        chuSideInitialType.apply(pieceInitPlacements);
        initializeEmptyPiece(pieceInitPlacements);

        return pieceInitPlacements;
    }

    private void initializeEmptyPiece(Map<Position, Piece> pieceInitPlacements) {
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                pieceInitPlacements.putIfAbsent(new Position(i, j), EmptyPiece.getInstance());
            }
        }
    }

    private void initializeSidePieces(Map<Position, Piece> placements, Side side) {
        placements.put(new Position(getRow(0, side), 0), new Chariot(side));
        placements.put(new Position(getRow(0, side), 3), new Guard(side));
        placements.put(new Position(getRow(0, side), 5), new Guard(side));
        placements.put(new Position(getRow(0, side), 8), new Chariot(side));

        placements.put(new Position(getRow(1, side), 4), new King(side));

        placements.put(new Position(getRow(2, side), 1), new Cannon(side));
        placements.put(new Position(getRow(2, side), 7), new Cannon(side));

        placements.put(new Position(getRow(3, side), 0), new Pawn(side));
        placements.put(new Position(getRow(3, side), 2), new Pawn(side));
        placements.put(new Position(getRow(3, side), 4), new Pawn(side));
        placements.put(new Position(getRow(3, side), 6), new Pawn(side));
        placements.put(new Position(getRow(3, side), 8), new Pawn(side));
    }

    private int getRow(int row, Side side) {
        if (side == Side.HAN) {
            return row;
        }

        return ROW_SIZE - row;
    }
}
