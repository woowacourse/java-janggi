package dao;

import dao.converter.GameRoomDto;
import dao.gameroom.GameRoomDao;
import dao.gameroom.GameRoomDaoImpl;
import dao.init.ConnectionGenerator;
import domain.piece.character.Team;
import fixture.EmbeddedH2ConnectionGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRoomDaoTest {

    private final ConnectionGenerator connectionGenerator = new EmbeddedH2ConnectionGenerator();
    private final GameRoomDao gameRoomDao = new GameRoomDaoImpl();
    private Connection testConnection;

    @BeforeEach
    void setup() throws SQLException {
        testConnection = connectionGenerator.createConnection();
        testConnection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        testConnection.rollback();
        testConnection.close();
    }

    @Test
    void row를_추가한다() {
        // given
        final String name = "room2";
        final Team turn = Team.HAN;
        final GameRoomDto gameRoom = new GameRoomDto(null, name, turn);

        // when
        boolean actual = gameRoomDao.insert(testConnection, gameRoom);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 방_이름으로_조회한다() {
        // given
        final String name = "room2";
        final Team turn = Team.CHO;

        final GameRoomDto gameRoom = new GameRoomDto(null, name, turn);
        gameRoomDao.insert(testConnection, gameRoom);

        // when
        Optional<GameRoomDto> maybeGameRoom = gameRoomDao.findByName(testConnection, name);

        // then
        SoftAssertions.assertSoftly(soflty -> {
            soflty.assertThat(maybeGameRoom.isPresent()).isTrue();
            soflty.assertThat(maybeGameRoom.get().name()).isEqualTo(name);
            soflty.assertThat(maybeGameRoom.get().turn()).isEqualTo(turn);
        });
    }

    @Test
    void 방_이름으로_조회하여_턴을_바꾼다() {
        // given
        final String name = "room1";
        final Team turn = Team.HAN;
        final GameRoomDto gameRoom = new GameRoomDto(null, name, turn);
        gameRoomDao.insert(testConnection, gameRoom);

        // when
        boolean actual = gameRoomDao.updateTurnByGameRoomName(testConnection, name, Team.CHO);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 방_이름으로_조회하여_row를_제거한다() {
        // given
        final String name = "room1";
        final Team turn = Team.HAN;
        final GameRoomDto gameRoom = new GameRoomDto(null, name, turn);
        gameRoomDao.insert(testConnection, gameRoom);

        // when
        boolean actual = gameRoomDao.deleteByGameRoomName(testConnection, name);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}