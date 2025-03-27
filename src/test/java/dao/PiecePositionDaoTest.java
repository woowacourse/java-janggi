package dao;

import static dao.DatabaseConfig.OPTION;
import static dao.DatabaseConfig.PASSWORD;
import static dao.DatabaseConfig.SERVER;
import static dao.DatabaseConfig.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

import domain.Team;
import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.General;
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
        piecePositionDao.deleteAll(connection);
        connection.close();
    }

    @Nested
    class ValidCases {

        @DisplayName("보드의 모든 기물 위치 정보를 저장한다.")
        @Test
        void addAll() {
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
        void deleteAll() {
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
        void findAll() {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();
            piecePositionDao.addAll(connection, pieces);

            // when & then
            assertThat(piecePositionDao.findAll(connection))
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }

        @DisplayName("데이터베이스에 해당 위치를 삭제한다.")
        @Test
        void deleteByBoardPosition() {
            // given
            Map<BoardPosition, Piece> pieces = Map.of(
                    new BoardPosition(4, 2), new General(Team.RED),
                    new BoardPosition(3, 1), new General(Team.GREEN)
            );
            Board board = new Board(pieces);
            piecePositionDao.addAll(connection, pieces);

            // when
            piecePositionDao.deleteByBoardPosition(connection, new BoardPosition(4, 2));

            // then
            assertThat(piecePositionDao.findAll(connection))
                    .containsEntry(new BoardPosition(3, 1), new General(Team.GREEN));
        }

        @DisplayName("데이터베이스에 해당 위치를 다른 위치로 갱신한다.")
        @Test
        void updateByBoardPosition() {
            // given
            Map<BoardPosition, Piece> pieces = Map.of(
                    new BoardPosition(4, 2), new General(Team.RED),
                    new BoardPosition(3, 1), new General(Team.GREEN)
            );
            Board board = new Board(pieces);
            piecePositionDao.addAll(connection, pieces);

            // when
            piecePositionDao.updateByBoardPosition(
                    connection,
                    new BoardPosition(4, 2),
                    new BoardPosition(6, 7)
            );

            // then
            assertThat(piecePositionDao.findAll(connection))
                    .containsExactlyInAnyOrderEntriesOf(Map.of(
                            new BoardPosition(6, 7), new General(Team.RED),
                            new BoardPosition(3, 1), new General(Team.GREEN)
                    ));
        }
    }
}
