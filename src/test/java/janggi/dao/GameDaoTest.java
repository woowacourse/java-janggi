package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.dto.GameSummary;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameDaoTest {

    private final GameDao gameDao = new GameDao("janggi_test");

    @Test
    void connection() {
        try (final var connection = gameDao.getConnection()) {
            System.out.println(connection.getClientInfo());
            assertThat(connection).isNotNull();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    void testGetAllGames() {
        // given
        try (Connection connection = gameDao.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO game (turn) VALUES ('CHO');")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // when
        List<GameSummary> allGames = gameDao.getAllGames();
        // then
        assertAll(
                () -> assertThat(allGames).hasSize(1),
                () -> assertThat(allGames.getFirst().turn()).isEqualTo("CHO"),
                () -> assertThat(allGames.getFirst().id()).isEqualTo(1)
        );
        // clean up
        try (Connection connection = gameDao.getConnection();
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
