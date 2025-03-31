package janggi.dao;

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
    public JanggiEntity save(final JanggiEntity janggiEntity) {
        Optional<Long> janggiIdOptional = findJanggiIdByRedAndGreenPlayerNameAndGameStatus(
                janggiEntity.redPlayerName(),
                janggiEntity.greenPlayerName(),
                janggiEntity.gameStatus());
        if (janggiIdOptional.isPresent()) {
            idToJanggi.put(janggiEntity.janggiId(), janggiEntity);
            return janggiEntity;
        }
        long targetId = id++;
        JanggiEntity saved = janggiEntity.addJanggiId(targetId);
        idToJanggi.put(targetId, saved);
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

    @Override
    public Optional<Long> findJanggiIdByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                                           final String greenPlayerName,
                                                                           final String targetStatus) {
        return idToJanggi.values().stream()
                .filter(janggiEntity -> janggiEntity.redPlayerName().equals(redPlayerName)
                        && janggiEntity.greenPlayerName().equals(greenPlayerName)
                        && janggiEntity.gameStatus().equals(targetStatus))
                .map(JanggiEntity::janggiId)
                .findAny();
    }

    public Map<Long, JanggiEntity> getIdToJanggi() {
        return idToJanggi;
    }
}
