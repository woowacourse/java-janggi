package janggi.constant;

public final class GameRule {

    public static final int MIN_POSITION_INDEX = 0;
    public static final int MAX_ROW_INDEX = 9;
    public static final int MAX_COLUMN_INDEX = 8;

    public static final int SINGLE_STEP_DISTANCE = 1;
    public static final int PASS_PIECE_COUNT = 1;

    public static final int HORSE_STRAIGHT_MOVE_DISTANCE = 1;
    public static final int HORSE_DIAGONAL_MOVE_DISTANCE = 1;

    public static final int ELEPHANT_STRAIGHT_MOVE_DISTANCE = 1;
    public static final int ELEPHANT_DIAGONAL_MOVE_DISTANCE = 2;

    private GameRule() {
    }
}
