package janggi.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class OuterElephantSetupPolicy extends SetupPolicy {
    @Override
    public Map<Position, PieceType> offerBoardMap() {
        final Map<Position, PieceType> boardMap = new LinkedHashMap<>(COMMON_BOARD_MAP);
        boardMap.put(Position.valueOf(1, 2), PieceType.ELEPHANT);
        boardMap.put(Position.valueOf(1, 3), PieceType.HORSE);
        boardMap.put(Position.valueOf(1, 7), PieceType.HORSE);
        boardMap.put(Position.valueOf(1, 8), PieceType.ELEPHANT);
        return boardMap;
    }
}
