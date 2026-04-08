package janggi.persistence.repository;

import janggi.domain.Position;
import janggi.persistence.GameStatus;
import janggi.persistence.model.JanggiGameHistory;

public interface JanggiGameRepository {

    long createNewGame();

    void saveMove(long gameId, int turnNumber, Position startPosition, Position endPosition);

    void update(GameStatus gameStatus, long gameId);

    JanggiGameHistory findRecentGame();
}
