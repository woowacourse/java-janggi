package janggi.dao;

import janggi.entity.JanggiEntity;
import java.util.Optional;

public interface JanggiDao {
    JanggiEntity save(JanggiEntity janggiEntity);

    Optional<JanggiEntity> findByRedAndGreenPlayerNameAndGameStatus(String redPlayerName,
                                                                    String greenPlayerName,
                                                                    String gameStatus);

    boolean existsByRedAndGreenPlayerNameAndGameStatus(String redPlayerName, String greenPlayerName, String gameStatus);

    Optional<Long> findJanggiIdByRedAndGreenPlayerNameAndGameStatus(String redPlayerName,
                                                                    String greenPlayerName,
                                                                    String targetStatus);
}
