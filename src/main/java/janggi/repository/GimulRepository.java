package janggi.repository;

import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import java.util.Map;

public interface GimulRepository {
    void saveAll(Long gameId, Map<Position, AbstractGimul> board);

    void deleteAll(Long gameId);

    Map<Position, AbstractGimul> findAll(Long gameId);
}
