package janggi.dao;

import janggi.domain.GameStatus;
import janggi.entity.JanggiEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryJanggiDao implements JanggiDao {

    private long id = 1L;

    private final Map<Long, JanggiEntity> idToJanggi;

    public InMemoryJanggiDao() {
        this.idToJanggi = new HashMap<>();
    }

    @Override
    public JanggiEntity save(final JanggiEntity janggiEntity, final GameStatus gameStatus) {
        Optional<JanggiEntity> existEntity = findByRedAndGreenPlayerNameAndGameStatus(
                janggiEntity.redPlayerName(), janggiEntity.greenPlayerName(), gameStatus.name());
        if (existEntity.isPresent()) {
            idToJanggi.put(existEntity.get().janggiId(), new JanggiEntity(existEntity.get().janggiId(),
                    janggiEntity.redPlayerName(),
                    janggiEntity.greenPlayerName(),
                    janggiEntity.redScore(),
                    janggiEntity.greenScore(),
                    janggiEntity.gameStatus(),
                    janggiEntity.gameTurn()));
            return janggiEntity;
        }
        JanggiEntity saved = new JanggiEntity(id, janggiEntity.redPlayerName(),
                janggiEntity.greenPlayerName(),
                janggiEntity.redScore(),
                janggiEntity.greenScore(),
                janggiEntity.gameStatus(),
                janggiEntity.gameTurn());
        idToJanggi.put(id++, saved);
        return saved;
    }

    @Override
    public Optional<JanggiEntity> findByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                                           final String greenPlayerName,
                                                                           final String gameStatus) {
        return idToJanggi.values().stream()
                .filter(janggiEntity -> janggiEntity.redPlayerName().equals(redPlayerName)
                        && janggiEntity.greenPlayerName().equals(greenPlayerName)
                        && janggiEntity.gameStatus().equals(gameStatus))
                .findAny();
    }

    @Override
    public boolean existsByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                              final String greenPlayerName,
                                                              final String gameStatus) {
        return idToJanggi.values().stream()
                .anyMatch(janggiEntity -> janggiEntity.redPlayerName().equals(redPlayerName)
                        && janggiEntity.greenPlayerName().equals(greenPlayerName)
                        && janggiEntity.gameStatus().equals(gameStatus));
    }

    public Map<Long, JanggiEntity> getIdToJanggi() {
        return idToJanggi;
    }
}
