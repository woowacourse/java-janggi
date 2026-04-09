package janggi.repository;

import janggi.domain.JanggiGame;
import janggi.dto.GameInformationDto;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface JanggiRepository {
    JanggiGame save(Connection conn, JanggiGame game);

    void update(Connection conn, JanggiGame game);

    List<GameInformationDto> findAll(Connection conn);

    Optional<JanggiGame> findById(Connection conn, int gameId);
}
