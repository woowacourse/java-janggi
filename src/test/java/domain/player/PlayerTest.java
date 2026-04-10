package domain.player;

import domain.piece.Side;
import domain.players.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {

    @Test
    @DisplayName("선택한 진영을 가진 플레이어가 생성된다.")
    void 진영에_맞는_플레이어_테스트() {
        // given, when
        Player player = new Player(Side.CHO);

        // then
        assertThat(player.getSide()).isEqualTo(Side.CHO);
    }
}
