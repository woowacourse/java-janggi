package janggi;

public class Turn {

    private int accumulatedCount;

    public Turn(final int accumulatedCount) {
        this.accumulatedCount = accumulatedCount;
    }

    public static Turn start() {
        return new Turn(1);
    }

    public Team getCurrentTeam() {
        boolean isChoTurn = accumulatedCount % 2 == 0;

        if (isChoTurn) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    public void next() {
        accumulatedCount++;
    }
}
