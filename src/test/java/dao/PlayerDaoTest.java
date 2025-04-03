package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import domain.player.Player;
import domain.player.Team;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerDaoTest {

    private final TestDatabaseConnectionManager connectionManager = new TestDatabaseConnectionManager();
    private final PlayerDao playerDao = new PlayerDao(connectionManager);

    @BeforeEach
    void setUp() {
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement("DELETE FROM player")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 테스트 데이터 초기화를 실패했습니다.");
        }
    }

    @Test
    void 이름과_팀으로_플레이어_정보를_저장한다() {
        assertThatCode(() -> playerDao.addPlayer(new Player("name", Team.HAN)))
                .doesNotThrowAnyException();
    }

    @Test
    void 플레이어의_팀으로_정보를_불러온다() {
        // given
        playerDao.addPlayer(new Player("name", Team.HAN));

        // when
        Optional<Player> optionalPlayer = playerDao.findPlayerByTeam(Team.HAN);

        // then
        assertThat(optionalPlayer).isNotEmpty();
    }

    @Test
    void 플레이어_정보를_삭제한다() {
        // given
        playerDao.addPlayer(new Player("name", Team.HAN));

        // when
        playerDao.clear();

        // then
        assertThat(playerDao.findPlayerByTeam(Team.HAN)).isEmpty();
    }
}
