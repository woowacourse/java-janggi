package domain.game;

public class GameRecord {
    private int consecutivePassCount;

    public GameRecord() {
        this.consecutivePassCount = 0;
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
