package janggi.repository;

import janggi.domain.GameStatus;
import janggi.domain.JanggiGame;
import java.util.Optional;

public interface JanggiRepository {
    void save(JanggiGame janggiGame);

    boolean existsByRedAndGreenPlayerNameAndGameStatus(String redPlayerName,
                                                       String greenPlayerName,
                                                       GameStatus gameStatus);

    Optional<JanggiGame> findByRedAndGreenPlayerNameAndGameStatus(String redPlayerName,
                                                                  String greenPlayerName,
                                                                  GameStatus gameStatus);

    Optional<Long> findJanggiIdByRedAndGreenPlayerNameAndGameStatus(String redPlayerName,
                                                                    String greenPlayerName,
                                                                    GameStatus gameStatus);
}
