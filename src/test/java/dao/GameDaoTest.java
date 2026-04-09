package dao;

import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import common.GameStatus;
import db.DbBootstrap;
import db.DbConnectionFactory;
import domain.player.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDaoTest {
    private final GameDao gameDao = new GameDao();

    @BeforeEach
    void setUp() {
        DbBootstrap.initializeForTest();
    }

    @Test
    void 게임을_생성하면_DB에_초기_턴과_상태가_저장된다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            long gameId = gameDao.createGame(connection, "CHO Player", "HAN Player");

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT current_turn, status FROM game WHERE game_id = ?")) {
                statement.setLong(1, gameId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    assertThat(resultSet.next()).isTrue();
                    assertThat(resultSet.getString("current_turn")).isEqualTo("CHO");
                    assertThat(resultSet.getString("status")).isEqualTo("PROGRESS");
                }
            }
        }
    }

    @Test
    void 게임_상태를_업데이트하면_턴과_상태가_변경된다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            long gameId = gameDao.createGame(connection, "CHO Player", "HAN Player");

            gameDao.updateGameState(connection, gameId, HAN, GameStatus.HAN_WIN);

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT current_turn, status FROM game WHERE game_id = ?")) {
                statement.setLong(1, gameId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    assertThat(resultSet.next()).isTrue();
                    assertThat(resultSet.getString("current_turn")).isEqualTo("HAN");
                    assertThat(resultSet.getString("status")).isEqualTo("HAN_WIN");
                }
            }
        }
    }

    @Test
    void findProgressGame_진행중인_게임이_있으면_ID를_반환한다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            // 기존 진행 중인 게임 종료
            Optional<Long> existing = gameDao.findProgressGame();
            while (existing.isPresent()) {
                gameDao.updateGameState(connection, existing.get(), Team.CHO, GameStatus.CHO_WIN);
                existing = gameDao.findProgressGame();
            }

            long gameId1 = gameDao.createGame(connection, "CHO Player", "HAN Player");
            long gameId2 = gameDao.createGame(connection, "CHO Player2", "HAN Player2");

            Optional<Long> foundId = gameDao.findProgressGame();

            assertThat(foundId).isPresent();
            assertThat(foundId.get()).isEqualTo(gameId2);
        }
    }

    @Test
    void findProgressGame_진행중인_게임이_없으면_비어있음을_반환한다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            Optional<Long> existingGame = gameDao.findProgressGame();
            while (existingGame.isPresent()) {
                gameDao.updateGameState(connection, existingGame.get(), HAN, GameStatus.CHO_WIN);
                existingGame = gameDao.findProgressGame();
            }

            long gameId = gameDao.createGame(connection, "CHO Player", "HAN Player");
            gameDao.updateGameState(connection, gameId, HAN, GameStatus.HAN_WIN);

            Optional<Long> foundId = gameDao.findProgressGame();

            assertThat(foundId).isEmpty();
        }
    }

    @Test
    void getCurrentTurn_해당_게임의_현재_차례를_반환한다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            long gameId = gameDao.createGame(connection, "CHO Player", "HAN Player");

            var currentTurn = gameDao.getCurrentTurn(gameId);

            assertThat(currentTurn.name()).isEqualTo("CHO");
        }
    }

    @Test
    void getCurrentTurn_업데이트_후_변경된_차례를_반환한다() throws SQLException {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            long gameId = gameDao.createGame(connection, "CHO Player", "HAN Player");
            gameDao.updateGameState(connection, gameId, HAN, GameStatus.PROGRESS);

            var currentTurn = gameDao.getCurrentTurn(gameId);

            assertThat(currentTurn.name()).isEqualTo("HAN");
        }
    }
}
