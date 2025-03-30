package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private final MysqlConnection mysqlConnection = new MysqlConnection("janggi_test");
    private final PieceDao pieceDao = new PieceDao(mysqlConnection);

    @BeforeEach
    void setUp() {
        cleanUp();
        String gameInsertQuery = "INSERT INTO game (turn) VALUES ('CHO');";
        String insertQuery1 = "INSERT INTO piece (game_id, pieceType, team, col_num, row_num) VALUES (1, 'GENERAL', 'CHO', 3, 0);";
        String insertQuery2 = "INSERT INTO piece (game_id, pieceType, team, col_num, row_num) VALUES (1, 'GENERAL', 'HAN', 3, 7);";
        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(gameInsertQuery);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertQuery1);
             PreparedStatement preparedStatement3 = connection.prepareStatement(insertQuery2)) {
            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        cleanUp();
    }

    @DisplayName("gameId를 이용해 해당 게임의 모든 기물 정보를 가져올 수 있다.")
    @Test
    void testGetPiecesByGameId() {
        // given
        int gameId = 1;
        // when
        List<PieceDto> pieceDtos = pieceDao.getPiecesByGameId(gameId);
        // then
        assertAll(
                () -> assertThat(pieceDtos).hasSize(2),
                () -> assertThat(pieceDtos.getFirst().pieceType()).isEqualTo("GENERAL"),
                () -> assertThat(pieceDtos.getFirst().team()).isEqualTo("CHO")
        );
    }

    private void cleanUp() {
        try (Connection connection = mysqlConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE piece");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
