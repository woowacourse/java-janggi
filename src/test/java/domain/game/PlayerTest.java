package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Side;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    void 초나라_플레이어가_선플레이어이다() {
        // given & when
        Player player = new Player();

        // then
        assertThat(player.getSide()).isEqualTo(Side.CHO);
    }

    @Test
    void 턴이_끝나면_플레이어의_순서를_바꾼다() {
        // given
        Player player = new Player();

        // when
        player.change();

        // then
        assertThat(player.getSide()).isEqualTo(Side.HAN);
    }
}
