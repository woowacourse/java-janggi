package janggi.domain.setup;

import janggi.domain.Position;
import janggi.domain.piece.PieceType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ElephantFormation {
    static final Map<Position, PieceType> COMMON_BOARD_MAP;

    static {
        Map<Position, PieceType> boardMap = new LinkedHashMap<>();
        boardMap.put(Position.valueOf(1, 1), PieceType.CHARIOT);
        boardMap.put(Position.valueOf(1, 4), PieceType.GUARD);
        boardMap.put(Position.valueOf(1, 6), PieceType.GUARD);
        boardMap.put(Position.valueOf(1, 9), PieceType.CHARIOT);
        boardMap.put(Position.valueOf(2, 5), PieceType.GENERAL);
        boardMap.put(Position.valueOf(3, 2), PieceType.CANNON);
        boardMap.put(Position.valueOf(3, 8), PieceType.CANNON);
        boardMap.put(Position.valueOf(4, 1), PieceType.SOLDIER);
        boardMap.put(Position.valueOf(4, 3), PieceType.SOLDIER);
        boardMap.put(Position.valueOf(4, 5), PieceType.SOLDIER);
        boardMap.put(Position.valueOf(4, 7), PieceType.SOLDIER);
        boardMap.put(Position.valueOf(4, 9), PieceType.SOLDIER);
        COMMON_BOARD_MAP = Collections.unmodifiableMap(boardMap);
    }

    public abstract Map<Position, PieceType> offerBoardMap();
}