package domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RouteTest {

    @Test
    @DisplayName("경로의 도착지를 반환한다.")
    void test1() {
        // given
        List<Position> positions = List.of(
                Position.of(0, 0),
                Position.of(0, 1),
                Position.of(0, 2),
                Position.of(0, 3));
        Route route = Route.of(positions);

        // when
        Position destination = route.searchDestination(Position.of(0, 4));

        // then
        assertThat(destination).isEqualTo(Position.of(0, 0));
    }

    @Test
    @DisplayName("경로의 도착지를 제외한 위치들을 반환한다.")
    void test2() {
        // given
        List<Position> positions = List.of(
                Position.of(0, 0),
                Position.of(0, 1),
                Position.of(0, 2),
                Position.of(0, 3));
        Route route = Route.of(positions);

        // when
        List<Position> exceptDestination = route.getPositionsExceptDestination(Position.of(0, 4));

        // then
        assertThat(exceptDestination).hasSize(3)
                .contains(
                        Position.of(0, 1),
                        Position.of(0, 2),
                        Position.of(0, 3)
                );
    }
}
