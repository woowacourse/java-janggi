import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Janggun;
import pieceProperty.Position;
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

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))), HAN);
        Player choPlayer = new Player(new Pieces(List.of()), CHO);
        JanggiGameState janggiGameState = new JanggiGameState(hanPlayer, choPlayer);

        //when - then
        assertThat(janggiGameState.isGameOver()).isTrue();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isNotGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))), HAN);
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))), CHO);
        JanggiGameState janggiGameState = new JanggiGameState(hanPlayer, choPlayer);

        //when - then
        assertThat(janggiGameState.isGameOver()).isFalse();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest2() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of()), HAN);
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))), CHO);
        JanggiGameState janggiGameState = new JanggiGameState(hanPlayer, choPlayer);

        //when - then
        assertThat(janggiGameState.isGameOver()).isTrue();
    }


}
