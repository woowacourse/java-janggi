import static org.junit.jupiter.api.Assertions.*;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import player.Pieces;
import player.Player;

class JanggiGameStateTest {

    @Test
    @DisplayName("장기 게임 상태는 플레이어들과 턴을 가진다.")
    void janggiGameStateTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of()), HAN);
        Player choPlayer = new Player(new Pieces(List.of()), CHO);

        //when - then
        assertDoesNotThrow(() -> new JanggiGameState(hanPlayer, choPlayer));
    }


}
