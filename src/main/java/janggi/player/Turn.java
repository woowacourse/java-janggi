package janggi.player;

public class Turn {

    private int accumulatedCount;

    private Turn(final int accumulatedCount) {
        this.accumulatedCount = accumulatedCount;
    }

    public static Turn start() {
        return new Turn(1);
    }

    public static Turn from(final int value) {
        return new Turn(value);
    }

    public Team getCurrentTeam() {
        final boolean isChoTurn = accumulatedCount % 2 == 0;

        if (isChoTurn) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    public void next() {
        accumulatedCount++;
    }

    public int getAccumulatedCount() {
        return accumulatedCount;
    }
}
