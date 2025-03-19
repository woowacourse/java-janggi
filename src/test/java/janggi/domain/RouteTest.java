package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RouteTest {

    @DisplayName("경로에 해당 위치가 포함되지 않으면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "1, 1, 2, 2, 3, 3, 4, 4, true", "1, 1, 2, 2, 3, 3, 1, 1, false"
    })
    void hasNotPositionTest(int firstX, int firstY, int secondX, int secondY, int destinationX, int destinationY,
                            int targetX, int targetY, boolean expected) {

        // given
        Route route = new Route(List.of(new Position(firstX, firstY), new Position(secondX, secondY)),
                new Position(destinationX, destinationY));

        // when & then
        assertThat(route.hasNotPosition(new Position(targetX, targetY))).isEqualTo(expected);
    }


}
