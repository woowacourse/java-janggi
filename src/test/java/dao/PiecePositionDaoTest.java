package dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Piece;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PiecePositionDaoTest {

    private final PiecePositionDao piecePositionDao = new PiecePositionDao();

    @Nested
    class ValidCases {

        @DisplayName("장기 데이터베이스 연결 테스트")
        @Test
        public void connection() {
            try (final var connection = piecePositionDao.getConnection()) {
                assertThat(connection).isNotNull();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @DisplayName("보드의 모든 기물 위치 정보를 저장한다.")
        @Test
        public void addAll() {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();

            // when
            piecePositionDao.addAll(pieces);

            // then
            assertThat(piecePositionDao.findAll())
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }
    }
}
