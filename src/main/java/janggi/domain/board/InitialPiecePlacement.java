package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceStrategy;
import java.util.HashMap;
import java.util.Map;

public enum InitialPiecePlacement {

    CHO_SOLDIER_1(3, 0, Camp.CHO, PieceStrategy.SOLDIER),
    CHO_SOLDIER_2(3, 2, Camp.CHO, PieceStrategy.SOLDIER),
    CHO_SOLDIER_3(3, 4, Camp.CHO, PieceStrategy.SOLDIER),
    CHO_SOLDIER_4(3, 6, Camp.CHO, PieceStrategy.SOLDIER),
    CHO_SOLDIER_5(3, 8, Camp.CHO, PieceStrategy.SOLDIER),
    CHO_CHARIOT_LEFT(0, 0, Camp.CHO, PieceStrategy.CHARIOT),
    CHO_GUARD_LEFT(0, 3, Camp.CHO, PieceStrategy.GUARD),
    CHO_GUARD_RIGHT(0, 5, Camp.CHO, PieceStrategy.GUARD),
    CHO_CHARIOT_RIGHT(0, 8, Camp.CHO, PieceStrategy.CHARIOT),
    CHO_GENERAL(1, 4, Camp.CHO, PieceStrategy.GENERAL),
    CHO_CANNON_LEFT(2, 1, Camp.CHO, PieceStrategy.CANNON),
    CHO_CANNON_RIGHT(2, 7, Camp.CHO, PieceStrategy.CANNON),

    HAN_SOLDIER_1(6, 0, Camp.HAN, PieceStrategy.SOLDIER),
    HAN_SOLDIER_2(6, 2, Camp.HAN, PieceStrategy.SOLDIER),
    HAN_SOLDIER_3(6, 4, Camp.HAN, PieceStrategy.SOLDIER),
    HAN_SOLDIER_4(6, 6, Camp.HAN, PieceStrategy.SOLDIER),
    HAN_SOLDIER_5(6, 8, Camp.HAN, PieceStrategy.SOLDIER),
    HAN_CANNON_LEFT(7, 1, Camp.HAN, PieceStrategy.CANNON),
    HAN_CANNON_RIGHT(7, 7, Camp.HAN, PieceStrategy.CANNON),
    HAN_GENERAL(8, 4, Camp.HAN, PieceStrategy.GENERAL),
    HAN_CHARIOT_LEFT(9, 0, Camp.HAN, PieceStrategy.CHARIOT),
    HAN_GUARD_LEFT(9, 3, Camp.HAN, PieceStrategy.GUARD),
    HAN_GUARD_RIGHT(9, 5, Camp.HAN, PieceStrategy.GUARD),
    HAN_CHARIOT_RIGHT(9, 8, Camp.HAN, PieceStrategy.CHARIOT);

    private final Position position;
    private final Piece piece;

    InitialPiecePlacement(int row, int column, Camp camp, PieceStrategy pieceStrategy) {
        this.position = new Position(row, column);
        this.piece = new Piece(pieceStrategy, camp);
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
