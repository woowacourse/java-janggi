package janggi.domain;

import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    @Test
    void 포지션이_도착지면_true를_반환한다() {
        List<Position> wayPoints = List.of(Position.of(1, 2), Position.of(2, 2));
        Path path = Path.of(wayPoints, Position.of(3, 3));

        boolean isDestination = path.isDestination(Position.of(3, 3));

        assertThat(isDestination).isEqualTo(true);
    }

    @Test
    void 포지션이_도착지가_아니면_false를_반환한다() {
        List<Position> wayPoints = List.of(Position.of(1, 2), Position.of(2, 2));
        Path path = Path.of(wayPoints, Position.of(3, 3));

        boolean isDestination = path.isDestination(Position.of(2, 3));

        assertThat(isDestination).isEqualTo(false);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2",
            "2, 2"
    })
    void 포지션이_경유지면_true_반환한다(int row, int column) {
        List<Position> wayPoints = List.of(Position.of(1, 2), Position.of(2, 2));
        Path path = Path.of(wayPoints, Position.of(3, 3));

        boolean isOnWayPoints = path.isOnWayPoints(Position.of(row, column));

        assertThat(isOnWayPoints).isEqualTo(true);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "3, 5"
    })
    void 포지션이_경유지가_아니면_false_반환한다(int row, int column) {
        List<Position> wayPoints = List.of(Position.of(1, 2), Position.of(2, 2));
        Path path = Path.of(wayPoints, Position.of(3, 3));

        boolean isOnWayPoints = path.isOnWayPoints(Position.of(row, column));

        assertThat(isOnWayPoints).isEqualTo(false);
    }
}
