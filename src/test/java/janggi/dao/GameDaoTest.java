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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {

    private final MysqlConnection mysqlConnection = new MysqlConnection("janggi_test");
    private final GameDao gameDao = new GameDao(mysqlConnection);

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
        List<GameDto> allGames = gameDao.findAllGames();
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
        GameDto gameDto = gameDao.findGameById(gameId);
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
        int savedGameId = gameDao.addGame(turn);
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
