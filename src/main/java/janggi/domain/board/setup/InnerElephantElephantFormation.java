package janggi.domain.board.setup;

import janggi.domain.Position;
import janggi.domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;

public class InnerElephantElephantFormation extends ElephantFormation {

    @Override
    public Map<Position, PieceType> offerBoardMap() {
        final Map<Position, PieceType> boardMap = new LinkedHashMap<>(COMMON_BOARD_MAP);
        boardMap.put(Position.valueOf(1, 2), PieceType.HORSE);
        boardMap.put(Position.valueOf(1, 3), PieceType.ELEPHANT);
        boardMap.put(Position.valueOf(1, 7), PieceType.ELEPHANT);
        boardMap.put(Position.valueOf(1, 8), PieceType.HORSE);
        return Map.copyOf(boardMap);
    }
}
