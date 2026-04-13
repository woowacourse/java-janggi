package dao;

import static org.assertj.core.api.Assertions.assertThat;

import db.ConnectionManager;
import db.DatabaseInitializer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardPieceDaoTest {

    private Connection connection;
    private BoardPieceDao boardPieceDao;
    private long gameRoomId;

    @BeforeEach
    void setUp() throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        new DatabaseInitializer(connectionManager).initialize();
        truncate(connectionManager);
        connection = connectionManager.getConnection();
        boardPieceDao = new BoardPieceDao();
        gameRoomId = new GameRoomDao().save(connection, new GameRoomRawData(0L, "방", "CHO", "RUNNING", 0));
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("게임방에 저장된 기물이 없을 때 조회하면 빈 리스트를 반환한다")
    void findByGameRoomId_empty() {
        List<BoardPieceRawData> found = boardPieceDao.findByGameRoomId(connection, gameRoomId);

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("기물들을 저장하면 게임방 id로 전부 조회된다")
    void saveAllAndFind() {
        List<BoardPieceRawData> pieces = List.of(
                new BoardPieceRawData(0, 0, "CHARIOT", "CHO"),
                new BoardPieceRawData(9, 4, "GENERAL", "HAN")
        );

        boardPieceDao.saveAll(connection, gameRoomId, pieces);

        List<BoardPieceRawData> found = boardPieceDao.findByGameRoomId(connection, gameRoomId);
        assertThat(found).containsExactlyInAnyOrderElementsOf(pieces);
    }

    @Test
    @DisplayName("게임방 id로 기물을 모두 삭제한다")
    void deleteByGameRoomId() {
        boardPieceDao.saveAll(connection, gameRoomId, List.of(
                new BoardPieceRawData(0, 0, "CHARIOT", "CHO")
        ));

        boardPieceDao.deleteByGameRoomId(connection, gameRoomId);

        assertThat(boardPieceDao.findByGameRoomId(connection, gameRoomId)).isEmpty();
    }

    @Test
    @DisplayName("특정 위치의 기물을 삭제한다")
    void deletePieceAt() {
        boardPieceDao.saveAll(connection, gameRoomId, List.of(
                new BoardPieceRawData(0, 0, "CHARIOT", "CHO"),
                new BoardPieceRawData(9, 4, "GENERAL", "HAN")
        ));

        boardPieceDao.deletePieceAt(connection, gameRoomId, new BoardPieceRawData(0, 0, "", ""));

        List<BoardPieceRawData> remaining = boardPieceDao.findByGameRoomId(connection, gameRoomId);
        assertThat(remaining).extracting(BoardPieceRawData::pieceType).containsExactly("GENERAL");
    }

    @Test
    @DisplayName("단건 기물을 삽입한다")
    void insertPiece() {
        boardPieceDao.insertPiece(connection, gameRoomId, new BoardPieceRawData(3, 5, "SOLDIER", "CHO"));

        assertThat(boardPieceDao.findByGameRoomId(connection, gameRoomId))
                .containsExactly(new BoardPieceRawData(3, 5, "SOLDIER", "CHO"));
    }

    private void truncate(ConnectionManager connectionManager) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM board_piece");
            statement.execute("DELETE FROM game_room");
        }
    }
}
