package janggi.domain.board.point;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.path.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PathTest {

    public static Stream<Arguments> takeUntil() {
        return Stream.of(
                Arguments.of(
                        new Path(List.of(
                                Point.of(0, 1),
                                Point.of(0, 2),
                                Point.of(0, 3),
                                Point.of(0, 4),
                                Point.of(0, 5))),
                        Point.of(0, 4),
                        new Path(List.of(
                                Point.of(0, 1),
                                Point.of(0, 2),
                                Point.of(0, 3),
                                Point.of(0, 4)))
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("cutUntil(): point까지 잘라서 Path를 리턴한다.")
    void takeUntil(Path path, Point point, Path expected) {
        assertThat(path.takeUntil(point)).isEqualTo(expected);
    }
}
