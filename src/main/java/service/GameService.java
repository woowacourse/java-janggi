package service;

import dto.GameRowDetail;
import repository.GameRepository;

import java.sql.Connection;

public class GameService {

    private static final String NO_ONGOING_GAME_MESSAGE = "진행 중인 게임이 없습니다.";

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setUp(Connection connection) {
        gameRepository.createTable(connection);
    }

    public boolean existsGame(Connection connection) {
        return gameRepository.existsGame(connection);
    }

    public GameRowDetail findOngoingGame(Connection connection) {
        return gameRepository.findOngoingGame(connection)
                .orElseThrow(() -> new IllegalStateException(NO_ONGOING_GAME_MESSAGE));
    }

    public int save(Connection connection, String turn) {
        return gameRepository.save(connection, turn);
    }

    public void updateTurn(Connection connection, int gameId, String turn) {
        gameRepository.updateTurn(connection, gameId, turn);
    }

    public void gameEnd(Connection connection, int gameId) {
        gameRepository.gameEnd(connection, gameId);
    }
}
