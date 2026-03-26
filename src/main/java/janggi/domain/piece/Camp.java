package janggi.domain.piece;

public enum Camp {

    HAN(-1),
    CHO(1);

    private final int forwardDirection;

    Camp(int forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    public void validateForwardDirection(int rowDiff) {
        if (forwardDirection != rowDiff && rowDiff != 0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}
