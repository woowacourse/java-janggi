package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.game.Team;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
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
        String insertQuery = "INSERT INTO game (turn) VALUES ('CHO');";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        cleanUp();
    }

    @DisplayName("저장된 게임 목록을 가져올 수 있다.")
    @Test
    void testGetAllGames() {
        // given
        // when
        List<GameDto> allGames = gameDao.getAllGames();
        // then
        assertAll(
                () -> assertThat(allGames).hasSize(1),
                () -> assertThat(allGames.getFirst().turn()).isEqualTo("CHO"),
                () -> assertThat(allGames.getFirst().id()).isEqualTo(1)
        );
    }

    @DisplayName("id로 게임을 가져올 수 있다.")
    @Test
    void testGetGameById() {
        // given
        int gameId = 1;
        // when
        GameDto gameDto = gameDao.getGameById(gameId);
        // then
        assertAll(
                () -> assertThat(gameDto).isNotNull(),
                () -> assertThat(gameDto.id()).isEqualTo(1),
                () -> assertThat(gameDto.turn()).isEqualTo("CHO")
        );
    }

    @DisplayName("새로운 게임을 저장할 수 있다.")
    @Test
    void testSaveGame() {
        // given
        cleanUp();
        Team turn = Team.CHO;
        List<PieceDto> pieceDtos = List.of(
                new PieceDto("GENERAL", "CHO", 3, 0),
                new PieceDto("GENERAL", "HAN", 3, 7)
        );
        // when
        gameDao.saveGame(turn, pieceDtos);
        // then
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement selectGameQuery = connection.prepareStatement("SELECT * FROM game");
             PreparedStatement selectPieceQuery = connection.prepareStatement("SELECT * FROM piece")) {

            ResultSet gameResultSet = selectGameQuery.executeQuery();
            ResultSet pieceResultSet = selectPieceQuery.executeQuery();

            int gameCount = 0;
            while (gameResultSet.next()) {
                gameCount++;
            }
            int pieceCount = 0;
            while (pieceResultSet.next()) {
                pieceCount++;
            }
            final int finalGameCount = gameCount;
            final int finalPieceCount = pieceCount;
            assertAll(
                    () -> assertThat(finalGameCount).isEqualTo(1),
                    () -> assertThat(finalPieceCount).isEqualTo(2)
            );
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
