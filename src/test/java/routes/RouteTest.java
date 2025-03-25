package routes;

import static org.assertj.core.api.Assertions.assertThat;
import static janggi.piece.Team.HAN;
import static position.PositionFixtures.E1;
import static janggi.route.Direction.NORTH;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import janggi.piece.normalPiece.Palace;
import janggi.piece.Piece;
import janggi.position.Board;
import janggi.route.Route;

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
        Route route = new Route(List.of(NORTH));

        // when - then
        assertThat(route.isPossibleRoute(E1, board)).isTrue();
    }

}
