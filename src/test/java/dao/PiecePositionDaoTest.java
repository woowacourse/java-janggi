package dao;

import static dao.DatabaseConfig.OPTION;
import static dao.DatabaseConfig.PASSWORD;
import static dao.DatabaseConfig.SERVER;
import static dao.DatabaseConfig.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PiecePositionDaoTest {

    private final PiecePositionDao piecePositionDao = new PiecePositionDao();

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + "janggi_test" + OPTION, USERNAME,
                PASSWORD);
        piecePositionDao.deleteAll(connection);
    }

    @AfterEach
    void close() throws SQLException {
        connection.close();
    }

    @Nested
    class ValidCases {

        @DisplayName("보드의 모든 기물 위치 정보를 저장한다.")
        @Test
        public void addAll() {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();

            // when
            piecePositionDao.addAll(connection, pieces);

            // then
            assertThat(piecePositionDao.findAll(connection))
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }

        @DisplayName("데이터베이스에 저장된 모든 기물 위치 정보를 삭제한다.")
        @Test
        public void deleteAll() {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();
            piecePositionDao.addAll(connection, pieces);

            // when
            piecePositionDao.deleteAll(connection);

            // then
            assertThat(piecePositionDao.findAll(connection))
                    .isEmpty();
        }

        @DisplayName("데이터베이스에 저장된 모든 기물 위치 정보를 찾는다.")
        @Test
        public void findAll() {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();
            piecePositionDao.addAll(connection, pieces);

            // when & then
            assertThat(piecePositionDao.findAll(connection))
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }

    }
}
