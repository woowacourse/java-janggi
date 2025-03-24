package janggi;

public class Turn {

    private int accumulatedCount;

    public Turn(final int accumulatedCount) {
        this.accumulatedCount = accumulatedCount;
    }

    public static Turn create(int initialValue) {
        return new Turn(initialValue);
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
