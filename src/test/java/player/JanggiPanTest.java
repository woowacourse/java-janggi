package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.Map;
import movementRule.Byeong;
import movementRule.Jol;
import movementRule.Ma;
import movementRule.linearMover.Cha;
import movementRule.linearMover.Po;
import movementRule.omniDirectionMover.HanSa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

class JanggiPanTest {

    @Test
    @DisplayName("장기 기물 이동 테스트")
    void moveTest() {
        //given
        Piece piece = new Piece(new Cha());
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), piece)
        );

        //when
        janggiPan.movePiece(new Position(5, 5), new Position(6, 5));

        //then
        assertThat(
                janggiPan.getPieces().get(new Position(6, 5))
                        .equals(piece))
                .isTrue();
    }

    @Test
    @DisplayName("시작 지점 아군 판별 테스트")
    void validateAllyPieceAtStartTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Cha()))
        );

        //when - then
        assertThatThrownBy(() -> janggiPan.validateAllyPieceAtStart(new Position(6, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다.");
        assertDoesNotThrow(() -> janggiPan.validateAllyPieceAtStart(new Position(5, 5)));
    }

    @Test
    @DisplayName("목적지 아군 판별 테스트")
    void validateAllyPieceAtDestinationTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Cha()))
        );

        //when - then
        assertThatThrownBy(() -> janggiPan.validateAllyPieceAtDestination(new Position(5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 도착지에 아군 기물이 존재합니다.");
        assertDoesNotThrow(() -> janggiPan.validateAllyPieceAtDestination(new Position(6, 5)));
    }

    @Test
    @DisplayName("기물 움직임 가능 테스트")
    void canPieceMoveToTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Cha()))
        );

        //when - then
        assertThatThrownBy(() -> janggiPan.canPieceMoveTo(new Position(5, 5), new Position(6, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));

        assertDoesNotThrow(() -> janggiPan.canPieceMoveTo(new Position(5, 5), new Position(6, 5)));
    }

    @Test
    @DisplayName("장애물 개수 확인 테스트")
    void countObstacleTest() {
        //given
        Position position = new Position(5, 5);
        Position position1 = new Position(5, 6);
        Position position2 = new Position(5, 7);
        Position position3 = new Position(5, 8);

        Jol jol = new Jol();
        Byeong byeong = new Byeong();
        Ma ma = new Ma();
        Cha cha = new Cha();

        JanggiPan janggiPan = new JanggiPan(
                Map.of(
                        position, new Piece(cha), position1, new Piece(ma), position2, new Piece(byeong)
                        , position3, new Piece(jol)
                )
        );

        //when
        int count = janggiPan.countObstacle(new Positions(List.of(
                position, position1, position2, position3
        )));

        //then
        assertThat(count == 4).isTrue();
    }

    @Test
    @DisplayName("경로 작성 테스트")
    void makeRoutTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Cha()))
        );

        //when
        Positions actual = janggiPan.makeRoute(new Position(5, 5), new Position(0, 5));

        //then
        assertThat(actual.getPositions()).containsExactly(
                new Position(4, 5),
                new Position(3, 5),
                new Position(2, 5),
                new Position(1, 5)
        );
    }

    @Test
    @DisplayName("장군 사망 테스트")
    void hasJanggunTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Cha()))
        );

        //when - then
        assertThat(janggiPan.hasJanggun()).isTrue();
    }

    @Test
    @DisplayName("시작 지점 포 판별 테스트")
    void isPoAtTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Po()))
        );

        //when - then
        assertThat(janggiPan.isPoAt(new Position(5, 5))).isTrue();
    }

    @Test
    @DisplayName("경로 상에 포 판별 테스트")
    void isExistPoInRouteTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Po()))
        );
        Positions route = new Positions(List.of(
                new Position(1, 5), new Position(5, 5), new Position(4, 5), new Position(3, 5)
        ));
        Positions route1 = new Positions(
                List.of(
                        new Position(4, 5), new Position(3, 5)
                )
        );

        //when - then
        assertThat(janggiPan.isExistPoInRoute(route)).isTrue();
        assertThat(janggiPan.isExistPoInRoute(route1)).isFalse();
    }

    @Test
    @DisplayName("기물 삭제 테스트")
    void removePieceTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), new Piece(new Po()))
        );

        //when
        janggiPan.removePiece(new Position(5, 5));

        //then
        assertThat(janggiPan.getPieces().containsKey(new Position(5, 5))).isFalse();
    }

}
