package routes;

import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.E1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Palace;
import piece.Piece;
import route.Routes;

public class RoutesTest {

    @Test
    @DisplayName("궁은 상하좌우로 이동할 수 있다.")
    void routesOfPalaceTest(){
        // given
        Piece palace = new Palace(E1);

        // when
        Routes routes = palace.routes();

        // then
        assertThat(routes.routes().size()).isEqualTo(4);
    }
}
