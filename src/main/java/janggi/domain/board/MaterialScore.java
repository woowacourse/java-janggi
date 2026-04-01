package janggi.domain.board;

import janggi.domain.Side;
import janggi.domain.SideScore;
import java.util.HashMap;
import java.util.Map;

public class MaterialScore {
    private static final String INVALID_NEGATIVE_DECREASE_SCORE = "차감할 점수는 0보다 커야 합니다.";
    private static final String INVALID_SCORE_SIDE = "해당 진영의 점수 정보가 존재하지 않습니다";

    private final Map<Side, Boolean> isGungAlive;
    private final Map<Side, Integer> score;

    public MaterialScore(int hanScore, int choScore) {
        this.isGungAlive = new HashMap<>(Map.of(Side.HAN, true, Side.CHO, true));
        this.score = new HashMap<>(Map.of(Side.HAN, hanScore, Side.CHO, choScore));
    }

    public SideScore getCurrentScore() {
        return new SideScore(score.get(Side.HAN), score.get(Side.CHO));
    }

    public boolean isAnyGungDead() {
        return isGungAlive.values().stream().anyMatch(isCaptured -> !isCaptured);
    }

    public void updateGungDead(Side side) {
        isGungAlive.put(side, false);
    }

    public void decreaseScore(Side side, int pieceScore) {
        if(pieceScore < 0) {
            throw new IllegalArgumentException(INVALID_NEGATIVE_DECREASE_SCORE);
        }

        Integer currentScore = score.get(side);
        if(currentScore == null) {
            throw new IllegalStateException(INVALID_SCORE_SIDE);
        }

        score.put(side, currentScore - pieceScore);
    }
}
