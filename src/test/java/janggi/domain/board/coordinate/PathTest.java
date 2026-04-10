package janggi.domain.board.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PathTest {

    public static Stream<Arguments> cutUntil() {
        return Stream.of(
                Arguments.of(
                        new Path(List.of(
                                Point.of(0, 1),
                                Point.of(0, 2),
                                Point.of(0, 3),
                                Point.of(0, 4),
                                Point.of(0, 5)), false),
                        Point.of(0, 4),
                        List.of(
                                Point.of(0, 1),
                                Point.of(0, 2),
                                Point.of(0, 3),
                                Point.of(0, 4))
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("cutUntil(): point까지 잘라서 Path를 리턴한다.")
    void cutUntil(Path path, Point point, List<Point> expected) {
        assertThat(path.cutUntil(point).getPath()).isEqualTo(expected);
    }
}
