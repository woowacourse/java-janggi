package object;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RouteTest {
    @Test
    void 루트는_경로를_가진다() {
        // given
        List<Coordinate> coordinates = List.of(
                new Coordinate(0, 0),
                new Coordinate(1, 1)
        );

        // when
        Route route = new Route(coordinates);

        // then
        Assertions.assertThatIterable(route.getPositions()).containsExactlyElementsOf(coordinates);
    }
}
