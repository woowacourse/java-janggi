package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.janggi.domain.Color;
import domain.janggi.domain.Player;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void 플레이어는_팀을_가진다() {
        Player player = new Player(Color.BLUE);

        assertThat(player.getTeam()).isEqualTo(Color.BLUE);
    }

    @Test
    void 플레이어는_기물을_가진다() {
        Player player = new Player(Color.BLUE);

        assertThat(player.getTeam()).isEqualTo(Color.BLUE);
    }
}
