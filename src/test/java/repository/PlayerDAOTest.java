package repository;

import java.sql.SQLException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.mock.TestConnector;

public final class PlayerDAOTest {
    private static final Connector CONNECTOR = new TestConnector();
    private static final PlayerDAO PLAYER_REPOSITORY = new PlayerDAO(CONNECTOR);

    @Test
    @Disabled
    @DisplayName("Player 추가를 요청한다.")
    void test_create() throws SQLException {
//        //given
//        final int id = PLAYER_REPOSITORY.getNextId();
//        final Player player = new Player(id, Team.HAN);
//
//        //when
//        createGameForTest(CONNECTOR.getConnection());
//        PLAYER_REPOSITORY.createWithGameId(player, 1);
//
//        //then
//        final Player actual = PLAYER_REPOSITORY.findById(id);
//        assertThat(actual.getId()).isEqualTo(id);
    }

    @Test
    @Disabled
    @DisplayName("Player의 값을 수정한다.")
    void test_update() throws SQLException {
//        //given
//        final int id = PLAYER_REPOSITORY.getNextId();
//        final Player player = new Player(id, Team.CHO);
//        createGameForTest(CONNECTOR.getConnection());
//        PLAYER_REPOSITORY.createWithGameId(player, 1);
//
//        //when
//        player.addScore(new Score(1.0));
//        PLAYER_REPOSITORY.update(player);
//        //then
//        final Player actual = PLAYER_REPOSITORY.findById(id);
//        assertThat(actual.getScore()).isEqualTo(new Score(1.0));
    }

    @Test
    @Disabled
    @DisplayName("같은 보드에 속한 플레이어 정보를 요청한다.")
    void test_findAllByGameId() throws SQLException {
//        //given
//        final int id = PLAYER_REPOSITORY.getNextId();
//        final Player player = new Player(id, Team.CHO);
//        final int gameId = 1;
//        createGameForTest(CONNECTOR.getConnection());
//        PLAYER_REPOSITORY.createWithGameID(player, gameId);
//
//        //when
//
//        final List<Player> players = PLAYER_REPOSITORY.findAllByGameId(gameId);
//        //then
//        assertThat(players).contains(player);
    }
}
