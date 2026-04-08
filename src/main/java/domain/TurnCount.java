package domain;

public class TurnCount {

    private final int turnCount;

    private TurnCount(final int turnCount) {
        this.turnCount = turnCount;
    }

    public static TurnCount of(final int turnCount) {
        validate(turnCount);
        return new TurnCount(turnCount);
    }

    public int getTurnCount() {
        return turnCount;
    }

    private static void validate(final int turnCount) {
        if (turnCount < 0) {
            throw new IllegalArgumentException("[ERROR] 턴은 0 이하가 될 수 없습니다.");
        }
    }
}
