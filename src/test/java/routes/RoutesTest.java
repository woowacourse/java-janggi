package routes;

import static org.assertj.core.api.Assertions.assertThat;
import static piece.Team.HAN;
import static position.PositionFixtures.E1;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.normalPiece.Palace;
import piece.Piece;
import position.Board;
import route.Routes;

public class RoutesTest {

    @Test
    @DisplayName("궁은 상하좌우 4가지의 이동 범위를 가진다.")
    void routesOfPalaceTest() {
        assertThat(Routes.ofPalace().routes().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("사는 상하좌우 4가지의 이동 범위를 가진다.")
    void routesOfSoldierTest() {
        assertThat(Routes.ofSoldier().routes().size()).isEqualTo(4);
    }

    /*
    0 ＿ * ＿
    1 * 궁 *
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));
        Routes routes = Routes.ofPalace();

        // when
        Routes palaceRoutes = routes.possibleRoutes(E1, board);

        // then
        assertThat(palaceRoutes.routes().size()).isEqualTo(4);
    }
}
