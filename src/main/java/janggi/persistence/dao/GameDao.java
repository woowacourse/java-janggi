package janggi.persistence.dao;

import janggi.domain.Camp;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameDao {
    void create(Connection conn, GameEntity gameEntity);

    List<String> findAllNames();

    Optional<String> findByName(String name);

    void deleteById(Connection conn, String id);

    Optional<GameEntity> findById(String id);

    void updateStatus(Connection conn, String gameId, Camp camp, Status status);
}
