package janggi.infra.dao;

import janggi.infra.datasource.H2DataSourceFactory;
import janggi.infra.dto.GameRoomData;
import janggi.infra.dto.PieceData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesDaoTest {

    private PiecesDao dao;
    private Connection connection;
    private Long roomId;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = H2DataSourceFactory.create();
        connection = dataSource.getConnection();
        dao = new PiecesDao();
        GameRoomDao roomDao = new GameRoomDao();
        GameRoomData newData = new GameRoomData("CHO", "HAN", 10, 10);
        roomId = roomDao.save(newData, connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("기물을 저장하면, 조회 시 기물 타입과 팀, 위치가 일치 해야한다.")
    void save_piece_select() {
        List<PieceData> pieceData = List.of(
                new PieceData("MA", "CHO", 0, 1)
        );
        dao.save(roomId, pieceData, connection);
        List<PieceData> data = dao.findAllByRoomId(roomId, connection);
        Assertions.assertThat(data.getFirst()).isNotNull();
        Assertions.assertThat(data.getFirst().pieceName()).isEqualTo("MA");
        Assertions.assertThat(data.getFirst().teamName()).isEqualTo("CHO");
        Assertions.assertThat(data.getFirst().row()).isEqualTo(0);
        Assertions.assertThat(data.getFirst().column()).isEqualTo(1);
    }

    @Test
    @DisplayName("기물을 이동하면, 조회 시 위치가 변경되어야 한다.")
    void move_piece() {
        List<PieceData> pieceData = List.of(
                new PieceData("MA", "CHO", 1, 0)
        );
        dao.save(roomId, pieceData, connection);
        dao.update(roomId, 1, 0, 2, 2, connection);
        List<PieceData> data = dao.findAllByRoomId(roomId, connection);
        Assertions.assertThat(data.getFirst().row()).isEqualTo(2);
        Assertions.assertThat(data.getFirst().column()).isEqualTo(2);
    }

    @Test
    @DisplayName("기물을 삭제하면, 조회 시 해당 기물이 존재하지 않아야 한다.")
    void delete_piece() {
        List<PieceData> pieceData = List.of(
                new PieceData("MA", "CHO", 0, 1)
        );
        dao.save(roomId, pieceData, connection);
        dao.delete(roomId, 0, 1, connection);
        List<PieceData> data = dao.findAllByRoomId(roomId, connection);
        Assertions.assertThat(data).isEmpty();
    }
}
