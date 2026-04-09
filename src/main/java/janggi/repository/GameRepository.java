package janggi.repository;

import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import java.util.Optional;

public interface GameRepository {
    long saveNewGame(JanggiGame janggiGame);

    Optional<SavedGame> findPlayingGame();

    void updateAfterMove(long savedGameId, JanggiGame janggiGame, Position startPiecePosition,
                         Position endPiecePosition);
}
