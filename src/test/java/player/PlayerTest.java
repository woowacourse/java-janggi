package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static pieceProperty.PieceType.CHO_JANGGUN;
import static pieceProperty.PieceType.JOL;
import static pieceProperty.PieceType.MA;
import static pieceProperty.PieceType.PO;

import java.util.List;
import java.util.Map;
import movementRule.Jol;
import movementRule.linearMover.Po;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;
import pieceProperty.Positions;

class PlayerTest {
    @Test
    @DisplayName("플레이어는 국가와 기물들을 가진다")
    void playerTest() {
        JanggiPan janggiPan = new JanggiPan(Map.of());

        assertDoesNotThrow(() -> new Player(janggiPan));
    }

    @Test
    @DisplayName("왕이 죽었는지 판단 테스트")
    void isJanggunDieTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL));
        Player player = new Player(janggiPan);

        //when - then
        assertThat(player.isJanggunDie()).isTrue();
    }

    @Test
    @DisplayName("왕이 살았는지 판단 테스트")
    void isKingNotDieTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(8, 5), CHO_JANGGUN));
        Player player = new Player(janggiPan);

        //when - then
        assertThat(player.isJanggunDie()).isFalse();
    }

    @Test
    @DisplayName("출발 지점에 있는 기물이 도착 지점에 갈 수 있는지 테스트")
    void validateAllyPieceAtStartTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(8, 4), CHO_JANGGUN));

        Player player = new Player(janggiPan);

        //when - then
        assertThatThrownBy(() -> player.validateAllyPieceAtStart(new Position(5,6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다.");


        assertDoesNotThrow(() -> player.canPieceMoveTo(new Position(5, 5), new Position(5, 6)));
    }

    @Test
    @DisplayName("도착 지점 아군 판별 테스트")
    void validateAllyPieceAtDestination() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(6, 4), MA));

        Player player = new Player(janggiPan);

        //when - then
        assertThatThrownBy(() -> player.validateAllyPieceAtDestination(new Position(5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 도착지에 아군 기물이 존재합니다.");

        assertDoesNotThrow(() -> player.validateAllyPieceAtDestination(new Position(5, 7)));
    }

    @Test
    @DisplayName("출발 지점에 있는 기물이 도착 지점에 갈 수 있는지 테스트")
    void canPieceMoveToTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(6, 4), MA));

        Player player = new Player(janggiPan);

        //when - then
        assertThatThrownBy(() -> player.canPieceMoveTo(new Position(5,5), new Position(5, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물이 움직일 수 없는 위치입니다.");

        assertDoesNotThrow(() -> player.canPieceMoveTo(new Position(5, 5), new Position(5, 6)));
    }

    @Test
    @DisplayName("기물 이동 경로 작성 테스트")
    void makeRouteTest() {
        //given
        Jol jol = new Jol();
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL));

        Player player = new Player(janggiPan);

        Position position = new Position(5, 6);

        //when
        Positions actual = player.makeRoute(new Position(5, 5), position);

        //then
        assertThat(actual.getPositions().contains(position)).isFalse();
    }

    @Test
    @DisplayName("경로 상에 같은 위치 기물 개수 확인")
    void countObstacleTest() {
        //given
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(6, 5), MA,
                new Position(4, 3), PO));

        Player player = new Player(janggiPan);

        Positions positions = new Positions(List.of(
                new Position(2, 5), new Position(7, 5), new Position(1,  3)
        ));

        //when
        int actual = player.countObstacle(positions);

        //then
        assertThat(actual).isEqualTo(0);
    }

    @Test
    @DisplayName("기물 이동 테스트")
    void movePieceTest() {
        //given
        //given
        Jol jol = new Jol();
        Po po = new Po();
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(6, 5), JOL,
                new Position(4, 3), PO));

        Player player = new Player(janggiPan);

        //when
        player.movePiece(new Position(6, 5), new Position(6, 6));

        //then
        assertThat(player.getPieces().getPieces().containsKey(new Position(6, 6))).isTrue();
    }

    @Test
    @DisplayName("포 존재 확인 테스트")
    void isPoAtTest() {
        //given
        //given
        Jol jol = new Jol();
        Po po = new Po();
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(6, 5), JOL,
                new Position(4, 3), PO));

        Player player = new Player(janggiPan);

        //when-then
        assertThat(player.isPoAt(new Position(4, 3))).isTrue();
    }

    @Test
    @DisplayName("경로에 포 존재 확인 테스트")
    void isExistPoInRoute() {
        //given
        Jol jol = new Jol();
        Po po = new Po();
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(6, 5), JOL,
                new Position(4, 3), PO));

        Player player = new Player(janggiPan);

        Positions route1 = new Positions(
                List.of(new Position(4, 3))
        );

        Positions route2 = new Positions(
                List.of(new Position(0, 0))
        );

        //when-then
        assertThat(player.isExistPoInRoute(route1)).isTrue();
        assertThat(player.isExistPoInRoute(route2)).isFalse();
    }

    @Test
    @DisplayName("기물 삭제 테스트")
    void removePieceTest() {
        //given
        Jol jol = new Jol();
        Po po = new Po();
        JanggiPan janggiPan = new JanggiPan(Map.of(new Position(5, 5), JOL, new Position(6, 5), JOL,
                new Position(4, 3), PO));

        Player player = new Player(janggiPan);

        //when
        player.removePiece(new Position(5, 5));

        //then
        assertThat(player.getPieces().getPieces().containsKey(new Position(5, 5))).isFalse();
    }

}
