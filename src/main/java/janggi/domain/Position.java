package janggi.domain;

import java.util.List;
import java.util.Objects;

public class Position {

    private static final int POSITION_SIZE = 2;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position makePosition(List<String> parsedPiecePosition) {
        if (parsedPiecePosition.size() != POSITION_SIZE) {
            throw new IllegalArgumentException("기물의 좌표는 두 개로 입력해야 합니다.");
        }
        try {
            int parsedX = Integer.parseInt(parsedPiecePosition.getFirst());
            int parsedY = Integer.parseInt(parsedPiecePosition.getLast());
            return new Position(parsedX, parsedY);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자가 입력되어야 합니다.");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position move(Delta delta) {
        return new Position(x + delta.dx(), y + delta.dy());
    }

    public boolean isSamePosition(Position position) {
        return x == position.getX() && y == position.getY();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Position position)) {
            return false;
        }
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
