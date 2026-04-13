package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import java.util.HashMap;
import java.util.Map;

public enum InitialPiecePlacement {

    CHO_SOLDIER_1(3, 0, CampType.CHO, PieceRule.SOLDIER),
    CHO_SOLDIER_2(3, 2, CampType.CHO, PieceRule.SOLDIER),
    CHO_SOLDIER_3(3, 4, CampType.CHO, PieceRule.SOLDIER),
    CHO_SOLDIER_4(3, 6, CampType.CHO, PieceRule.SOLDIER),
    CHO_SOLDIER_5(3, 8, CampType.CHO, PieceRule.SOLDIER),
    CHO_CHARIOT_LEFT(0, 0, CampType.CHO, PieceRule.CHARIOT),
    CHO_GUARD_LEFT(0, 3, CampType.CHO, PieceRule.GUARD),
    CHO_GUARD_RIGHT(0, 5, CampType.CHO, PieceRule.GUARD),
    CHO_CHARIOT_RIGHT(0, 8, CampType.CHO, PieceRule.CHARIOT),
    CHO_GENERAL(1, 4, CampType.CHO, PieceRule.GENERAL),
    CHO_CANNON_LEFT(2, 1, CampType.CHO, PieceRule.CANNON),
    CHO_CANNON_RIGHT(2, 7, CampType.CHO, PieceRule.CANNON),

    HAN_SOLDIER_1(6, 0, CampType.HAN, PieceRule.SOLDIER),
    HAN_SOLDIER_2(6, 2, CampType.HAN, PieceRule.SOLDIER),
    HAN_SOLDIER_3(6, 4, CampType.HAN, PieceRule.SOLDIER),
    HAN_SOLDIER_4(6, 6, CampType.HAN, PieceRule.SOLDIER),
    HAN_SOLDIER_5(6, 8, CampType.HAN, PieceRule.SOLDIER),
    HAN_CANNON_LEFT(7, 1, CampType.HAN, PieceRule.CANNON),
    HAN_CANNON_RIGHT(7, 7, CampType.HAN, PieceRule.CANNON),
    HAN_GENERAL(8, 4, CampType.HAN, PieceRule.GENERAL),
    HAN_CHARIOT_LEFT(9, 0, CampType.HAN, PieceRule.CHARIOT),
    HAN_GUARD_LEFT(9, 3, CampType.HAN, PieceRule.GUARD),
    HAN_GUARD_RIGHT(9, 5, CampType.HAN, PieceRule.GUARD),
    HAN_CHARIOT_RIGHT(9, 8, CampType.HAN, PieceRule.CHARIOT);

    private final Position position;
    private final Piece piece;

    InitialPiecePlacement(int row, int column, CampType campType, PieceRule pieceRule) {
        this.position = new Position(row, column);
        this.piece = new Piece(pieceRule, campType);
    }

    public static Board initialize(ElephantFormation hanFormation, ElephantFormation choFormation) {
        Map<Position, Piece> board = new HashMap<>();

        for (InitialPiecePlacement placement : values()) {
            board.put(placement.position, placement.piece);
        }
        board.putAll(hanFormation.placeElephantSetUpPieces());
        board.putAll(choFormation.placeElephantSetUpPieces());
        return new Board(board);
    }
}
