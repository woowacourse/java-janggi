package domain.game.progress;

public class PassStreak {
    private int count;

    public PassStreak() {
        this(0);
    }

    public PassStreak(int initialCount) {
        if (initialCount < 0) {
            throw new IllegalArgumentException("연속 패스 수는 0 이상이어야 합니다.");
        }
        this.count = initialCount;
    }

    public void reset() {
        this.count = 0;
    }

    public void increase() {
        this.count++;
    }

    public int value() {
        return count;
    }
}
