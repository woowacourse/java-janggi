package janggi.domain;

public record Position(int x, int y) {
    public static final int BOARD_START_ROWS = 1;
    public static final int BOARD_START_COLS = 1;
    public static final int BOARD_END_ROWS = 10;
    public static final int BOARD_END_COLS = 9;

    public Position(int x, int y) {
        validate();
        this.x = x;
        this.y = y;
    }

    private void validate() {
        validateRow();
        validateCol();
    }

    private void validateRow() {
        if(x < BOARD_START_ROWS || x > BOARD_END_ROWS){
            throw new IllegalArgumentException("유효하지 않은 위치입니다. 행은 1부터 10까지 가능합니다.");
        }
    }

    private void validateCol() {
        if(y < BOARD_START_COLS || y > BOARD_END_COLS) {
            throw new IllegalArgumentException("유효하지 않은 위치입니다. 열은 1부터 9까지 가능합니다.");
        }
    }

    public Position move(Movement movement) {
        return new Position(x + movement.getDx(), y + movement.getDy());
    }

    public int calculateDistance(Position position, boolean isVertical) {
        if(isVertical){
            return position.x - this.x;
        }
        return position.y - this.y;
    }

    public boolean isHorizon(Position position) {
        return position.x == this.x;
    }

    public boolean isVertical(Position position) {
        return position.y == this.y;
    }
}
