package service;

import dto.GameDto;
import repository.GameRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class GameService {

    private static final String NO_ONGOING_GAME_MESSAGE = "진행 중인 게임이 없습니다.";

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setUp(Connection connection) throws SQLException {
        gameRepository.createTable(connection);
    }

    public boolean existsGame(Connection connection) throws SQLException {
        return gameRepository.existsGame(connection);
    }

    public GameDto findOngoingGame(Connection connection) throws SQLException {
        return gameRepository.findOngoingGame(connection)
                .orElseThrow(() -> new IllegalStateException(NO_ONGOING_GAME_MESSAGE));
    }

    public int save(Connection connection, String turn) throws SQLException {
        return gameRepository.save(connection, turn);
    }

    public void updateTurn(Connection connection, int gameId, String turn) throws SQLException {
        gameRepository.updateTurn(connection, gameId, turn);
    }
}
