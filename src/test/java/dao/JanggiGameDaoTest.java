package dao;

import common.GameStatus;
import db.ConfigLoader;
import db.DbBootstrap;
import db.DbConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

class JanggiGameDaoTest {
    private final ConfigLoader configLoader = new ConfigLoader("application-test.properties");
    private final DbConnectionFactory dbConnectionFactory = new DbConnectionFactory(configLoader);
    private final JanggiGameDao janggiGameDao = new JanggiGameDao(dbConnectionFactory);

    @BeforeEach
    void setUp() {
        DbBootstrap dbBootstrap = new DbBootstrap(dbConnectionFactory);
        dbBootstrap.initialize();
    }

    @Test
    void 게임을_생성하면_DB에_초기_턴과_상태가_저장된다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");

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
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");

            janggiGameDao.updateGameState(connection, gameId, HAN, GameStatus.HAN_WIN);

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
    void 해당_게임의_현재_차례를_반환한다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");

            var currentTurn = janggiGameDao.getCurrentTurn(gameId);

            assertThat(currentTurn.name()).isEqualTo("CHO");
        }
    }

    @Test
    void 업데이트_후_변경된_차례를_반환한다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");
            janggiGameDao.updateGameState(connection, gameId, HAN, GameStatus.PROGRESS);

            var currentTurn = janggiGameDao.getCurrentTurn(gameId);

            assertThat(currentTurn.name()).isEqualTo("HAN");
        }
    }
}
