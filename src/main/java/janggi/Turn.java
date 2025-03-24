package janggi;

public class Turn {

    private int accumulatedCount;

    public Turn(final int accumulatedCount) {
        this.accumulatedCount = accumulatedCount;
    }

    public static Turn create() {
        return new Turn(0);
    }

    public Team next() {
        accumulatedCount++;
        boolean isChoTurn = accumulatedCount % 2 == 0;

        if (isChoTurn) {
            return Team.CHO;
        }
        return Team.HAN;
    }
}
