package janggi.repository;

import janggi.domain.game.JanggiGame;
import janggi.domain.game.MoveResult;
import java.util.Optional;

public interface GameRepository {
    long saveNewGame(JanggiGame janggiGame);

    Optional<SavedGame> findPlayingGame();

    void applyMoveResult(long savedGameId, MoveResult moveResult);
}
