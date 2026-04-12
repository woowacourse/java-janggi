package janggi.service;

import janggi.domain.Janggi;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameRepository {
    Optional<Janggi> findById(String id);

    String save(Connection conn, String name, Janggi janggi);

    List<String> findAllNames();

    Optional<String> findByName(String gameName);

    void updateGameResult(Connection conn, String gameId, Janggi janggi);

    void update(Connection conn, String id, Janggi janggi);

    void deleteById(Connection conn, String gameId);
}
