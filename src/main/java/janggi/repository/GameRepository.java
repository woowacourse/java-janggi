package janggi.repository;

import janggi.GameStatus;
import janggi.player.Score;
import janggi.player.Turn;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Long save(Turn turn, Score choScore, Score hanScore);

    Long save(long gameId, Turn turn, Score choScore, Score hanScore);

    List<Integer> findIdsByStatus(GameStatus status);

    Optional<Turn> findTurnByGameId(Long select);

    Optional<Score> findChoScoreByGameId(long gameId);

    Optional<Score> findHanScoreByGameId(long gameId);
}


