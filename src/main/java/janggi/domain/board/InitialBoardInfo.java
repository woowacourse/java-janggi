package janggi.domain.board;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
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

    public void setPieces(Map<Position, Piece> initialBoard) {
        initMajorPieces(initialBoard);
        initPalaceAndCannons(initialBoard);
        initSoldiers(initialBoard, solderType);
    }

    private void initMajorPieces(Map<Position, Piece> initialBoard) {
        put(initialBoard, bottomRow, 0, PieceType.CHARIOT, "0");
        put(initialBoard, bottomRow, 8, PieceType.CHARIOT, "1");
        put(initialBoard, bottomRow, 1, PieceType.ELEPHANT, "0");
        put(initialBoard, bottomRow, 7, PieceType.ELEPHANT, "1");
        put(initialBoard, bottomRow, 2, PieceType.HORSE, "0");
        put(initialBoard, bottomRow, 6, PieceType.HORSE, "1");
        put(initialBoard, bottomRow, 3, PieceType.GUARD, "0");
        put(initialBoard, bottomRow, 5, PieceType.GUARD, "1");
    }

    private void initPalaceAndCannons(Map<Position, Piece> initialBoard) {
        put(initialBoard, palaceRow, 4, PieceType.PALACE, "0");
        put(initialBoard, cannonRow, 1, PieceType.CANNON, "0");
        put(initialBoard, cannonRow, 7, PieceType.CANNON, "1");
    }

    private void initSoldiers(Map<Position, Piece> initialBoard, PieceType type) {
        for (int i = 0; i < 5; i++) {
            put(initialBoard, solderRow, i * 2, type, String.valueOf(i));
        }
    }

    private void put(Map<Position, Piece> initialBoard, int row, int column, PieceType type, String number) {
        initialBoard.put(new Position(row, column), new Piece(side, type, number));
    }
}
