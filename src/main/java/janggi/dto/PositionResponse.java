package janggi.dto;

import janggi.domain.common.Position;
import java.util.Objects;

public class PositionResponse {

    private final int x;
    private final int y;

    private PositionResponse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static PositionResponse from(Position position) {
        return new PositionResponse(position.getX(), position.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PositionResponse that = (PositionResponse) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
