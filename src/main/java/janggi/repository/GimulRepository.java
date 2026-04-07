package janggi.repository;

import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import java.sql.Connection;
import java.util.Map;

public interface GimulRepository {
    void saveAll(Connection connection, Long gameId, Map<Position, AbstractGimul> board);

    void deleteAll(Connection connection, Long gameId);
    
    Map<Position, AbstractGimul> findAll(Long gameId);
}
