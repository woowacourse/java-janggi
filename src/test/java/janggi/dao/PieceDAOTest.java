package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.Board;
import janggi.domain.move.Position;
import janggi.manager.DatabaseManager;
import janggi.manager.DatabaseTestManager;
import janggi.util.BoardFixture;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDAOTest {

    static final String GAME_ROOM_NAME = "room1";
    static DatabaseManager databaseManager = DatabaseTestManager.create();
    static GameRoomDAO gameRoomDAO = new GameRoomDAO(databaseManager);
    PieceDAO pieceDAO = new PieceDAO(databaseManager);

    @BeforeAll
    static void setupDatabase() throws SQLException {

        databaseManager.createTableIfNotExist();
        Connection connection = databaseManager.getConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM game_room");
        }

        gameRoomDAO.create(GAME_ROOM_NAME);
    }

    @DisplayName("보드를 게임에 저장한다.")
    @Test
    void test1() {
        Board board = BoardFixture.sangMaSangMa();

        assertThatCode(() -> pieceDAO.saveAll(GAME_ROOM_NAME, board))
                .doesNotThrowAnyException();
    }

    @DisplayName("보드를 게임에 저장하고 반환한다.")
    @Test
    void test2() {
        // given
        Board board = BoardFixture.sangMaSangMa();
        pieceDAO.saveAll(GAME_ROOM_NAME, board);

        // when
        Board result = pieceDAO.toDomain(GAME_ROOM_NAME);

        // then
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(board);
    }

    @DisplayName("기물을 움직일 수 있다.")
    @Test
    void test3() {
        // given
        Board board = BoardFixture.sangMaSangMa();
        pieceDAO.saveAll(GAME_ROOM_NAME, board);
        Position currentPosition = Position.of(7, 1);
        Position targetPosition = Position.of(6, 1);

        // when
        pieceDAO.movePiece(GAME_ROOM_NAME, currentPosition, targetPosition);

        // then
        Board domain = pieceDAO.toDomain(GAME_ROOM_NAME);
        assertThatCode(() -> domain.getPiece(targetPosition))
                .doesNotThrowAnyException();
    }

    @AfterEach
    void clearDatabase() throws SQLException {
        Connection connection = databaseManager.getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM piece");
        }
    }

}
