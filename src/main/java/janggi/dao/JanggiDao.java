package janggi.dao;

import janggi.dto.MoveDto;
import java.util.List;

public interface JanggiDao {

    void saveInitialGame(int setupOption);

    boolean existNotFinishedGame();

    int findNotFinishedGameId();

    int findGameSetup(int gameId);

    List<MoveDto> selectAllHistory(int gameId);

    void saveHistory(MoveDto moveDto, int gameId);

    void setGameFinished(int gameId);
}
