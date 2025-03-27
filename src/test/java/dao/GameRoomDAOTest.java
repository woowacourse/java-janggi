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

class GameRoomDAOTest {

    private Connection testConnection;
    private GameRoomDAO gameRoomDAO;

    @BeforeEach
    void setupConnection() throws SQLException {
        testConnection = DatabaseConnectionFixture.getTestConnection();
        testConnection.setAutoCommit(false);
        DatabaseSetting.settingTable(testConnection);
        gameRoomDAO = new GameRoomDAO(testConnection);
    }

    @AfterEach
    void rollbackConnection() throws SQLException {
        testConnection.rollback();
        testConnection.close();
    }

    @Test
    void GameRoomEntity를_추가한다() {
        // given
        final String name = "room2";
        final Team team = Team.HAN;
        final GameRoomEntity gameRoom = new GameRoomEntity(name, team);

        // when
        boolean actual = gameRoomDAO.insert(gameRoom);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void GameRoomEntity를_불러온다() {
        // given
        final String name = "room2";
        final Team team = Team.CHO;

        final GameRoomEntity gameRoom = new GameRoomEntity(name, team);
        gameRoomDAO.insert(gameRoom);

        // when
        Optional<GameRoomEntity> maybeGameRoom = gameRoomDAO.findByGameRoomName(name);

        // then
        SoftAssertions.assertSoftly(soflty -> {
            soflty.assertThat(maybeGameRoom.isPresent()).isTrue();
            soflty.assertThat(maybeGameRoom.get().name()).isEqualTo(name);
            soflty.assertThat(maybeGameRoom.get().turn()).isEqualTo(team);
        });
    }

    @Test
    void GameRoomEntity를_제거한다() {
        // given
        final String name = "room1";
        final Team team = Team.HAN;
        final GameRoomEntity gameRoom = new GameRoomEntity(name, team);
        gameRoomDAO.insert(gameRoom);

        // when
        boolean actual = gameRoomDAO.deleteByGameRoomName(name);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}