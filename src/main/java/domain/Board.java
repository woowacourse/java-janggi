package domain;

import java.util.Map;

public class Board {
    Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece pieceAt(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }

        return pieces.get(position);
    }
}
