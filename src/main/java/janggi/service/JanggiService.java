package janggi.service;

import janggi.domain.game.GameManager;
import janggi.dto.GameSessionDTO;
import janggi.persistence.GameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {

    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameSessionDTO> activeGames(Connection connection) throws SQLException {
        return gameRepository.findAllActiveGames(connection);
    }

    public GameManager loadManagerByGameId(Connection connection, long gameId) throws SQLException {
        return gameRepository.findByGameId(connection, gameId);
    }
}
