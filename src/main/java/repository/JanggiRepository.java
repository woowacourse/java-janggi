package repository;

import domain.JanggiGame;
import java.util.Optional;

public interface JanggiRepository {

    long createGame(JanggiGame game);

    void updateGame(long gameId, JanggiGame game);

    JanggiGame loadGame(long gameId);

    boolean hasUnfinishedGame();

    Optional<Long> findLatestUnfinishedGameId();


    // 게임 저장 로직 완성 후 행마 내용 저장은 고민

//    void saveMove(long gameId, int moveOrder, Position from, Position to, JanggiGame game);

//    int findCurrentMoveOrder(long gameId);

//    Optional<Long> findLatestUnfinishedGameId();
}
