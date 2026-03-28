package janggi.domain.board;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public enum InitialBoardInfo {
    HAN(Side.HAN, 0, 1, 2, 3, PieceType.HAN_SOLDIER),
    CHO(Side.CHO, 9, 8, 7, 6, PieceType.CHO_SOLDIER),
    ;

    final Side side;
    final int bottomRow;
    final int palaceRow;
    final int cannonRow;
    final int solderRow;
    final PieceType solderType;

    InitialBoardInfo(Side side, int bottomRow, int palaceRow, int cannonRow, int solderRow, PieceType solderType) {
        this.side = side;
        this.bottomRow = bottomRow;
        this.palaceRow = palaceRow;
        this.cannonRow = cannonRow;
        this.solderRow = solderRow;
        this.solderType = solderType;
    }

    public Map<Position, Piece> generateInitialPiecePositions() {
        Map<Position, Piece> initialPiecePositions = new HashMap<>();
        initialPiecePositions.putAll(initMajorPieces());
        initialPiecePositions.putAll(initPalaceAndCannons());
        initialPiecePositions.putAll(initSoldiers());
        return initialPiecePositions;
    }

    private Map<Position, Piece> initMajorPieces() {
        Map<Position, Piece> majorPieces = new HashMap<>();
        putPiece(majorPieces, bottomRow, 0, PieceType.CHARIOT, "0");
        putPiece(majorPieces, bottomRow, 8, PieceType.CHARIOT, "1");
        putPiece(majorPieces, bottomRow, 1, PieceType.ELEPHANT, "0");
        putPiece(majorPieces, bottomRow, 7, PieceType.ELEPHANT, "1");
        putPiece(majorPieces, bottomRow, 2, PieceType.HORSE, "0");
        putPiece(majorPieces, bottomRow, 6, PieceType.HORSE, "1");
        putPiece(majorPieces, bottomRow, 3, PieceType.GUARD, "0");
        putPiece(majorPieces, bottomRow, 5, PieceType.GUARD, "1");
        return majorPieces;
    }

    private Map<Position, Piece> initPalaceAndCannons() {
        Map<Position, Piece> palaceAndCannons = new HashMap<>();
        putPiece(palaceAndCannons, palaceRow, 4, PieceType.PALACE, "0");
        putPiece(palaceAndCannons, cannonRow, 1, PieceType.CANNON, "0");
        putPiece(palaceAndCannons, cannonRow, 7, PieceType.CANNON, "1");
        return palaceAndCannons;
    }

    private Map<Position, Piece> initSoldiers() {
        Map<Position, Piece> soldiers = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            putPiece(soldiers, solderRow, i * 2, this.solderType, String.valueOf(i));
        }
        return soldiers;
    }

    private void putPiece(Map<Position, Piece> targetMap, int row, int column, PieceType type, String number) {
        targetMap.put(new Position(row, column), new Piece(this.side, type, number));
    }
}
