package janggi.dao;

import janggi.domain.GameStatus;
import janggi.entity.JanggiEntity;
import java.util.Optional;

public interface JanggiDao {
    JanggiEntity save(JanggiEntity janggiEntity, GameStatus gameStatus);

    Optional<JanggiEntity> findByRedAndGreenPlayerNameAndGameStatus(String redPlayerName,
                                                                    String greenPlayerName,
                                                                    String gameStatus);

    boolean existsByRedAndGreenPlayerNameAndGameStatus(String redPlayerName, String greenPlayerName, String gameStatus);
}
