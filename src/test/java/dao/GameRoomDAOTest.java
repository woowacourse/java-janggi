package dao;

import dao.init.DatabaseSetting;
import domain.piece.character.Team;
import fixture.DatabaseConnectionFixture;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameRoomDaoTest {

    private Connection testConnection;
    private GameRoomDao gameRoomDao;

    @BeforeEach
    void setupConnection() throws SQLException {
        testConnection = DatabaseConnectionFixture.getTestConnection();
        testConnection.setAutoCommit(false);
        DatabaseSetting.settingTable(testConnection);
        gameRoomDao = new GameRoomDao();
    }

    @AfterEach
    void rollbackConnection() throws SQLException {
        testConnection.rollback();
        testConnection.close();
    }

    @Test
    void row를_추가한다() {
        // given
        final String name = "room2";
        final Team turn = Team.HAN;
        final GameRoomEntity gameRoom = new GameRoomEntity(name, turn);

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

        final GameRoomEntity gameRoom = new GameRoomEntity(name, turn);
        gameRoomDao.insert(testConnection, gameRoom);

        // when
        Optional<GameRoomEntity> maybeGameRoom = gameRoomDao.findByName(testConnection, name);

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
        final GameRoomEntity gameRoom = new GameRoomEntity(name, turn);
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
        final GameRoomEntity gameRoom = new GameRoomEntity(name, turn);
        gameRoomDao.insert(testConnection, gameRoom);

        // when
        boolean actual = gameRoomDao.deleteByGameRoomName(testConnection, name);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}