package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = positionPieceMap;
    }

    public Map<Position, Piece> getPositionPieceMap() {
        return positionPieceMap;
    }

    public boolean isBlank(final Position position) {
        return !positionPieceMap.containsKey(position);
    }

    public Piece findPieceByPosition(final Position position) {
        if (isBlank(position)) {
            throw new IllegalStateException("요청된 위치에는 기물이 존재하지 않습니다.");
        }
        return positionPieceMap.get(position);
    }

    public Map<Position, Piece> offerSelectedPieceMap(final List<Position> positions) {
        final Map<Position, Piece> selected = new LinkedHashMap<>();
        positions.forEach(position -> {
            if (!isBlank(position)) {
                selected.put(position, findPieceByPosition(position));
            }
        });

        return selected;
    }
}
