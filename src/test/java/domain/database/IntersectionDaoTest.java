package domain.database;

import database.service.SchemaInitializer;
import database.dao.*;
import database.mapper.JanggiBoardMapper;
import org.junit.jupiter.api.BeforeEach;

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
