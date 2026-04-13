package data;

import domain.piece.Camp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BoardDaoTest extends DatabaseTestHelper {
    private DataSource dataSource;
    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        dataSource = createDataSource();
        boardDao = new BoardDao();
    }

    @Test
    void 장기판을_저장하고_조회한다() throws Exception {
        try (Connection connection = getConnection(dataSource)) {
            Long boardId = boardDao.insertBoard(connection, true, Camp.CHO.name());

            Optional<BoardDto> result = boardDao.getBoard(connection, boardId);

            assertThat(result).isPresent();
            assertThat(result.get()).isEqualTo(new BoardDto(boardId, true, Camp.CHO));
        }
    }

    @Test
    void 턴과_게임_진행_상태를_변경한다() throws Exception {
        try (Connection connection = getConnection(dataSource)) {
            Long boardId = boardDao.insertBoard(connection, true, Camp.CHO.name());

            boardDao.updateBoard(connection, boardId, false, Camp.HAN.name());

            Optional<BoardDto> result = boardDao.getBoard(connection, boardId);

            assertThat(result).contains(new BoardDto(boardId, false, Camp.HAN));
        }
    }

    @Test
    void 장기판을_삭제한다() throws Exception {
        try (Connection connection = getConnection(dataSource)) {
            Long boardId = boardDao.insertBoard(connection, true, Camp.CHO.name());

            boardDao.deleteBoard(connection, boardId);

            assertThat(boardDao.getBoard(connection, boardId)).isEmpty();
        }
    }

    @Test
    void 저장된_장기판_목록을_조회한다() throws Exception {
        try (Connection connection = getConnection(dataSource)) {
            Long firstBoardId = boardDao.insertBoard(connection, true, Camp.CHO.name());
            boardDao.insertBoard(connection, false, Camp.HAN.name());

            List<BoardDto> result = boardDao.getAllBoards(connection);

            assertThat(result).containsExactly(
                    new BoardDto(firstBoardId, true, Camp.CHO)
            );
        }
    }
}
