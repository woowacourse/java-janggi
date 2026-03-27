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
                                new Point(0, 1),
                                new Point(0, 2),
                                new Point(0, 3),
                                new Point(0, 4),
                                new Point(0, 5))),
                        new Point(0, 4),
                        new Path(List.of(
                                new Point(0, 1),
                                new Point(0, 2),
                                new Point(0, 3),
                                new Point(0, 4)))
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("cutUntil(): point까지 잘라서 Path를 리턴한다.")
    void cutUntil(Path path, Point point, Path expected) {
        assertThat(path.cutUntil(point)).isEqualTo(expected);
    }
}
