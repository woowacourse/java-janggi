package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {
    private static BoardDao boardDao;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        try (var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Board ("
                    + "row_value INT NOT NULL, "
                    + "column_value INT NOT NULL, "
                    + "type VARCHAR(64) NOT NULL, "
                    + "dynasty VARCHAR(64) NOT NULL, "
                    + "PRIMARY KEY (row_value, column_value))");
        }

        connection.close();

        boardDao = new BoardDao();
    }

    @AfterEach
    void clearDatabase() {
        boardDao.deleteBoardDB();
    }

    @Test
    @DisplayName("보드 데이터를 삭제한다.")
    void deleteDBTest() {
        List<PieceInfo> pieces = List.of(
                new PieceInfo(1, 1, "KING", "HAN"),
                new PieceInfo(2, 2, "CANNON", "CHO")
        );

        boardDao.createBoardDB(pieces);

        boardDao.deleteBoardDB();
        List<PieceInfo> result = boardDao.readBoardDB();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("보드 데이터를 추가하고 읽어온다.")
    void createAndReadDBTest() {
        List<PieceInfo> pieces = List.of(
                new PieceInfo(1, 2, "KING", "HAN"),
                new PieceInfo(3, 3, "CANNON", "CHO")
        );

        boardDao.createBoardDB(pieces);
        List<PieceInfo> result = boardDao.readBoardDB();

        assertThat(result).hasSize(2)
                .extracting(PieceInfo::row, PieceInfo::column, PieceInfo::type, PieceInfo::dynasty)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(1, 2, "KING", "HAN"),
                        Tuple.tuple(3, 3, "CANNON", "CHO")
                );
    }
}