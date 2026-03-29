package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class Board implements BoardMediator {

    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = positionPieceMap;
    }

    @Override
    public boolean hasPieceAt(final Position position) {
        return isNotBlank(position);
    }

    @Override
    public Piece getPieceInPosition(final Position position) {
        return findPieceByPosition(position);
    }

    public Map<Position, Piece> getPositionPieceMapForDTO() {
        return Map.copyOf(positionPieceMap);
    }

    private boolean isNotBlank(final Position position) {
        return positionPieceMap.containsKey(position);
    }

    private Piece findPieceByPosition(final Position position) {
        if (!isNotBlank(position)) {
            throw new IllegalStateException("요청된 위치에는 기물이 존재하지 않습니다.");
        }
        return positionPieceMap.get(position);
    }
}
