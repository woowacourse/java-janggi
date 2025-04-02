package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.game.Team;
import janggi.dto.GameDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MysqlGameDaoTest {

    private final MysqlConnection mysqlConnection = new MysqlConnection("janggi_test");
    private final MysqlGameDao mysqlGameDao = new MysqlGameDao();

    @BeforeEach
    void setUp() {
        cleanUp();
    }

    @AfterEach
    void tearDown() {
        cleanUp();
    }

    @DisplayName("저장된 게임 목록을 가져올 수 있다.")
    @Test
    void testFindAllGames() {
        // given
        setupGame();
        // when
        List<GameDto> allGames = executeWithConnectionFunction(mysqlGameDao::findAllGames);
        // then
        assertAll(
                () -> assertThat(allGames).hasSize(1),
                () -> assertThat(allGames.getFirst().turn()).isEqualTo("CHO"),
                () -> assertThat(allGames.getFirst().id()).isEqualTo(1)
        );
    }

    @DisplayName("id로 게임을 가져올 수 있다.")
    @Test
    void testFindGameById() {
        // given
        setupGame();
        int gameId = 1;
        // when
        GameDto gameDto = executeWithConnectionFunction(connection -> mysqlGameDao.findGameById(connection, gameId));
        // then
        assertAll(
                () -> assertThat(gameDto).isNotNull(),
                () -> assertThat(gameDto.id()).isEqualTo(1),
                () -> assertThat(gameDto.turn()).isEqualTo("CHO")
        );
    }

    @DisplayName("새로운 게임을 추가할 수 있다.")
    @Test
    void testAddGame() {
        // given
        Team turn = Team.CHO;
        // when
        int savedGameId = executeWithConnectionFunction(connection -> mysqlGameDao.addGame(connection, turn));
        // then
        String selectQuery = "SELECT * FROM game WHERE id = ?";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, savedGameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertAll(
                    () -> assertThat(resultSet.next()).isTrue(),
                    () -> assertThat(resultSet.getString("turn")).isEqualTo("CHO")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("id를 이용해 저장된 게임의 턴을 바꿀 수 있다.")
    @Test
    void testUpdateGameById() {
        // given
        setupGame();
        // when
        int gameId = 1;
        executeWithConnectionConsumer(connection -> {
            mysqlGameDao.updateGameById(connection, gameId, Team.HAN);
            mysqlGameDao.updateGameById(connection, gameId, Team.HAN);
        });
        // then
        String selectQuery = "SELECT * FROM game WHERE id = ?";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertAll(
                    () -> assertThat(resultSet.next()).isTrue(),
                    () -> assertThat(resultSet.getString("turn")).isEqualTo("HAN")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("id를 이용해 저장된 게임을 삭제할 수 있다.")
    @Test
    void testDeleteGame() {
        // given
        setupGame();
        // when
        executeWithConnectionConsumer(connection -> mysqlGameDao.deleteGameById(connection, 1));
        // then
        String selectQuery = "SELECT COUNT(*) FROM game";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            assertAll(
                    () -> assertThat(resultSet.next()).isTrue(),
                    () -> assertThat(resultSet.getInt(1)).isEqualTo(0)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeWithConnectionConsumer(final Consumer<Connection> block) {
        try (Connection connection = mysqlConnection.getConnection()) {
            block.accept(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T executeWithConnectionFunction(final Function<Connection, T> block) {
        try (Connection connection = mysqlConnection.getConnection()) {
            return block.apply(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupGame() {
        String insertQuery = "INSERT INTO game (turn) VALUES ('CHO');";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanUp() {
        try (Connection connection = mysqlConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("TRUNCATE TABLE piece");
            statement.execute("TRUNCATE TABLE game");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
