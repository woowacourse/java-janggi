package player;

import domain.board.Board;
import domain.piece.Side;
import domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    @DisplayName("선택한 진영을 가진 플레이어가 생성된다.")
    void of_ReturnPlayer_WhenCreateWithSide() {
        // given, when
        Player player = new Player(Side.CHO, new Board());

        // then
        assertThat(player.getSide()).isEqualTo(Side.CHO);
    }
}
