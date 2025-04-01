package janggi.dao;

import java.util.List;

public interface GameDao {

    void saveInitialGame(int setupOption);

    boolean existNotFinishedGame();

    int findRecentNotFinishedGameId();

    List<Integer> findNotFinishedGameIds();

    int findGameSetup(int gameId);

    void setGameFinished(int gameId);
}
