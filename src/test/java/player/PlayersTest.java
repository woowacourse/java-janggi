package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static pieceProperty.PieceType.BYEONG;
import static pieceProperty.PieceType.CHO_JANGGUN;
import static pieceProperty.PieceType.JOL;
import static pieceProperty.PieceType.MA;
import static pieceProperty.PieceType.PO;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;

class PlayersTest {

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest() {
        //given
        Player hanPlayer = new Player(new JanggiPan(Map.of(new Position(5, 5), MA)));
        Player choPlayer = new Player(new JanggiPan(Map.of()));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertThat(players.isJanggunDie()).isTrue();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isNotGameOverTest() {
        //given
        Player hanPlayer = new Player(new JanggiPan(Map.of(new Position(8, 5), CHO_JANGGUN)));
        Player choPlayer = new Player(new JanggiPan(Map.of(new Position(8, 5), CHO_JANGGUN)));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertThat(players.isJanggunDie()).isFalse();
    }

    @Test
    @DisplayName("게임 종료 판단 테스트")
    void isGameOverTest2() {
        //given
        Player hanPlayer = new Player(new JanggiPan(Map.of()));
        Player choPlayer = new Player(new JanggiPan(Map.of(new Position(6, 5), MA)));
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));

        //when - then
        assertThat(players.isJanggunDie()).isTrue();
    }

    @Test
    @DisplayName("움직임 검증 테스트")
    void validateStartPosition() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(5, 5), MA, new Position(6, 5), JOL,
                new Position(4, 3), BYEONG
        ));

        JanggiPan janggiPan1 = new JanggiPan(Map.of(
                new Position(1, 7), MA, new Position(2, 5), JOL,
                new Position(3, 3), BYEONG
        ));


        Player player1 = new Player(janggiPan);
        Player player2 = new Player(janggiPan1);

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
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(5, 5), MA, new Position(6, 5), JOL,
                new Position(4, 3), BYEONG
        ));

        JanggiPan janggiPan1 = new JanggiPan(Map.of(
                new Position(1, 7), MA, new Position(2, 5), JOL,
                new Position(3, 3), BYEONG)
        );


        Player player1 = new Player(janggiPan);
        Player player2 = new Player(janggiPan1);

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
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(5, 5), MA, new Position(6, 5), JOL,
                new Position(4, 3), BYEONG
        ));

        JanggiPan janggiPan1 = new JanggiPan(Map.of(
                new Position(1, 7), MA, new Position(2, 5), JOL,
                new Position(3, 3), BYEONG)
        );


        Player player1 = new Player(janggiPan);
        Player player2 = new Player(janggiPan1);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

        //when-then
        assertThatThrownBy(() ->
                players.validateMovement(HAN, new Position(4, 3), new Position(3, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물이 움직일 수 없는 위치입니다.");

        assertThatThrownBy(() ->
                players.validateMovement(CHO, new Position(0, 0), new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다.");

    }

    @Test
    @DisplayName("경로 검증 테스트")
    void validateRoute() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(4, 5), MA, new Position(5, 5), MA,
                new Position(6, 5), JOL, new Position(4, 3), BYEONG
        ));

        JanggiPan janggiPan1 = new JanggiPan(Map.of(
                new Position(1, 7), JOL, new Position(2, 5), JOL,
                new Position(3, 3), BYEONG
        ));


        Player player1 = new Player(janggiPan);
        Player player2 = new Player(janggiPan1);

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
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(4, 5), MA, new Position(5, 5), MA,
                new Position(6, 5), JOL, new Position(4, 3), BYEONG
        ));

        JanggiPan janggiPan1 = new JanggiPan(Map.of(
                new Position(1, 7), MA, new Position(2, 5), JOL,
                new Position(3, 3), BYEONG
        ));

        Player player1 = new Player(janggiPan);
        Player player2 = new Player(janggiPan1);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

        //when
        players.capturePiece(CHO, new Position(1, 7), new Position(1, 6));

        //then
        assertThat(player2.getPieces().getPieces().containsKey(new Position(1, 6))).isTrue();
    }

    @Test
    @DisplayName("기물 삭제 테스트")
    void removePieceTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(1, 6), MA, new Position(4, 5), MA,
                new Position(5, 5), MA, new Position(6, 5), JOL,
                new Position(4, 3), BYEONG
        ));

        JanggiPan janggiPan1 = new JanggiPan(Map.of(
                new Position(1, 7), MA, new Position(2, 5), JOL,
                new Position(3, 3), BYEONG
        ));

        Player player1 = new Player(janggiPan);
        Player player2 = new Player(janggiPan1);

        Players players = new Players(Map.of(HAN, player1, CHO, player2));

        //when
        players.capturePiece(HAN, new Position(1, 6), new Position(1, 7));

        //then
        assertThat(player2.getPieces().getPieces().containsKey(new Position(1, 7))).isFalse();
    }

    @Test
    @DisplayName("경로에 포 존재 확인 테스트")
    void isPoExistInRoute() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(5, 5), PO
        ));
        Player player = new Player(janggiPan);

        Player player1 = new Player(new JanggiPan(Map.of(
                new Position(8, 5), PO)));

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
        JanggiPan janggiPan = new JanggiPan(Map.of(
                new Position(5, 5), PO, new Position(6, 5), JOL
        ));
        Player player = new Player(janggiPan);
        Player player1 = new Player(new JanggiPan(Map.of(
                new Position(8, 5), JOL)));

        Players players = new Players(Map.of(HAN, player1, CHO, player));

        //when - then
        assertThatThrownBy(() ->
                players.validateMovement(CHO, new Position(5, 5), new Position(9, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포가 가는 경로에 장애물이 2개 이상 존재하거나 포가 존재하여 이동할 수 없습니다.");
    }

}
