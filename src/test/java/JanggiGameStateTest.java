import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Byeong;
import piece.Janggun;
import piece.Jol;
import piece.Ma;
import pieceProperty.Position;
import player.Pieces;
import player.Player;
import player.Players;

class JanggiGameStateTest {

    @Test
    @DisplayName("장기 게임 상태는 플레이어들과 턴을 가진다.")
    void janggiGameStateTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of()));
        Player choPlayer = new Player(new Pieces(List.of()));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertDoesNotThrow(() -> new JanggiGameState(players));
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))));
        Player choPlayer = new Player(new Pieces(List.of()));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when - then
        assertThat(janggiGameState.isGameOver()).isTrue();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isNotGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))));
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when - then
        assertThat(janggiGameState.isGameOver()).isFalse();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest2() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of()));
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when - then
        assertThat(janggiGameState.isGameOver()).isTrue();
    }

    @Test
    @DisplayName("기물 이동 테스트")
    void movePieceTest() {
        //given
        Janggun janggun = new Janggun(new Position(1, 7));

        Ma ma = new Ma(new Position(1, 6));

        Pieces pieces1 = new Pieces(List.of(
                new Ma(new Position(4, 5)),
                new Janggun(new Position(5, 5)), new Jol(new Position(6, 5))
                , new Byeong(new Position(4, 3)), ma
        ));

        Pieces pieces2 = new Pieces(List.of(
                janggun, new Jol(new Position(2, 5))
                , new Byeong(new Position(3, 3))
        ));

        Player player1 = new Player(pieces1);
        Player player2 = new Player(pieces2);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when
        janggiGameState.movePiece(new Position(1, 7), new Position(1, 6));

        //then
        assertThat(janggiGameState.getAttackNation().equals(HAN)).isTrue();
        assertThat(janggun.isSamePosition(new Position(1, 6))).isTrue();
        assertThat(pieces2.getPieces().contains(ma)).isFalse();
    }

}
