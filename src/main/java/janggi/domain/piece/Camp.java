package janggi.domain.piece;

public enum Camp {

    HAN(-1, 9),
    CHO(1, 0),
    ;

    private final int forwardDirection;
    private final int startRowPosition;

    Camp(int forwardDirection, int startRowPosition) {
        this.forwardDirection = forwardDirection;
        this.startRowPosition = startRowPosition;
    }

    public void validateForwardDirection(int rowDiff) {
        if (forwardDirection != rowDiff && rowDiff != 0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }

    public int getStartRowPosition() {
        return startRowPosition;
    }
}
