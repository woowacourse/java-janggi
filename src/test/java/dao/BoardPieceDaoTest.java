package dao;

import static org.assertj.core.api.Assertions.assertThat;

import db.ConnectionManager;
import db.DatabaseInitializer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardPieceDaoTest {

    private BoardPieceDao boardPieceDao;
    private long gameRoomId;

    @BeforeEach
    void setUp() throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        new DatabaseInitializer(connectionManager).initialize();
        truncate(connectionManager);
        boardPieceDao = new BoardPieceDao(connectionManager);
        gameRoomId = new GameRoomDao(connectionManager).save("방", "CHO", "RUNNING", 0);
    }

    @Test
    @DisplayName("기물들을 저장하면 게임방 id로 전부 조회된다")
    void saveAllAndFind() {
        List<BoardPieceRawData> pieces = List.of(
                new BoardPieceRawData(0, 0, "CHARIOT", "CHO"),
                new BoardPieceRawData(9, 4, "GENERAL", "HAN")
        );

        boardPieceDao.saveAll(gameRoomId, pieces);

        List<BoardPieceRawData> found = boardPieceDao.findByGameRoomId(gameRoomId);
        assertThat(found).containsExactlyInAnyOrderElementsOf(pieces);
    }

    @Test
    @DisplayName("게임방 id로 기물을 모두 삭제한다")
    void deleteByGameRoomId() {
        boardPieceDao.saveAll(gameRoomId, List.of(
                new BoardPieceRawData(0, 0, "CHARIOT", "CHO")
        ));

        boardPieceDao.deleteByGameRoomId(gameRoomId);

        assertThat(boardPieceDao.findByGameRoomId(gameRoomId)).isEmpty();
    }

    private void truncate(ConnectionManager connectionManager) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM board_piece");
            statement.execute("DELETE FROM game_room");
        }
    }
}