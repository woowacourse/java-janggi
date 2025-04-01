package janggi.dao;

import java.util.List;

public interface GameDao {

    void saveInitialGame(int setupOption);

    boolean existNotFinishedGame();

    int findRecentNotFinishedGameId();

    List<Integer> findNotFinishedGameIds();

    int findGameSetup(int gameId);

//    List<MoveDto> selectAllHistory(int gameId);
//
//    void saveHistory(MoveDto moveDto, int gameId);

    void setGameFinished(int gameId);
}
