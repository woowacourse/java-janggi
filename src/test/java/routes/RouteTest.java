package routes;

import static janggi.piece.Team.HAN;
import static janggi.route.Direction.EAST;
import static janggi.route.Direction.NORTH;
import static janggi.route.Direction.SOUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.D0;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E4;
import static position.PositionFixtures.E5;
import static position.PositionFixtures.F1;

import janggi.piece.Piece;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.normalPiece.Palace;
import janggi.position.Board;
import janggi.route.Route;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RouteTest {

    /*
    0 ＿ * ＿
    1 ＿ 궁 ＿
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("해당 경로로 이동하는 것이 가능한지 검사할 수 있다.")
    void isPossibleRouteTest_1() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));
        Route route = new Route(List.of(SOUTH));

        // when - then
        assertThat(route.isPossibleRoute(E1, board)).isTrue();
    }

    /*
    6 ＿ 궁 ＿
    7 ＿ * ＿
    8 ＿ * ＿
    9 ＿ * ＿
      d e f
    */
    @Test
    @DisplayName("해당 경로로 이동하는 것이 가능한지 검사할 수 있다.")
    void isPossibleRouteTest_2() {
        // given
        Piece palace = new Palace(HAN, E5);
        Board board = new Board(Set.of(palace));
        Route route = new Route(List.of(NORTH, NORTH, NORTH, NORTH, NORTH, NORTH));

        // when - then
        assertThat(route.isPossibleRoute(E4, board)).isFalse();
    }

    /*
    0 ＿ ＿ ＿
    1 포 궁 *
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("기물을 뛰어넘는 것이 가능한지 검사할 수 있다.")
    void canJumpTest_1() {
        // given
        Piece cannon = new Cannon(HAN, D1);
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(cannon, palace));
        Route route = new Route(List.of(EAST));

        // when - then
        assertThat(route.canJump(D1, board)).isTrue();
    }

    /*
    0 ＿ ＿ ＿ ＿
    1 포 ＿ 궁 *
    2 ＿ ＿ ＿ ＿
    3 d e  f g
    */
    @Test
    @DisplayName("기물을 뛰어넘는 것이 가능한지 검사할 수 있다.")
    void canJumpTest_2() {
        // given
        Piece cannon = new Cannon(HAN, D1);
        Piece palace = new Palace(HAN, F1);
        Board board = new Board(Set.of(cannon, palace));
        Route route = new Route(List.of(EAST, EAST, EAST, EAST));

        // when - then
        assertThat(route.canJump(D1, board)).isTrue();
    }

    /*
    0 궁 ＿ ＿
    1 포 ＿ ＿
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("기물을 뛰어넘는 것이 가능한지 검사할 수 있다.")
    void canJumpTest_3() {
        // given
        Piece cannon = new Cannon(HAN, D1);
        Piece palace = new Palace(HAN, F1);
        Board board = new Board(Set.of(cannon, palace));
        Route route = new Route(List.of(SOUTH, SOUTH));

        // when - then
        assertThat(route.canJump(D1, board)).isFalse();
    }

    /*
    0 포 ＿ ＿
    1 ＿ ＿ ＿
    2 ＿ ＿ ＿
    3 d e f
    */
    @Test
    @DisplayName("기물을 뛰어넘는 것이 가능한지 검사할 수 있다.")
    void canJumpTest_4() {
        // given
        Piece cannon = new Cannon(HAN, D0);
        Board board = new Board(Set.of(cannon));
        Route route = new Route(List.of(SOUTH));

        // when - then
        assertThat(route.canJump(D0, board)).isFalse();
    }

}
