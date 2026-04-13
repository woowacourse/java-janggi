package domain.game;

import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidGameResultException;
import domain.pieces.Side;

public record GameScore(Score choScore, Score hanScore) {

    public Side winner() {
        if (choScore.value() > hanScore.value()) {
            return Side.CHO;
        }
        if (hanScore.value() > choScore.value()) {
            return Side.HAN;
        }
        throw new InvalidGameResultException(GameErrorMessage.GAME_SCORE_CANNOT_BE_TIED);
    }
}
