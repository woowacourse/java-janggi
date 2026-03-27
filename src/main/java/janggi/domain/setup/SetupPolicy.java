package janggi.domain.setup;

import janggi.domain.Position;
import janggi.domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SetupPolicy {
    static final Map<Position, PieceType> COMMON_BOARD_MAP;

    static {
        COMMON_BOARD_MAP = new LinkedHashMap<>();
        COMMON_BOARD_MAP.put(Position.valueOf(1, 1), PieceType.CHARIOT);
        COMMON_BOARD_MAP.put(Position.valueOf(1, 4), PieceType.GUARD);
        COMMON_BOARD_MAP.put(Position.valueOf(1, 6), PieceType.GUARD);
        COMMON_BOARD_MAP.put(Position.valueOf(1, 9), PieceType.CHARIOT);
        COMMON_BOARD_MAP.put(Position.valueOf(2, 5), PieceType.GENERAL);
        COMMON_BOARD_MAP.put(Position.valueOf(3, 2), PieceType.CANNON);
        COMMON_BOARD_MAP.put(Position.valueOf(3, 8), PieceType.CANNON);
        COMMON_BOARD_MAP.put(Position.valueOf(4, 1), PieceType.SOLDIER);
        COMMON_BOARD_MAP.put(Position.valueOf(4, 3), PieceType.SOLDIER);
        COMMON_BOARD_MAP.put(Position.valueOf(4, 5), PieceType.SOLDIER);
        COMMON_BOARD_MAP.put(Position.valueOf(4, 7), PieceType.SOLDIER);
        COMMON_BOARD_MAP.put(Position.valueOf(4, 9), PieceType.SOLDIER);
    }

    public abstract Map<Position, PieceType> offerBoardMap();
}