package domain.constant;

import domain.Position;

public enum Palace {
    CHO(1, 3, 4, 6, Position.create(2, 5)),
    HAN(8, 10, 4, 6, Position.create(9, 5));

    private final int startX;
    private final int endX;
    private final int startY;
    private final int endY;
    private final Position center;

    Palace(int startX, int endX, int startY, int endY, Position position) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.center = position;
    }

    public static Palace from(Country country) {
        if (country.equals(Country.CHO)) {
            return CHO;
        }
        return HAN;
    }

    public boolean isPalace(Position position) {
        return position.getX() >= startX && position.getX() <= endX && position.getY() >= startY && position.getY() <= endY;
    }

    public boolean isDiagonalPath(Position start, Position end) {
        if (!(isPalace(start) && isPalace(end))) {
            return false;
        }

        int diffX = Math.abs(end.getX() - start.getX());
        int diffY = Math.abs(end.getY() - start.getY());
        if (diffX != diffY) {
            return false;
        }

        if (!(isCorner(start) && isCorner(end))) {
            return false;
        }

        return diffX == 1 || diffX == 2;
    }

    private boolean isCorner(Position position) {
        return (position.getX() == startX || position.getX() == endX)
                && (position.getY() == startY || position.getY() == endY) || position.equals(center);
    }

    public Position getCenter() {
        return center;
    }
}
