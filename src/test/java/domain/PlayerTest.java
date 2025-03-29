package domain;

import domain.piece.Score;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void 플레이어의_점수를_증가한다() {
        // given
        Player player = new Player(Team.HAN, new Score(0));

        // when
        player.increaseScore(new Score(10));

        // then
        Assertions.assertThat(player.getScore().value()).isEqualTo(10);
    }
}
