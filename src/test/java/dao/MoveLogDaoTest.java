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

class MoveLogDaoTest {

    private Connection connection;
    private MoveLogDao moveLogDao;
    private long gameRoomId;

    @BeforeEach
    void setUp() throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        new DatabaseInitializer(connectionManager).initialize();
        truncate(connectionManager);
        connection = connectionManager.getConnection();
        moveLogDao = new MoveLogDao();
        gameRoomId = new GameRoomDao().save(connection, new GameRoomRawData(0L, "방", "CHO", "RUNNING", 0));
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("저장된 이동 로그가 없으면 빈 리스트를 반환한다")
    void findByGameRoomId_empty() {
        assertThat(moveLogDao.findByGameRoomId(connection, gameRoomId)).isEmpty();
    }

    @Test
    @DisplayName("MOVE 로그를 저장하고 조회한다")
    void insertAndFindMove() {
        MoveLogRawData raw = new MoveLogRawData(0, "MOVE", "CHO", 1, 1, 2, 2, "CHARIOT");

        moveLogDao.insert(connection, gameRoomId, raw);

        assertThat(moveLogDao.findByGameRoomId(connection, gameRoomId))
                .containsExactly(raw);
    }

    @Test
    @DisplayName("PASS 로그는 좌표와 기물 타입이 null로 저장된다")
    void insertAndFindPass() {
        MoveLogRawData raw = new MoveLogRawData(0, "PASS", "HAN", null, null, null, null, null);

        moveLogDao.insert(connection, gameRoomId, raw);

        assertThat(moveLogDao.findByGameRoomId(connection, gameRoomId))
                .containsExactly(raw);
    }

    @Test
    @DisplayName("조회 결과는 seq 오름차순으로 정렬된다")
    void findOrderedBySeq() {
        moveLogDao.insert(connection, gameRoomId,
                new MoveLogRawData(1, "PASS", "HAN", null, null, null, null, null));
        moveLogDao.insert(connection, gameRoomId,
                new MoveLogRawData(0, "MOVE", "CHO", 1, 1, 2, 2, "CHARIOT"));
        moveLogDao.insert(connection, gameRoomId,
                new MoveLogRawData(2, "MOVE", "CHO", 2, 2, 3, 3, "CHARIOT"));

        List<MoveLogRawData> found = moveLogDao.findByGameRoomId(connection, gameRoomId);

        assertThat(found).extracting(MoveLogRawData::seq).containsExactly(0, 1, 2);
    }

    @Test
    @DisplayName("게임방 id로 이동 로그를 모두 삭제한다")
    void deleteByGameRoomId() {
        moveLogDao.insert(connection, gameRoomId,
                new MoveLogRawData(0, "MOVE", "CHO", 1, 1, 2, 2, "CHARIOT"));

        moveLogDao.deleteByGameRoomId(connection, gameRoomId);

        assertThat(moveLogDao.findByGameRoomId(connection, gameRoomId)).isEmpty();
    }

    private void truncate(ConnectionManager connectionManager) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM move_log");
            statement.execute("DELETE FROM board_piece");
            statement.execute("DELETE FROM game_room");
        }
    }
}
