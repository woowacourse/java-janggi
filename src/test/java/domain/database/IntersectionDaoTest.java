package domain.database;

import database.SchemaInitializer;
import database.connection.DBConnector;
import database.dao.BoardDao;
import database.dao.IntersectionDao;
import database.dao.JdbcBoardDao;
import database.dao.JdbcIntersectionDao;
import domain.board.JanggiBoard;
import domain.board.JanggiIntersectionGenerator;
import domain.intersection.Intersection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static domain.board.Formation.ELEPHANT_HORSE_ELEPHANT_HORSE;

class IntersectionDaoTest {

    private SchemaInitializer schemaInitializer;
    private IntersectionDao intersectionDao;
    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
        intersectionDao = new JdbcIntersectionDao();
        boardDao = new JdbcBoardDao();
    }

    @Test
    @DisplayName("장기판 초기화 시, 90개의 교차점 데이터를 저장하고 이를 읽을 수 있다.")
    void readIntersectionTest() throws SQLException {
        try (Connection connection = DBConnector.getConnection()) {
            // given
            int defaultIntersectionCount = 90;

            Long savedId = boardDao.save(connection);
            JanggiBoard janggiBoard = new JanggiBoard(new JanggiIntersectionGenerator(
                    ELEPHANT_HORSE_ELEPHANT_HORSE,
                    ELEPHANT_HORSE_ELEPHANT_HORSE)
            );

            List<Intersection> actual = janggiBoard.getListIntersection();
            intersectionDao.saveAllIntersection(connection, savedId, actual);

            // when
            List<Intersection> expected = intersectionDao.readIntersectionByBoardId(connection, savedId);

            // then
            Assertions.assertThat(actual.size())
                    .isEqualTo(expected.size())
                    .isEqualTo(defaultIntersectionCount);
        }
    }

}
