package domain.board;

public class Palace {
    private static final int PALACE_MIN_X = 3;
    private static final int PALACE_MAX_X = 7;
    private static final int HAN_PALACE_MIN_Y = 0;
    private static final int HAN_PALACE_MAX_Y = 4;
    private static final int CHO_PALACE_MIN_Y = 7;
    private static final int CHO_PALACE_MAX_Y = 11;

    public Palace() {
    }

    public boolean isInPalace(Position position) {
        return isInChoPalace(position) || isInHanPalace(position);
    }

    private boolean isInChoPalace(Position position) {
        return xIsInPalace(position) && yIsInChoPalace(position);
    }

    private boolean isInHanPalace(Position position) {
        return xIsInPalace(position) && yIsInHanPalace(position);
    }

    private boolean xIsInPalace(Position position) {
        return position.x() > PALACE_MIN_X && position.x() < PALACE_MAX_X;
    }

    private boolean yIsInHanPalace(Position position) {
        return position.y() > HAN_PALACE_MIN_Y && position.y() < HAN_PALACE_MAX_Y;
    }

    private boolean yIsInChoPalace(Position position) {
        return position.y() > CHO_PALACE_MIN_Y && position.y() < CHO_PALACE_MAX_Y;
    }
}
