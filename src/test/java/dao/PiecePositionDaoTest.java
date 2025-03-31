package dao;

import static dao.DatabaseConfig.getOption;
import static dao.DatabaseConfig.getPassword;
import static dao.DatabaseConfig.getServer;
import static dao.DatabaseConfig.getUsername;
import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.janggi.Team;
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
        connection = DriverManager.getConnection("jdbc:mysql://"
                + getServer()
                + "/" + "janggi_test"
                + getOption(), getUsername(), getPassword());
        piecePositionDao.deleteAll(connection);
    }

    @AfterEach
    void close() throws SQLException {
        piecePositionDao.deleteAll(connection);
        connection.close();
    }

    @Nested
    class ValidCases {

        @DisplayName("보드의 모든 기물 위치 정보를 해당 장기 게임에 저장한다.")
        @Test
        void createAllByJanggiId() throws SQLException {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();

            // when
            piecePositionDao.createAllByJanggiId(connection, 1, pieces);

            // then
            assertThat(piecePositionDao.findAllByJanggiId(connection, 1))
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }

        @DisplayName("해당 장기 게임의 저장된 모든 기물 위치 정보를 찾는다.")
        @Test
        void findAllByJanggiId() throws SQLException {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();
            piecePositionDao.createAllByJanggiId(connection, 1, pieces);

            // when & then
            assertThat(piecePositionDao.findAllByJanggiId(connection, 1))
                    .containsExactlyInAnyOrderEntriesOf(pieces);
        }

        @DisplayName("해당 장기 게임의 특정 위치를 다른 위치로 갱신한다.")
        @Test
        void updateByJanggiIdAndPosition() throws SQLException {
            // given
            Map<BoardPosition, Piece> pieces = Map.of(
                    new BoardPosition(4, 2), new General(Team.RED),
                    new BoardPosition(3, 1), new General(Team.GREEN)
            );
            piecePositionDao.createAllByJanggiId(connection, 1, pieces);

            // when
            piecePositionDao.updateByJanggiIdAndPosition(
                    connection, 1,
                    new BoardPosition(4, 2),
                    new BoardPosition(6, 7)
            );

            // then
            assertThat(piecePositionDao.findAllByJanggiId(connection, 1))
                    .containsExactlyInAnyOrderEntriesOf(Map.of(
                            new BoardPosition(6, 7), new General(Team.RED),
                            new BoardPosition(3, 1), new General(Team.GREEN)
                    ));
        }

        @DisplayName("해당 장기 게임의 특정 위치를 삭제한다.")
        @Test
        void deleteByJanggiIdAndPosition() throws SQLException {
            // given
            Map<BoardPosition, Piece> pieces = Map.of(
                    new BoardPosition(4, 2), new General(Team.RED),
                    new BoardPosition(3, 1), new General(Team.GREEN)
            );
            Board board = new Board(pieces);
            piecePositionDao.createAllByJanggiId(connection, 1, pieces);

            // when
            piecePositionDao.deleteByJanggiIdAndPosition(connection, 1, new BoardPosition(4, 2));

            // then
            assertThat(piecePositionDao.findAllByJanggiId(connection, 1))
                    .containsEntry(new BoardPosition(3, 1), new General(Team.GREEN));
        }


        @DisplayName("모든 게임의 모든 기물 위치 정보를 삭제한다.")
        @Test
        void deleteAll() throws SQLException {
            // given
            Board board = Board.initialize();
            Map<BoardPosition, Piece> pieces = board.getPieces();
            piecePositionDao.createAllByJanggiId(connection, 1, pieces);

            // when
            piecePositionDao.deleteAll(connection);

            // then
            assertThat(piecePositionDao.findAllByJanggiId(connection, 1))
                    .isEmpty();
        }
    }
}
