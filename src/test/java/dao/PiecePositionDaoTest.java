package dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Piece;
import java.sql.Connection;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PiecePositionDaoTest {

    private final PiecePositionDao piecePositionDao = new PiecePositionDao();

    @Nested
    class ValidCases {

        @DisplayName("보드의 모든 기물 위치 정보를 저장한다.")
        @Test
        public void addAll() {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();
            Connection connection = ConnectionProvider.getConnection();

            // when
            piecePositionDao.addAll(connection, pieces);

            // then
            assertThat(piecePositionDao.findAll(connection))
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }

        @DisplayName("데이터베이스에 저장된 모든 기물 위치 정보를 찾는다.")
        @Test
        public void findAll() {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();
            Connection connection = ConnectionProvider.getConnection();

            // when
            piecePositionDao.addAll(connection, pieces);

            // then
            assertThat(piecePositionDao.findAll(connection))
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }
    }
}
