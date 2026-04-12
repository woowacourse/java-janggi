package service;

import domain.board.context.BoardIdContext;
import database.dao.*;
import database.dto.GameResult;
import database.mapper.JanggiBoardMapper;
import domain.board.exception.BoardException;
import domain.board.JanggiBoard;
import service.dto.Moved;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import fixture.DatabaseTestSupport;
import fixture.JanggiBoardFixture;
import fixture.TestTransactionExecutor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static fixture.IntersectionFixture.generate;

class JanggiServiceTest extends DatabaseTestSupport {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    JdbcBoardDao boardDao = new JdbcBoardDao(jdbcTemplate);
    JdbcIntersectionDao intersectionDao = new JdbcIntersectionDao(jdbcTemplate);
    JanggiBoardMapper mapper = new JanggiBoardMapper();
    TestTransactionExecutor transactionExecutor = new TestTransactionExecutor();
    JanggiService janggiService = new JanggiService(boardDao, mapper, transactionExecutor, intersectionDao);

    @AfterEach
    void clearBoardIdContext() {
        BoardIdContext.clear();
    }

    @Test
    @DisplayName("기존의 장기게임을 불러올 때, DB로부터 저장된 기물 배치정보와 턴 정보가 일치하는 JanggiBoard를 가져온다.")
    void shouldReturnJanggiBoardWhenGetExistBoardWithValidId() {
        // given
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
    @DisplayName("기물 이동 후, Turn을 넘겼을 때 DB에 기물 위치 변경 다음 턴의 정보가 DB에 반영된다.")
    void shouldUpdatePiecePointAndTurnWhenUpdateTurn() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(0, 1);
        JanggiBoard expected = JanggiBoardFixture.generate(
                generate(start, Team.CHO, PieceType.CHARIOT),
                generate(end, Team.HAN, PieceType.CHARIOT)
        );

        Long savedId = janggiService.createBoard(expected);
        BoardIdContext.setBoardId(savedId);
        Moved moved = Moved.of(expected, start, end);

        // when
        janggiService.updateTurn(moved);

        // then
        Assertions.assertThat(janggiService.getExistBoard(savedId))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("장기가 종료되어, 결과를 저장할때, DB에 종료 및 승자가 반영된다.")
    void shouldSaveGameResultWhenUpdateBoardResult() {
        // given
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

    @Test
    @DisplayName("존재하지 않는 보드 ID로 조회 시 BoardNotFoundException이 발생한다.")
    void shouldThrowExceptionWhenGetBoardWithInvalidId() {
        // given
        Long invalidId = -1L;

        // when & then
        Assertions.assertThatThrownBy(() -> janggiService.getExistBoard(invalidId))
                .isInstanceOf(BoardException.class);
    }

}
