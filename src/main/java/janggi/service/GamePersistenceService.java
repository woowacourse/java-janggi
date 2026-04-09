package janggi.service;

import janggi.config.ConnectionManager;
import janggi.config.SchemaInitializer;
import janggi.domain.Game;
import janggi.dto.TurnDto;
import janggi.repository.GameRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class GamePersistenceService {
    private static final String CREATE_GAME_FAIL_MESSAGE = "게임 생성 저장 중 오류가 발생했습니다.";
    private static final String ENTER_GAME_FAIL_MESSAGE = "게임 복원 중 오류가 발생했습니다.";
    private static final String MOVE_SAVE_FAIL_MESSAGE = "게임 이동 저장 중 오류가 발생했습니다.";

    private final ConnectionManager connectionManager;
    private final SchemaInitializer schemaInitializer;
    private final GameRepository gameRepository;

    public GamePersistenceService(
            ConnectionManager connectionManager,
            SchemaInitializer schemaInitializer,
            GameRepository gameRepository
    ) {
        this.connectionManager = connectionManager;
        this.schemaInitializer = schemaInitializer;
        this.gameRepository = gameRepository;
    }

    public void initialize() {
        schemaInitializer.init();
    }

    public long saveNewGame(Game game) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                long roomId = gameRepository.saveNewGame(game, connection);
                connection.commit();
                return roomId;
            } catch (RuntimeException e) {
                rollback(connection);
                throw new IllegalStateException(CREATE_GAME_FAIL_MESSAGE, e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(CREATE_GAME_FAIL_MESSAGE, e);
        }
    }

    public Game enterGame(long roomId) {
        try (Connection connection = connectionManager.getConnection()) {
            return gameRepository.enterGame(roomId, connection);
        } catch (SQLException e) {
            throw new IllegalStateException(ENTER_GAME_FAIL_MESSAGE, e);
        }
    }

    public void saveMove(long roomId, Game game, TurnDto turnDto) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                gameRepository.saveMove(roomId, game, turnDto, connection);
                connection.commit();
            } catch (RuntimeException e) {
                rollback(connection);
                throw new IllegalStateException(MOVE_SAVE_FAIL_MESSAGE, e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(MOVE_SAVE_FAIL_MESSAGE, e);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }
}
