package domain.game;

public class GameRecord {
    private int consecutivePassCount;

    public GameRecord() {
        this(0);
    }

    public GameRecord(int consecutivePassCount) {
        this.consecutivePassCount = consecutivePassCount;
    }

    public void recordMove() {
        consecutivePassCount = 0;
    }

    public void recordPass() {
        consecutivePassCount++;
    }

    public int consecutivePassCount() {
        return consecutivePassCount;
    }
}
