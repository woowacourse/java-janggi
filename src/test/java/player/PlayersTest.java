package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Byeong;
import piece.Cha;
import piece.Janggun;
import piece.Jol;
import piece.Ma;
import piece.Po;
import pieceProperty.Position;

class PlayersTest {

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))));
        Player choPlayer = new Player(new Pieces(List.of()));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertThat(players.isJanggunDie()).isTrue();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isNotGameOverTest() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of(new Janggun(new Position(5, 5)))));
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertThat(players.isJanggunDie()).isFalse();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest2() {
        //given
        Player hanPlayer = new Player(new Pieces(List.of()));
        Player choPlayer = new Player(new Pieces(List.of(new Janggun(new Position(6, 5)))));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertThat(players.isJanggunDie()).isTrue();
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

        Player player1 = new Player(pieces1);
        Player player2 = new Player(pieces2);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

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

        Player player1 = new Player(pieces1);
        Player player2 = new Player(pieces2);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

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

        Player player1 = new Player(pieces1);
        Player player2 = new Player(pieces2);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

        //when-then
        assertThatThrownBy(() ->
                players.validateMovement(HAN, new Position(4, 3), new Position(3, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물이 움직일 수 없는 위치입니다.");

        assertThatThrownBy(() ->
                players.validateMovement(CHO, new Position(0, 0), new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물이 움직일 수 없는 위치입니다.");

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

        Player player1 = new Player(pieces1);
        Player player2 = new Player(pieces2);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

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

        Player player1 = new Player(pieces1);
        Player player2 = new Player(pieces2);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

        //when
        players.capturePiece(CHO, new Position(1, 7), new Position(1, 6));

        //then
        assertThat(janggun.isSamePosition(new Position(1, 6))).isTrue();
    }

    @Test
    @DisplayName("기물 삭제 테스트")
    void removePieceTest() {
        //given
        Janggun janggun = new Janggun(new Position(1, 7));
        Pieces pieces1 = new Pieces(List.of(
                new Janggun(new Position(1, 6)),
                new Ma(new Position(4, 5)),
                new Janggun(new Position(5, 5)), new Jol(new Position(6, 5))
                , new Byeong(new Position(4, 3))
        ));

        Pieces pieces2 = new Pieces(List.of(
                janggun, new Jol(new Position(2, 5))
                , new Byeong(new Position(3, 3))
        ));

        Player player1 = new Player(pieces1);
        Player player2 = new Player(pieces2);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

        //when
        players.capturePiece(HAN, new Position(1, 6), new Position(1, 7));

        //then
        assertThat(pieces2.getPieces().contains(janggun)).isFalse();
    }

    @Test
    @DisplayName("경로에 포 존재 확인 테스트")
    void isPoExistInRoute() {
        //given
        Po po = new Po(new Position(5, 5));

        Pieces pieces = new Pieces(List.of(po));

        Player player = new Player(pieces);
        Player player1 = new Player(new Pieces(List.of(new Po(new Position(8, 5)))));

        Players players = new Players(Map.of(HAN, player1, CHO, player));

        assertThatThrownBy(() ->
                players.validateMovement(CHO, new Position(5, 5), new Position(9, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포가 가는 경로에 장애물이 2개 이상 존재하거나 포가 존재하여 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("경로에 장애물 2개 이상 존재 확인 테스트")
    void isExistObstacleOverTwoInRoute() {
        //given
        Po po = new Po(new Position(5, 5));
        Jol jol = new Jol(new Position(6, 5));

        Pieces pieces = new Pieces(List.of(po, jol));

        Player player = new Player(pieces);
        Player player1 = new Player(new Pieces(List.of(new Jol(new Position(8, 5)))));

        Players players = new Players(Map.of(HAN, player1, CHO, player));

        //when - then
        assertThatThrownBy(() ->
                players.validateMovement(CHO, new Position(5, 5), new Position(9, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포가 가는 경로에 장애물이 2개 이상 존재하거나 포가 존재하여 이동할 수 없습니다.");
    }

}
