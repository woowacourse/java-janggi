package janggi.infra.dao;

import janggi.infra.datasource.H2DataSourceFactory;
import janggi.infra.dto.GameRoomData;
import janggi.infra.transaction.ConnectionContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameRoomDaoTest {

    private GameRoomDao dao;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = H2DataSourceFactory.create();
        Connection connection = dataSource.getConnection();
        ConnectionContext.setConnection(connection);
        dao = new GameRoomDao();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("게임방을 저장하면, 조회 시 게임턴과 승자, 점수가 일치 해야한다.")
    void room_save_select() {
        GameRoomData newData = new GameRoomData("CHO", "HAN", 10, 10);
        Long roomId = dao.save(newData);
        Optional<GameRoomData> loadedData = dao.findRoomById(roomId);
        GameRoomData data = loadedData.orElseThrow();
        Assertions.assertThat(data.currentTurn()).isEqualTo(newData.currentTurn());
        Assertions.assertThat(data.winner()).isEqualTo(newData.winner());
        Assertions.assertThat(data.hanScore()).isEqualTo(newData.hanScore());
        Assertions.assertThat(data.choScore()).isEqualTo(newData.choScore());
    }

    @Test
    @DisplayName("게임방 정보를 갱신하면, 조회 시 바뀐 내용이 저장되어 있어야한다.")
    void update_info() {
        GameRoomData newData = new GameRoomData("CHO", "HAN", 10, 10);
        Long roomId = dao.save(newData);
        GameRoomData editData = new GameRoomData("HAN", "HAN", 0, 10);
        dao.update(roomId, editData);
        Optional<GameRoomData> loadedData = dao.findRoomById(roomId);
        GameRoomData data = loadedData.orElseThrow();
        Assertions.assertThat(data.currentTurn()).isEqualTo(editData.currentTurn());
        Assertions.assertThat(data.winner()).isEqualTo(editData.winner());
        Assertions.assertThat(data.hanScore()).isEqualTo(editData.hanScore());
        Assertions.assertThat(data.choScore()).isEqualTo(editData.choScore());
    }
}
