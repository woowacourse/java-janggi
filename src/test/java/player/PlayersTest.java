package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Byeong;
import piece.Cha;
import piece.Janggun;
import piece.Jol;
import piece.Ma;
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

    @Test
    @DisplayName("움직임 검증 테스트")
    void validateStartPosition() {
        //given
        Pieces pieces1 = new Pieces(List.of(
                new Janggun(new Position(5, 5)), new Jol(new Position(6, 5))
                , new Byeong(new Position(4, 3))
        ));

        Pieces pieces2 = new Pieces(List.of(
                new Janggun(new Position(1, 7)), new Jol(new Position(2, 5))
                , new Byeong(new Position(3, 3))
        ));

        Player player1 = new Player(pieces1, HAN);
        Player player2 = new Player(pieces2, CHO);

        Players players = new Players(List.of(player1, player2));

        //when-then
        assertThatThrownBy(() ->
                players.validateMovement(HAN, new Position(1, 7), new Position(2, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("움직임 검증 테스트")
    void validateDestinationPosition() {
        //given
        Pieces pieces1 = new Pieces(List.of(
                new Janggun(new Position(5, 5)), new Jol(new Position(6, 5))
                , new Byeong(new Position(4, 3))
        ));

        Pieces pieces2 = new Pieces(List.of(
                new Janggun(new Position(1, 7)), new Jol(new Position(2, 5))
                , new Byeong(new Position(3, 3))
        ));

        Player player1 = new Player(pieces1, HAN);
        Player player2 = new Player(pieces2, CHO);

        Players players = new Players(List.of(player1, player2));

        //when-then
        assertThatThrownBy(() ->
                players.validateMovement(HAN, new Position(5, 5), new Position(6, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 도착지에 아군 기물이 존재합니다.");
    }

    @Test
    @DisplayName("움직임 검증 테스트")
    void validateCanPieceMoveTo() {
        //given
        Pieces pieces1 = new Pieces(List.of(
                new Janggun(new Position(5, 5)), new Jol(new Position(6, 5))
                , new Byeong(new Position(4, 3))
        ));

        Pieces pieces2 = new Pieces(List.of(
                new Janggun(new Position(1, 7)), new Jol(new Position(2, 5))
                , new Byeong(new Position(3, 3)), new Cha(new Position(0, 0))
        ));

        Player player1 = new Player(pieces1, HAN);
        Player player2 = new Player(pieces2, CHO);

        Players players = new Players(List.of(player1, player2));

        //when-then
        assertThatThrownBy(() ->
                players.validateMovement(HAN, new Position(4, 3), new Position(3, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 병이 움직일 수 없는 위치 입니다.");

        assertThatThrownBy(() ->
                players.validateMovement(CHO, new Position(0, 0), new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차가 움직일 수 없는 위치 입니다.");

    }

    @Test
    @DisplayName("경로 검증 테스트")
    void validateRoute() {
        //given
        Pieces pieces1 = new Pieces(List.of(
                new Ma(new Position(4, 5)),
                new Janggun(new Position(5, 5)), new Jol(new Position(6, 5))
                , new Byeong(new Position(4, 3))
        ));

        Pieces pieces2 = new Pieces(List.of(
                new Janggun(new Position(1, 7)), new Jol(new Position(2, 5))
                , new Byeong(new Position(3, 3))
        ));

        Player player1 = new Player(pieces1, HAN);
        Player player2 = new Player(pieces2, CHO);

        Players players = new Players(List.of(player1, player2));

        //when-then
        assertThatThrownBy(() ->
                players.validateMovement(HAN, new Position(4, 5), new Position(6, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장애물이 존재하여 이동할 수 없습니다.");

        assertDoesNotThrow(() ->
                players.validateMovement(CHO, new Position(1, 7), new Position(1, 6)));
    }

    @Test
    @DisplayName("기물 이동 테스트")
    void movePieceTest() {
        //given
        Janggun janggun = new Janggun(new Position(1, 7));
        Pieces pieces1 = new Pieces(List.of(
                new Ma(new Position(4, 5)),
                new Janggun(new Position(5, 5)), new Jol(new Position(6, 5))
                , new Byeong(new Position(4, 3))
        ));

        Pieces pieces2 = new Pieces(List.of(
                janggun, new Jol(new Position(2, 5))
                , new Byeong(new Position(3, 3))
        ));

        Player player1 = new Player(pieces1, HAN);
        Player player2 = new Player(pieces2, CHO);

        Players players = new Players(List.of(player1, player2));

        //when
        players.movePiece(CHO, new Position(1, 7), new Position(1, 6));

        //then
        assertThat(janggun.isSamePosition(new Position(1, 6))).isTrue();
    }


}
