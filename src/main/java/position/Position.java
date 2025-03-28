package position;

import movement.Movement;

public record Position(int x, int y) {

    private static final int SQUARE_NUMBER = 2;

    public Position {
        if (x <= 0 || x > 9 || y <= 0 || y > 10) {
            throw new IllegalArgumentException("존재할 수 없는 위치의 값입니다.");
        }
    }

    public double calculateDistance(final Position destPosition) {
        int dx = this.x - destPosition.x;
        int dy = this.y - destPosition.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // todo : row, column인 Position 이랑 int인 movement랑 자료형 어떻게 연결해줄 것인지 고민해보기
    public Position move(Movement movement) {
        if (movement == Movement.UP) {
            return new Position(x, y + 1);
        } else if (movement == Movement.DOWN) {
            return new Position(x, y - 1);
        } else if (movement == Movement.RIGHT) {
            return new Position(x + 1, y);
        } else if (movement == Movement.LEFT) {
            return new Position(x - 1, y);
        } else if (movement == Movement.LEFT_UP) {
            return new Position(x - 1, y + 1);
        } else if (movement == Movement.LEFT_DOWN) {
            return new Position(x - 1, y - 1);
        } else if (movement == Movement.RIGHT_DOWN) {
            return new Position(x + 1, y - 1);
        } else if (movement == Movement.RIGHT_UP) {
            return new Position(x + 1, y + 1);
        }
        return new Position(x, y);
    }

    public boolean isSameLine(final Position dest) {
        return isVertical(dest) || isHorizontal(dest);
    }

    public boolean isVertical(final Position dest) {
        return x == dest.x && y != dest.y;
    }

    public boolean isHorizontal(final Position dest) {
        return x != dest.x && y == dest.y;
    }

    public boolean isXGreaterThan(final Position dest) {
        return x >= (dest.x);
    }

    public boolean isXLessThan(final Position dest) {
        return x <= (dest.x);
    }
}
