package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void test_hasEqualTeam() {
        //given
        Piece cannon = new Cannon(Team.CHO);

        //when&then
        assertThat(cannon.hasEqualTeam(Team.CHO)).isTrue();
        assertThat(cannon.hasEqualTeam(Team.HAN)).isFalse();
    }

    @Test
    @DisplayName("도착할 수 있는지 확인한다.")
    void test_IsAbleToArrive() {
        // given
        Cannon cannon = new Cannon(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(0, 9);

        // when
        boolean actual = cannon.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("도착할 수 없는지 확인한다.")
    void test_IsNotAbleToArrive() {
        // given
        Cannon cannon = new Cannon(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(3, 3);

        // when
        boolean actual = cannon.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isFalse();
    }


    @Test
    @DisplayName("도착점까지의 경로를 모두 반환한다.")
    void test_getRoutePoints() {
        // given
        Cannon cannon = new Cannon(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(0, 3);

        // when
        List<Point> routePoints = cannon.getRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );
    }

    @Test
    @DisplayName("경로 상 기물이 하나 있어야 이동 할 수 있다.")
    void test_isMovableWhenPieceOnRoute() {
        //given
        Cannon cannon = new Cannon(Team.CHO);
        Chariot chariot = new Chariot(Team.CHO);
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(chariot, null, null));

        //when&then
        assertThat(cannon.isMovable(piecesOnRoute)).isTrue();
    }

    @Test
    @DisplayName("경로 상 포가 있으면 이동할 수 없다.")
    void test_isMovableWhenCannonOnRoute() {
        //given
        Cannon cannon = new Cannon(Team.CHO);
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(cannon, null, null));

        //when&then
        assertThat(cannon.isMovable(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 없으면 이동할 수 없다.")
    void test_isMovable() {
        //given
        Cannon Cannon = new Cannon(Team.CHO);
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(null, null, null));

        //when&then
        assertThat(Cannon.isMovable(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("경로 상 기물이 둘이면 이동할 수 없다.")
    void test_isMovableWhenPiecesOnRoute() {
        //given
        Cannon Cannon = new Cannon(Team.CHO);
        Chariot chariot = new Chariot(Team.CHO);

        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(chariot, chariot, null));

        //when&then
        assertThat(Cannon.isMovable(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("도착점에 아군 기물이 있으면 이동할 수 없다.")
    void test_isMovableWhenPieceIsInOtherTeam() {
        //given
        Cannon cannon = new Cannon(Team.HAN);
        Chariot chariot = new Chariot(Team.HAN);
        PiecesOnRoute piecesOnRoute = new PiecesOnRoute(Arrays.asList(chariot, null, chariot));

        //when&then
        assertThat(cannon.isMovable(piecesOnRoute)).isFalse();
    }

    @Test
    @DisplayName("포는 팀에 따라 다르게 이름을 반환한다.")
    void test_toString() {
        //given
        Piece pieceForCho = new Cannon(Team.CHO);
        Piece pieceForHan = new Cannon(Team.HAN);

        //when&then
        assertThat(pieceForCho.getName()).isEqualTo("포");
        assertThat(pieceForHan.getName()).isEqualTo("包");
    }
}
