package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static pieceProperty.PieceType.BYEONG;
import static pieceProperty.PieceType.CHA;
import static pieceProperty.PieceType.JOL;
import static pieceProperty.PieceType.MA;
import static pieceProperty.PieceType.PO;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

class JanggiPanTest {

    @Test
    @DisplayName("장기 기물 이동 테스트")
    void moveTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), CHA)
        );

        //when
        janggiPan.movePiece(new Position(5, 5), new Position(6, 5));

        //then
        assertThat(
                janggiPan.getPieces().get(new Position(6, 5))
                        .equals(CHA))
                .isTrue();
    }

    @Test
    @DisplayName("시작 지점 아군 판별 테스트")
    void validateAllyPieceAtStartTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), CHA)
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
                Map.of(new Position(5, 5), CHA)
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
                Map.of(new Position(5, 5), CHA)
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

        JanggiPan janggiPan = new JanggiPan(
                Map.of(
                        position, CHA, position1,MA, position2, BYEONG
                        , position3, JOL
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
                Map.of(new Position(5, 5), CHA)
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
                Map.of(new Position(5, 5), CHA)
        );

        //when - then
        assertThat(janggiPan.hasJanggun()).isTrue();
    }

    @Test
    @DisplayName("시작 지점 포 판별 테스트")
    void isPoAtTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), PO)
        );

        //when - then
        assertThat(janggiPan.isPoAt(new Position(5, 5))).isTrue();
    }

    @Test
    @DisplayName("경로 상에 포 판별 테스트")
    void isExistPoInRouteTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(
                Map.of(new Position(5, 5), PO)
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
                Map.of(new Position(5, 5), PO)
        );

        //when
        janggiPan.removePiece(new Position(5, 5));

        //then
        assertThat(janggiPan.getPieces().containsKey(new Position(5, 5))).isFalse();
    }

}
