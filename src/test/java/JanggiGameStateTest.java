import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static pieceProperty.PieceType.BYEONG;
import static pieceProperty.PieceType.CHO_JANGGUN;
import static pieceProperty.PieceType.JOL;
import static pieceProperty.PieceType.MA;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;
import player.JanggiPan;
import player.Player;
import player.Players;

class JanggiGameStateTest {

    @Test
    @DisplayName("장기 게임 상태는 플레이어들과 턴을 가진다.")
    void janggiGameStateTest() {
        //given
        Player hanPlayer = new Player(new JanggiPan(Map.of()));
        Player choPlayer = new Player(new JanggiPan(Map.of()));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertDoesNotThrow(() -> new JanggiGameState(players));
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest() {
        //given
        Player hanPlayer = new Player(new JanggiPan(Map.of(
                new Position(5, 5), MA)));
        Player choPlayer = new Player(new JanggiPan(Map.of()));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when - then
        assertThat(janggiGameState.isGameOver()).isTrue();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isNotGameOverTest() {
        //given
        Player hanPlayer = new Player(new JanggiPan(Map.of(
                new Position(8, 5), CHO_JANGGUN)));
        Player choPlayer = new Player(new JanggiPan(Map.of(
                new Position(0, 5), CHO_JANGGUN)));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when - then
        assertThat(janggiGameState.isGameOver()).isFalse();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest2() {
        //given
        Player hanPlayer = new Player(new JanggiPan(Map.of()));
        Player choPlayer = new Player(new JanggiPan(Map.of(
                new Position(5, 5), MA)));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when - then
        assertThat(janggiGameState.isGameOver()).isTrue();
    }

    @Test
    @DisplayName("기물 이동 테스트")
    void movePieceTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(1, 6), MA, new Position(4, 5), MA,
                new Position(5, 5), MA, new Position(6, 5), JOL,
                new Position(4, 3), BYEONG
        ));
        JanggiPan janggiPan1 = new JanggiPan(Map.of(
                new Position(1, 7), JOL, new Position(2, 5), JOL,
                new Position(3, 3),BYEONG
        ));

        Player player1 = new Player(janggiPan);
        Player player2 = new Player(janggiPan1);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        //when
        janggiGameState.movePiece(CHO, new Position(1, 7), new Position(1, 6));

        //then
        assertThat(player2.getPieces().getPieces().containsKey(new Position(1, 6))).isTrue();
        assertThat(player1.getPieces().getPieces().containsKey(new Position(1, 6))).isFalse();
    }

}
