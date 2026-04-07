package janggi.repository;

import janggi.model.Team;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameRepository {
    Long save(Team currentTurn, String name);

    List<String> findAllNames();

    Optional<Long> findIdByName(String name);

    Optional<Team> findCurrentTurn(Long gameId);

    void updateCurrentTurn(Connection connection, Long gameId, Team currentTurn);

    void delete(Connection connection, Long gameId);
}
