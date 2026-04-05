package janggi.domain.board;

import janggi.domain.Position;

public record PalaceRange(int startX, int endX, int startY, int endY) {
    public boolean isContain(Position position) {
        return position.isRange(startX, endX, startY, endY);
    }
}
