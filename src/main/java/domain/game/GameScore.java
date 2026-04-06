package domain.game;

import domain.pieces.Side;

public record GameScore(Score choScore, Score hanScore) {

    public Side winner() {
        if (choScore.value() > hanScore.value()) {
            return Side.CHO;
        }
        return Side.HAN;
    }
}
