package repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static repository.mock.TestConnector.createRoomForTest;

import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.mock.TestConnector;

public final class PlayerDAOTest {
    private static final Connector CONNECTOR = new TestConnector();
    private static final PlayerDAO PLAYER_REPOSITORY = new PlayerDAO(CONNECTOR);

    @Test
    @DisplayName("Player 추가를 요청한다.")
    void test_create() throws SQLException {
        //given
        final int id = PLAYER_REPOSITORY.findNextId();
        final Player player = new Player(id, Team.HAN);

        //when
        createRoomForTest(CONNECTOR.getConnection());
        PLAYER_REPOSITORY.createWithRoomId(player, 1);

        //then
        final Player actual = PLAYER_REPOSITORY.findById(id);
        assertThat(actual.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Player의 값을 수정한다.")
    void test_update() throws SQLException {
        //given
        final int id = PLAYER_REPOSITORY.findNextId();
        final Player player = new Player(id, Team.CHO);
        createRoomForTest(CONNECTOR.getConnection());
        PLAYER_REPOSITORY.createWithRoomId(player, 1);

        //when
        player.addScore(new Score(1.0));
        PLAYER_REPOSITORY.update(player);
        //then
        final Player actual = PLAYER_REPOSITORY.findById(id);
        assertThat(actual.getScore()).isEqualTo(new Score(1.0));
    }

    @Test
    @DisplayName("같은 보드에 속한 플레이어 정보를 요청한다.")
    void test_findAllByRoomId() throws SQLException {
        //given
        final int id = PLAYER_REPOSITORY.findNextId();
        final Player player = new Player(id, Team.CHO);
        final int roomId = 1;
        createRoomForTest(CONNECTOR.getConnection());
        PLAYER_REPOSITORY.createWithRoomId(player, roomId);

        //when

        final List<Player> players = PLAYER_REPOSITORY.findAllByRoomId(roomId);
        //then
        assertThat(players).contains(player);
    }

    @Test
    @DisplayName("테이블의 가장 큰 Id 값을 가져온다.")
    void test_findNextId() {
        assertDoesNotThrow(PLAYER_REPOSITORY::findNextId);
    }
}
