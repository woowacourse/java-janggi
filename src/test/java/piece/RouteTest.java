package piece;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RouteTest {
    @Test
    void 루트는_경로를_가진다() {
        // given
        List<Position> positions = List.of(
                new Position(0, 0),
                new Position(1, 1)
        );

        // when
        Route route = new Route(positions);

        // then
        Assertions.assertThatIterable(route.getPositions()).containsExactlyElementsOf(positions);
    }
}
