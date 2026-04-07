package domain.database;

import database.SchemaInitializer;
import database.connection.DBConnector;
import database.dao.*;
import database.dto.IntersectionDto;
import database.mapper.JanggiBoardMapper;
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

    private JdbcTemplate jdbcTemplate;
    private SchemaInitializer schemaInitializer;
    private IntersectionDao intersectionDao;
    private JanggiBoardMapper mapper;
    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate();
        schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
        intersectionDao = new JdbcIntersectionDao(jdbcTemplate);
        boardDao = new JdbcBoardDao(jdbcTemplate);
        mapper = new JanggiBoardMapper();
    }

//    @Test
//    @DisplayName("장기판 초기화 시, 90개의 교차점 데이터를 저장하고 이를 읽을 수 있다.")
//    void readIntersectionTest() throws SQLException {
//        try (Connection connection = DBConnector.getConnection()) {
//            // given
//            int defaultIntersectionCount = 90;
//
//            Long savedId = boardDao.save(connection);
//            JanggiBoard janggiBoard = new JanggiBoard(new JanggiIntersectionGenerator(
//                    ELEPHANT_HORSE_ELEPHANT_HORSE,
//                    ELEPHANT_HORSE_ELEPHANT_HORSE)
//            );
//
//            List<IntersectionDto> actual = mapper.toIntersectionDtoList(janggiBoard.getListIntersection());
//            intersectionDao.saveAll(connection, savedId, actual);
//
//            // when
//            List<Intersection> expected = intersectionDao.readByBoardId(savedId);
//
//            // then
//            Assertions.assertThat(actual.size())
//                    .isEqualTo(expected.size())
//                    .isEqualTo(defaultIntersectionCount);
//        }
//    }

}
