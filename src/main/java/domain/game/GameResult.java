package domain.game;

import java.util.Map;

public record GameResult(Side winner, boolean generalCaptured, Map<Side, Double> totalPointBySide) {

    public String winnerName() {
        return winner.name();
    }
}
