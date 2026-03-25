package domain;

import java.util.Map;

public interface Piece {
    boolean isHan();

    boolean isCho();

    boolean isMovable(Map<Position, Piece> pieces, Position departure, Position destination);
}
