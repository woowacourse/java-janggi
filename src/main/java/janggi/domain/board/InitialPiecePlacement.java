package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.piece.strategy.MoveStrategy;
import java.util.HashMap;
import java.util.Map;

public enum InitialPiecePlacement {
    CHO_SOLDIER_1(3, 0, Camp.CHO, Type.SOLDIER, null),
    CHO_SOLDIER_2(3, 2, Camp.CHO, Type.SOLDIER, null),
    CHO_SOLDIER_3(3, 4, Camp.CHO, Type.SOLDIER, null),
    CHO_SOLDIER_4(3, 6, Camp.CHO, Type.SOLDIER, null),
    CHO_SOLDIER_5(3, 8, Camp.CHO, Type.SOLDIER, null),
    CHO_CHARIOT_LEFT(0, 0, Camp.CHO, Type.CHARIOT, null),
    CHO_ELEPHANT_LEFT(0, 1, Camp.CHO, Type.ELEPHANT, null),
    CHO_HORSE_LEFT(0, 2, Camp.CHO, Type.HORSE, null),
    CHO_GUARD_LEFT(0, 3, Camp.CHO, Type.GUARD, null),
    CHO_GUARD_RIGHT(0, 5, Camp.CHO, Type.GUARD, null),
    CHO_ELEPHANT_RIGHT(0, 6, Camp.CHO, Type.ELEPHANT, null),
    CHO_HORSE_RIGHT(0, 7, Camp.CHO, Type.HORSE, null),
    CHO_CHARIOT_RIGHT(0, 8, Camp.CHO, Type.CHARIOT, null),
    CHO_GENERAL(1, 4, Camp.CHO, Type.GENERAL, null),
    CHO_CANNON_LEFT(2, 1, Camp.CHO, Type.CANNON, null),
    CHO_CANNON_RIGHT(2, 7, Camp.CHO, Type.CANNON, null),

    HAN_SOLDIER_1(6, 0, Camp.HAN, Type.SOLDIER, null),
    HAN_SOLDIER_2(6, 2, Camp.HAN, Type.SOLDIER, null),
    HAN_SOLDIER_3(6, 4, Camp.HAN, Type.SOLDIER, null),
    HAN_SOLDIER_4(6, 6, Camp.HAN, Type.SOLDIER, null),
    HAN_SOLDIER_5(6, 8, Camp.HAN, Type.SOLDIER, null),
    HAN_CANNON_LEFT(7, 1, Camp.HAN, Type.CANNON, null),
    HAN_CANNON_RIGHT(7, 7, Camp.HAN, Type.CANNON, null),
    HAN_GENERAL(8, 4, Camp.HAN, Type.GENERAL, null),
    HAN_CHARIOT_LEFT(9, 0, Camp.HAN, Type.CHARIOT, null),
    HAN_ELEPHANT_LEFT(9, 1, Camp.HAN, Type.ELEPHANT, null),
    HAN_HORSE_LEFT(9, 2, Camp.HAN, Type.HORSE, null),
    HAN_GUARD_LEFT(9, 3, Camp.HAN, Type.GUARD, null),
    HAN_GUARD_RIGHT(9, 5, Camp.HAN, Type.GUARD, null),
    HAN_ELEPHANT_RIGHT(9, 6, Camp.HAN, Type.ELEPHANT, null),
    HAN_HORSE_RIGHT(9, 7, Camp.HAN, Type.HORSE, null),
    HAN_CHARIOT_RIGHT(9, 8, Camp.HAN, Type.CHARIOT, null);

    private final int row;
    private final int column;
    private final Camp camp;
    private final Type type;
    private final MoveStrategy moveStrategy;

    InitialPiecePlacement(int row, int column, Camp camp, Type type, MoveStrategy moveStrategy) {
        this.row = row;
        this.column = column;
        this.camp = camp;
        this.type = type;
        this.moveStrategy = moveStrategy;
    }

    public static Map<Position, Piece> init() {
        Map<Position, Piece> board = new HashMap<>();
        for (InitialPiecePlacement piece : values()) {
            board.put(new Position(piece.row, piece.column)
                    , new Piece(piece.type, piece.camp, piece.moveStrategy));
        }
        return board;
    }
}
