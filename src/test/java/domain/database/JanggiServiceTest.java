package domain.database;

import database.context.BoardIdContext;
import database.context.ConnectionContext;
import database.dao.*;
import database.dto.GameResult;
import database.mapper.JanggiBoardMapper;
import database.service.JanggiService;
import database.service.SchemaInitializer;
import domain.board.JanggiBoard;
import database.dto.Moved;
import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import fixture.JanggiBoardFixture;
import fixture.TestTransactionExecutor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static fixture.IntersectionFixture.generate;

class JanggiServiceTest {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    JdbcBoardDao boardDao = new JdbcBoardDao(jdbcTemplate);
    JdbcIntersectionDao intersectionDao = new JdbcIntersectionDao(jdbcTemplate);
    JanggiBoardMapper mapper = new JanggiBoardMapper();
    TestTransactionExecutor transactionExecutor = new TestTransactionExecutor();
    JanggiService janggiService = new JanggiService(boardDao, mapper, transactionExecutor, intersectionDao);

    @BeforeAll
    static void setSchemaSQL() {
        SchemaInitializer schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
    }

    @BeforeEach
    void startTransaction() throws SQLException {
        ConnectionContext.setConnection();
        Connection connection = ConnectionContext.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void rollbackTransaction() throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.close();
        }
        ConnectionContext.clear();
    }

    @AfterEach
    void clearBoardIdContext() {
        BoardIdContext.clear();
    }

    @Test
    void test() throws SQLException{
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                generate(0, 0, Team.CHO, PieceType.CHARIOT),
                generate(0, 8, Team.CHO, PieceType.CHARIOT)
        );

        List<Intersection> expected = janggiBoard.getListIntersection();

        // when
        Long savedId = janggiService.createBoard(janggiBoard);

        // then
        Assertions.assertThat(intersectionDao.readByBoardId(savedId))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("기존의 장기판 조회를 테스트한다.")
    void test1() {
        JanggiBoard expected = JanggiBoardFixture.generate(
                generate(0, 0, Team.CHO, PieceType.CHARIOT),
                generate(0, 8, Team.CHO, PieceType.CHARIOT)
        );

        Long savedId = janggiService.createBoard(expected);

        // when
        JanggiBoard actual = janggiService.getExistBoard(savedId);

        // then
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Turn을 넘겼을 때 DB에 제대로 저장되는 지 확인한다.")
    void test2() {
        Point start = new Point(0, 0);
        Point end = new Point(0, 1);
        JanggiBoard expected = JanggiBoardFixture.generate(
                generate(start, Team.CHO, PieceType.CHARIOT),
                generate(end, Team.HAN, PieceType.CHARIOT)
        );

        Long savedId = janggiService.createBoard(expected);
        BoardIdContext.setBoardId(savedId);
        Moved moved = expected.processTurn(start, end);

        // when
        janggiService.updateTurn(moved, Team.HAN);

        // then
        Assertions.assertThat(janggiService.getExistBoard(savedId))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("장기의 게임 결과가 DB에 제대로 저장되는 지 확인한다.")
    void test3() {
        Point start = new Point(0, 0);
        Point end = new Point(0, 1);
        JanggiBoard expected = JanggiBoardFixture.generate(
                generate(start, Team.CHO, PieceType.CHARIOT),
                generate(end, Team.HAN, PieceType.CHARIOT)
        );

        Long savedId = janggiService.createBoard(expected);
        BoardIdContext.setBoardId(savedId);

        // when
        janggiService.updateBoardResult(GameResult.from(expected));

        // then
        Assertions.assertThat(janggiService.getExistBoard(savedId))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}
