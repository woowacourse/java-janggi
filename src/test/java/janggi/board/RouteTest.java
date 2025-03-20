package janggi.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RouteTest {

    @Test
    @DisplayName("경로 추가 테스트")
    void addRoute() {
        Route route = new Route(new Position(1, 1));
        route.addRoute(new Position(2, 2));

        assertAll(
                () -> assertThat(route.getPositions().getFirst()).isEqualTo(new Position(1, 1)),
                () -> assertThat(route.getPositions().getLast()).isEqualTo(new Position(2, 2))
        );
    }

    @Test
    @DisplayName("중간 경로 반환 테스트")
    void getIntermediatePositions() {
        Route route = new Route();
        route.addRoute(new Position(1, 1));
        route.addRoute(new Position(2, 2));
        route.addRoute(new Position(3, 3));
        route.addRoute(new Position(4, 4));

        List<Position> intermediatePositions = route.getIntermediatePositions();
        assertAll(
                () -> assertThat(intermediatePositions.getLast()).isEqualTo(new Position(3, 3)),
                () -> assertThat(intermediatePositions.size()).isEqualTo(3)
        );
    }
}