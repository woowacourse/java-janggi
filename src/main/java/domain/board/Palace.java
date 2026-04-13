package domain.board;

import domain.path.Direction;

public class Palace {
    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;
    private static final int CHO_PALACE_MIN_ROW = 0;
    private static final int CHO_PALACE_MAX_ROW = 2;
    private static final int HAN_PALACE_MIN_ROW = 7;
    private static final int HAN_PALACE_MAX_ROW = 9;

    private static final Position HAN_PALACE_CENTER_POSITION = new Position(4, 8);
    private static final Position CHO_PALACE_CENTER_POSITION = new Position(4, 1);

    public static boolean isPalacePath(Position departure, Position destination) {
        if (!isInPalace(departure) || !isInPalace(destination)) {
            return false;
        }

        int deltaX = departure.calculateDeltaX(destination);
        int deltaY = departure.calculateDeltaY(destination);
        Direction direction = Direction.decideDirection(deltaX, deltaY);

        if (Direction.isLinear(direction)) {
            return true;
        }

        Position nextPosition = departure.move(direction.getDeltaX(), direction.getDeltaY());
        return isPalaceCenter(departure) || isPalaceCenter(nextPosition);
    }

    public static boolean isPalaceCenter(Position position) {
        return isChoPalaceCenter(position) || isHanPalaceCenter(position);
    }

    public static boolean isChoPalaceCenter(Position position) {
        return position.equals(CHO_PALACE_CENTER_POSITION);
    }

    public static boolean isHanPalaceCenter(Position position) {
        return position.equals(HAN_PALACE_CENTER_POSITION);
    }

    public static boolean isInPalace(Position position) {
        return isInChoPalace(position) || isInHanPalace(position);
    }

    public static boolean isInChoPalace(Position position) {
        return isPalaceColumn(position.column()) && isChoPalaceRow(position.row());
    }

    public static boolean isInHanPalace(Position position) {
        return isPalaceColumn(position.column()) && isHanPalaceRow(position.row());
    }

    private static boolean isPalaceColumn(int column) {
        return column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN;
    }

    private static boolean isChoPalaceRow(int row) {
        return row >= CHO_PALACE_MIN_ROW && row <= CHO_PALACE_MAX_ROW;
    }

    private static boolean isHanPalaceRow(int row) {
        return row >= HAN_PALACE_MIN_ROW && row <= HAN_PALACE_MAX_ROW;
    }
}
