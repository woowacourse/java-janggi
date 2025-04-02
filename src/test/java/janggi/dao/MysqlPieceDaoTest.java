package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.dto.PieceDto;
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

class MysqlPieceDaoTest {

    private final MysqlConnection mysqlConnection = new MysqlConnection("janggi_test");
    private final MysqlPieceDao mysqlPieceDao = new MysqlPieceDao();

    @BeforeEach
    void setUp() {
        cleanUp();
        setupGame();
    }

    @AfterEach
    void tearDown() {
        cleanUp();
    }

    @DisplayName("gameId를 이용해 해당 게임의 모든 기물 정보를 가져올 수 있다.")
    @Test
    void testFindPiecesByGameId() {
        // given
        setupPieces();
        int gameId = 1;
        // when
        List<PieceDto> pieceDtos = executeWithConnectionFunction(
                connection -> mysqlPieceDao.findPiecesByGameId(connection, gameId));
        // then
        assertAll(
                () -> assertThat(pieceDtos).hasSize(2),
                () -> assertThat(pieceDtos.getFirst().pieceType()).isEqualTo("GENERAL"),
                () -> assertThat(pieceDtos.getFirst().team()).isEqualTo("CHO")
        );
    }

    @DisplayName("gameId에 해당하는 기물들 정보를 저장할 수 있다.")
    @Test
    void testAddPieces() {
        // given
        int gameId = 1;
        List<PieceDto> pieceDtos = List.of(
                new PieceDto("GENERAL", "CHO", 3, 0),
                new PieceDto("GENERAL", "HAN", 3, 7)
        );
        // when
        executeWithConnectionConsumer(connection -> mysqlPieceDao.addPieces(connection, gameId, pieceDtos));
        // then
        String selectQuery = "SELECT * FROM piece WHERE game_id = ?";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            assertThat(count).isEqualTo(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("game id를 이용해 연관된 기물들을 삭제할 수 있다.")
    @Test
    void testDeletePiecesByGameId() {
        // given
        setupPieces();
        int gameId = 1;
        // when
        executeWithConnectionConsumer(connection -> mysqlPieceDao.deletePiecesByGameId(connection, gameId));
        // then
        String selectQuery = "SELECT COUNT(*) FROM piece WHERE game_id = ?";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, gameId);
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

    private void cleanUp() {
        try (Connection connection = mysqlConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE piece");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupPieces() {
        String insertQuery1 = "INSERT INTO piece (game_id, pieceType, team, col_num, row_num) VALUES (1, 'GENERAL', 'CHO', 3, 0);";
        String insertQuery2 = "INSERT INTO piece (game_id, pieceType, team, col_num, row_num) VALUES (1, 'GENERAL', 'HAN', 3, 7);";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(insertQuery1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertQuery2)) {
            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
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
}
