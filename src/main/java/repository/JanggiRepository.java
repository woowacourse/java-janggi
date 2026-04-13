package repository;

import domain.JanggiGame;
import dto.UnfinishedGameInfo;
import java.util.List;
import java.util.Optional;

public interface JanggiRepository {

    long createGame(JanggiGame game);

    void updateGame(long gameId, JanggiGame game);

    Optional<JanggiGame> loadGame(long gameId);

    boolean hasUnfinishedGame();

    Optional<Long> findLatestUnfinishedGameId();

    List<UnfinishedGameInfo> findUnfinishedGameInfos();
}
