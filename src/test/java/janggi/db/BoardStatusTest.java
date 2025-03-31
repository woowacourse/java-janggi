package janggi.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Position;
import janggi.piece.Guard;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.team.TeamName;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardStatusTest {

    private final BoardStatus boardStatus = new BoardStatus();

    @BeforeEach
    public void setUp() {
        boardStatus.clearBoardStatus();
    }

    @DisplayName("정상: DB 연결 확인")
    @Test
    public void connection() throws SQLException {
        try (final var connection = boardStatus.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @DisplayName("정상: 보드 상태 비어있음 확인")
    @Test
    public void isBoardStatusEmpty() {
        assertThat(boardStatus.isBoardStatusEmpty()).isTrue();
    }

    @DisplayName("정상: 보드 상태 로드 확인")
    @Test
    public void loadBoardStatus() {
        assertThatCode(boardStatus::loadBoardStatus).doesNotThrowAnyException();
    }

    @DisplayName("정상: 보드 상태 저장 확인")
    @Test
    public void saveBoardStatus() {
        List<Piece> pieces = List.of(
                new King(TeamName.CHO, new Position(4, 1)),
                new Guard(TeamName.CHO, new Position(3, 0))
        );

        assertThatCode(() -> boardStatus.saveBoardStatus(pieces)).doesNotThrowAnyException();
    }
}
