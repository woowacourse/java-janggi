package domain.board;

public class Palace {
    private static final int PALACE_MIN_X = 4;
    private static final int PALACE_MAX_X = 6;
    private static final int HAN_PALACE_MIN_Y = 1;
    private static final int HAN_PALACE_MAX_Y = 3;
    private static final int CHO_PALACE_MIN_Y = 8;
    private static final int CHO_PALACE_MAX_Y = 10;
    private static final int PALACE_CENTER_X = 5;
    private static final int HAN_PALACE_CENTER_Y = 2;
    private static final int CHO_PALACE_CENTER_Y = 9;
    private static final Position HAN_GENERAL = new Position(5, 2);
    private static final Position CHO_GENERAL = new Position(5, 9);

    public Palace() {
    }

    public boolean isInPalace(Position position) {
        return isInChoPalace(position) || isInHanPalace(position);
    }

    public boolean isOnPalaceCenter(Position position) {
        return isOnHanPalaceCenter(position) || isOnChoPalaceCenter(position);
    }

    public Position findPalaceCenter(Position from) {
        if (from.y() <= HAN_PALACE_MAX_Y) {
            return HAN_GENERAL;
        }
        return CHO_GENERAL;
    }

    public boolean isInDifferencePalace(Position from, Position to) {
        boolean isBothHanPalace = isInHanPalace(from) && isInHanPalace(to);
        boolean isBothChoPalace = isInChoPalace(from) && isInChoPalace(to);

        return !(isBothHanPalace || isBothChoPalace);
    }

    private boolean isInChoPalace(Position position) {
        return xIsInPalace(position) && yIsInChoPalace(position);
    }

    private boolean isInHanPalace(Position position) {
        return xIsInPalace(position) && yIsInHanPalace(position);
    }

    private boolean xIsInPalace(Position position) {
        return position.x() >= PALACE_MIN_X && position.x() <= PALACE_MAX_X;
    }

    private boolean yIsInHanPalace(Position position) {
        return position.y() >= HAN_PALACE_MIN_Y && position.y() <= HAN_PALACE_MAX_Y;
    }

    private boolean yIsInChoPalace(Position position) {
        return position.y() >= CHO_PALACE_MIN_Y && position.y() <= CHO_PALACE_MAX_Y;
    }

    private boolean isOnHanPalaceCenter(Position position) {
        return position.x() == PALACE_CENTER_X && position.y() == HAN_PALACE_CENTER_Y;
    }

    private boolean isOnChoPalaceCenter(Position position) {
        return position.x() == PALACE_CENTER_X && position.y() == CHO_PALACE_CENTER_Y;
    }
}
