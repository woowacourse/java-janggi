package domain.game;

import java.util.Map;

public final class GameRule {

    private static final Side FIRST_TURN = Side.CHO;
    private static final int MINIMUM_POINT_FOR_CONTINUE = 30;
    private static final Map<Side, Double> BONUS_POINT_BY_SIDE = Map.of(
            Side.CHO, 0.0,
            Side.HAN, 1.5
    );

    public Side firstTurn() {
        return FIRST_TURN;
    }

    public boolean isFinished(boolean generalCaptured, int pointOfCho, int pointOfHan) {
        return generalCaptured || !hasEnoughPointsToContinue(pointOfCho, pointOfHan);
    }

    private boolean hasEnoughPointsToContinue(int pointOfCho, int pointOfHan) {
        return pointOfCho >= MINIMUM_POINT_FOR_CONTINUE
                || pointOfHan >= MINIMUM_POINT_FOR_CONTINUE;
    }

    public double bonusPointOf(Side side) {
        return BONUS_POINT_BY_SIDE.getOrDefault(side, 0.0);
    }
}
