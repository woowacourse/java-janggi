package domain.game;

import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidGameResultException;
import domain.pieces.Side;

public record GameResult(GameStatus status, Side winner) {
    public GameResult {
        if (status == GameStatus.RUNNING && winner != null) {
            throw new InvalidGameResultException(GameErrorMessage.RUNNING_GAME_CANNOT_HAVE_WINNER);
        }
        if (status == GameStatus.ENDED && winner == null) {
            throw new InvalidGameResultException(GameErrorMessage.ENDED_GAME_MUST_HAVE_WINNER);
        }
    }

    public static GameResult running() {
        return new GameResult(GameStatus.RUNNING, null);
    }

    public static GameResult ended(Side winner) {
        return new GameResult(GameStatus.ENDED, winner);
    }

    public boolean isEnded() {
        return status.equals(GameStatus.ENDED);
    }
}
