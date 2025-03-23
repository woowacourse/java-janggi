package player;

import static org.assertj.core.api.Assertions.assertThat;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Janggun;
import pieceProperty.Position;

class PlayersTest {

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))), HAN);
        Player choPlayer = new Player(new Pieces(List.of()), CHO);
        Players players = new Players(List.of(choPlayer, hanPlayer));

        //when - then
        assertThat(players.isKingDie()).isTrue();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isNotGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))), HAN);
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))), CHO);
        Players players = new Players(List.of(choPlayer, hanPlayer));

        //when - then
        assertThat(players.isKingDie()).isFalse();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest2() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of()), HAN);
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))), CHO);
        Players players = new Players(List.of(choPlayer, hanPlayer));

        //when - then
        assertThat(players.isKingDie()).isTrue();
    }

}
