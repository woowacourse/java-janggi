package domain.database;

import database.dao.*;
import database.mapper.JanggiBoardMapper;
import database.service.JanggiService;
import database.service.SchemaInitializer;
import database.transaction.TransactionExecutor;
import domain.board.JanggiBoard;
import fixture.JanggiBoardFixture;
import fixture.TestTransactionExecutor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    private SchemaInitializer schemaInitializer;
    private JdbcTemplate jdbcTemplate;
    private BoardDao boardDao;
    private IntersectionDao intersectionDao;
    private TransactionExecutor transactionExecutor;
    private JanggiBoardMapper mapper;
    private JanggiService janggiService;

    @BeforeEach
    void setUp() {
        schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
        jdbcTemplate = new JdbcTemplate();
        boardDao = new JdbcBoardDao(jdbcTemplate);
        intersectionDao = new JdbcIntersectionDao(jdbcTemplate);
        transactionExecutor = new TestTransactionExecutor();
        mapper = new JanggiBoardMapper();
        janggiService = new JanggiService(boardDao, mapper, transactionExecutor, intersectionDao);
    }

//    @Test
//    @DisplayName("Board를 저장하면 AutoIncrement에 의한 ID를 반환한다.")
//    void saveBoardReturnAutoIncrementId() {
//        JanggiBoard janggiBoard = JanggiBoardFixture.generate();
//
//        Assertions.assertThat(janggiService.createBoard(janggiBoard))
//                .isNotNull()
//                .isGreaterThan(0L);
//    }
//
//    @Test
//    @DisplayName("Board를 저장할 때마다 ID는 다르다.")
//    void shouldDifferentBoardIdWheneverSaveBoard() {
//        JanggiBoard janggiBoard1 = JanggiBoardFixture.generate();
//        JanggiBoard janggiBoard2 = JanggiBoardFixture.generate();
//
//        Long savedId1 = janggiService.createBoard(janggiBoard1);
//        Long savedId2 = janggiService.createBoard(janggiBoard2);
//
//        Assertions.assertThat(savedId1)
//                .isNotEqualTo(savedId2);
//    }

}
