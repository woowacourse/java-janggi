package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.dao.impl.BoardDAOImpl;
import janggi.dao.impl.GameRoomDAOImpl;
import janggi.domain.Board;
import janggi.domain.move.Position;
import janggi.manager.DatabaseManager;
import janggi.manager.DatabaseTestManager;
import janggi.util.BoardFixture;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
class BoardDAOTest {

    private static final String GAME_ROOM_NAME = "room1";
    private final DatabaseManager databaseManager = DatabaseTestManager.create();
    private final GameRoomDAO gameRoomDAOImpl = new GameRoomDAOImpl(databaseManager);
    private final BoardDAO boardDAOImpl = new BoardDAOImpl(databaseManager);

    @BeforeEach
    void setup() throws SQLException {
        DatabaseTestManager.resetDatabase();
        databaseManager.createTableIfNotExist();
        gameRoomDAOImpl.create(GAME_ROOM_NAME);
    }

    @DisplayName("보드를 게임에 저장한다.")
    @Test
    void test1() {
        Board board = BoardFixture.sangMaSangMa();

        assertThatCode(() -> boardDAOImpl.save(GAME_ROOM_NAME, board))
                .doesNotThrowAnyException();
    }

    @DisplayName("보드를 게임에 저장하고 반환한다.")
    @Test
    void test2() {
        // given
        Board board = BoardFixture.sangMaSangMa();
        boardDAOImpl.save(GAME_ROOM_NAME, board);

        // when
        Board result = boardDAOImpl.toDomain(GAME_ROOM_NAME);

        // then
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(board);
    }

    @DisplayName("기물을 움직일 수 있다.")
    @Test
    void test3() {
        // given
        Board board = BoardFixture.sangMaSangMa();
        boardDAOImpl.save(GAME_ROOM_NAME, board);
        Position currentPosition = Position.of(7, 1);
        Position targetPosition = Position.of(6, 1);

        // when
        boardDAOImpl.movePiece(GAME_ROOM_NAME, currentPosition, targetPosition);

        // then
        Board domain = boardDAOImpl.toDomain(GAME_ROOM_NAME);
        assertThatCode(() -> domain.getPiece(targetPosition))
                .doesNotThrowAnyException();
    }
}
