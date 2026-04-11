package janggi.service;

import janggi.domain.Janggi;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    Optional<Janggi> findById(String id);

    String save(String name, Janggi janggi);

    List<String> findAllNames();

    Optional<String> findByName(String gameName);

    void updateGameResult(String gameId, Janggi janggi);

    void update(String id, Janggi janggi);

    void deleteById(String gameId);
}
