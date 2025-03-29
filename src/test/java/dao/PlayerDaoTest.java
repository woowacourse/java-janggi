package dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.player.Player;
import domain.player.Team;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PlayerDaoTest {

    private final PlayerDao playerDao = new PlayerDao();

    @Test
    void 연결을_생성한다() {
        try (final var connection = playerDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 이름과_팀으로_플레이어_정보를_저장한다() {
        playerDao.addPlayer(new Player("name", Team.HAN));
    }

    @Test
    void 플레이어의_이름과_팀으로_정보를_불러온다() {
        Optional<Player> optionalPlayer = playerDao.findByPlayerNameAndTeam("name", Team.HAN);
        assertThat(optionalPlayer.isEmpty()).isFalse();
    }
}
