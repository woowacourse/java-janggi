package janggi.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    @Test
    void 포지션이_도착지면_true를_반환한다() {
        Path path = new Path(List.of(JanggiPosition.of(1, 2), JanggiPosition.of(2, 2)),
                JanggiPosition.of(3, 3));

        boolean hasDestination = path.hasDestination(JanggiPosition.of(3, 3));

        assertThat(hasDestination).isEqualTo(true);
    }

    @Test
    void 포지션이_도착지가_아니면_false를_반환한다() {
        Path path = new Path(List.of(JanggiPosition.of(1, 2), JanggiPosition.of(2, 2)),
                JanggiPosition.of(3, 3));

        boolean hasDestination = path.hasDestination(JanggiPosition.of(2, 3));

        assertThat(hasDestination).isEqualTo(false);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2",
            "2, 2"
    })
    void 포지션이_경유지면_true_반환한다(int row, int column) {
        Path path = new Path(List.of(JanggiPosition.of(1, 2), JanggiPosition.of(2, 2)),
                JanggiPosition.of(3, 3));

        boolean hasRoute = path.hasRoute(JanggiPosition.of(row, column));

        assertThat(hasRoute).isEqualTo(true);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "3, 5"
    })
    void 포지션이_경유지가_아니면_false_반환한다(int row, int column) {
        Path path = new Path(List.of(JanggiPosition.of(1, 2), JanggiPosition.of(2, 2)),
                JanggiPosition.of(3, 3));

        boolean hasRoute = path.hasRoute(JanggiPosition.of(row, column));

        assertThat(hasRoute).isEqualTo(false);
    }
}
