package repository;

import domain.player.Player;
import domain.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.mock.TestConnector;

public final class PlayerRepositoryTest {
    private static final Connector CONNECTOR = new TestConnector();
    private static final Repository<Player> PLAYER_REPOSITORY = new PlayerRepository(CONNECTOR);

    @Test
    @DisplayName("Player 추가를 요청한다.")
    void test_create() {
        final Player player = new Player(0, Team.HAN);
        PLAYER_REPOSITORY.create(player);
    }
}
