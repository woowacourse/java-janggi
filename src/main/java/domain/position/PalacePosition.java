package domain.position;

public enum PalacePosition {
    CHO_TOP_LEFT(new Position(1, 4), true),
    CHO_TOP_CENTER(new Position(1, 5), false),
    CHO_TOP_RIGHT(new Position(1, 6), true),
    CHO_MID_LEFT(new Position(2, 4), false),
    CHO_CENTER(new Position(2, 5), true),
    CHO_MID_RIGHT(new Position(2, 6), false),
    CHO_BOT_LEFT(new Position(3, 4), true),
    CHO_BOT_CENTER(new Position(3, 5), false),
    CHO_BOT_RIGHT(new Position(3, 6), true),

    HAN_TOP_LEFT(new Position(8, 4), true),
    HAN_TOP_CENTER(new Position(8, 5), false),
    HAN_TOP_RIGHT(new Position(8, 6), true),
    HAN_MID_LEFT(new Position(9, 4), false),
    HAN_CENTER(new Position(9, 5), true),
    HAN_MID_RIGHT(new Position(9, 6), false),
    HAN_BOT_LEFT(new Position(10, 4), true),
    HAN_BOT_CENTER(new Position(10, 5), false),
    HAN_BOT_RIGHT(new Position(10, 6), true);

    private final Position position;
    private final boolean canMoveDiagonal;

    PalacePosition(Position position, boolean canMoveDiagonal) {
        this.position = position;
        this.canMoveDiagonal = canMoveDiagonal;
    }

    public static boolean isCanMoveDiagonal(Position position) {
        for (PalacePosition palacePosition : PalacePosition.values()) {
            Position other = palacePosition.position;
            if (other.equals(position)) {
                return palacePosition.canMoveDiagonal;
            }
        }
        return false;
    }

    public static boolean isPalacePosition(Position position) {
        for (PalacePosition palacePosition : PalacePosition.values()) {
            Position other = palacePosition.position;
            if (other.equals(position)) {
                return true;
            }
        }
        return false;
    }
}
