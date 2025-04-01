package janggi.repository;

import janggi.dto.MoveDto;
import java.util.List;

public interface Repository {

    void setGameFinished(final int id);

    void saveInitialGame(final int setupOption);

    int findRecentOngoingGameId();

    boolean isExistOngoingGame();

    List<Integer> findOngoingGameIds();

    int findGameSetup(final int id);

    List<MoveDto> selectAllHistory(final int id);

    void saveHistory(final MoveDto moveDto, final int id);
}
