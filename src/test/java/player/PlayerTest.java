package player;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Side;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    @DisplayName("선택한 진영을 가진 플레이어가 생성된다.")
    void of_ReturnPlayer_WhenCreateWithSide() {
        // given, when
        Player player = Player.of(Side.CHO);

        // then
        assertThat(player.getSide()).isEqualTo(Side.CHO);
    }
}
