package dao;

import static org.assertj.core.api.Assertions.assertThat;

import db.ConnectionManager;
import db.DatabaseInitializer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameRoomDaoTest {

    private ConnectionManager connectionManager;
    private Connection connection;
    private GameRoomDao gameRoomDao;

    @BeforeEach
    void setUp() throws SQLException {
        connectionManager = new ConnectionManager();
        new DatabaseInitializer(connectionManager).initialize();
        truncate(connectionManager);
        connection = connectionManager.getConnection();
        gameRoomDao = new GameRoomDao();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("게임방을 저장하면 생성된 id로 조회할 수 있다")
    void saveAndFindById() {
        long id = gameRoomDao.save(connection, "테스트방", "CHO", "RUNNING", 0);

        Optional<GameRoomRawData> found = gameRoomDao.findById(connection, id);

        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("테스트방");
        assertThat(found.get().currentTurn()).isEqualTo("CHO");
        assertThat(found.get().status()).isEqualTo("RUNNING");
        assertThat(found.get().consecutivePassCount()).isZero();
    }

    @Test
    @DisplayName("저장된 모든 게임방을 id 순으로 조회한다")
    void findAll() {
        gameRoomDao.save(connection, "방1", "CHO", "RUNNING", 0);
        gameRoomDao.save(connection, "방2", "HAN", "FINISHED", 1);

        List<GameRoomRawData> rooms = gameRoomDao.findAll(connection);

        assertThat(rooms).hasSize(2);
        assertThat(rooms).extracting(GameRoomRawData::name).containsExactly("방1", "방2");
    }

    @Test
    @DisplayName("게임방을 업데이트하면 변경된 값이 조회된다")
    void update() {
        long id = gameRoomDao.save(connection, "방", "CHO", "RUNNING", 0);

        gameRoomDao.update(connection, id, "HAN", "FINISHED", 2);

        GameRoomRawData updated = gameRoomDao.findById(connection, id).orElseThrow();
        assertThat(updated.currentTurn()).isEqualTo("HAN");
        assertThat(updated.status()).isEqualTo("FINISHED");
        assertThat(updated.consecutivePassCount()).isEqualTo(2);
    }

    private void truncate(ConnectionManager connectionManager) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM board_piece");
            statement.execute("DELETE FROM game_room");
        }
    }
}
