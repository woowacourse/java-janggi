package janggi.dao;

import static janggi.domain.TestFixture.RED_HORSE;
import static janggi.domain.TestFixture.RED_SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.BoardSetup;
import janggi.domain.board.InitialBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.util.ConnectionUtil;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {
    private final BoardDao boardDao = new BoardDao();

    @Test
    public void connection() {
        try (final var connection = ConnectionUtil.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("기물 정보와 기물 위치 정보를 저장한다.")
    @Test
    void savePiece() {
        // given
        Position position = Position.of(1, 1);
        Piece piece = RED_HORSE;

        // when
        boardDao.savePiece(position, piece);

        // then
    }

    @DisplayName("초기화된 보드를 저장할 수 있다.")
    @Test
    void test_save_initialBoard() {
        // given
        InitialBoard board = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        Map<Position, Piece> initialBoard = board.getInitialBoard();

        // when
        boardDao.saveBoard(initialBoard);

        // then
    }

    @DisplayName("기물이 이동했을 떄 이동 정보를 업데이트한다.")
    @Test
    void update_Board() {
        // given
        Position source = Position.of(1, 1);
        Position destination = Position.of(3, 1);

        boardDao.savePiece(destination, RED_SOLDIER);

        // when
        boardDao.updatePiecePosition(source, destination, PieceType.CANNON, TeamColor.BLUE);

        // then
    }
}
