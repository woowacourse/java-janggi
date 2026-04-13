package janggi.domain.piece.path;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.point.Point;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CandidatePathTest {

    public static Stream<Arguments> subPath() {
        return Stream.of(
                Arguments.of(
                        new CandidatePath(List.of(
                                new Point(0, 1),
                                new Point(0, 2),
                                new Point(0, 3),
                                new Point(0, 4),
                                new Point(0, 5))),
                        new Point(0, 4),
                        new CandidatePath(List.of(
                                new Point(0, 1),
                                new Point(0, 2),
                                new Point(0, 3),
                                new Point(0, 4)))
                )
        );
    }

    public static Stream<Arguments> isForward() {
        return Stream.of(
                Arguments.of(
                        new CandidatePath(new Point(1, 4), List.of(
                                new Point(2, 4),
                                new Point(2, 5),
                                new Point(2, 3))),
                        Direction.NORTH,
                        true
                ),
                Arguments.of(
                        new CandidatePath(new Point(1, 4), List.of(
                                new Point(1, 3),
                                new Point(1, 5),
                                new Point(0, 3),
                                new Point(0, 4),
                                new Point(0, 5)
                        )),
                        Direction.NORTH,
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("point까지 잘라서 Path를 리턴한다.")
    void subPath(CandidatePath candidatePath, Point point, CandidatePath expected) {
        assertThat(candidatePath.takeUntil(point)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Direction을 향하면 true를 리턴한다.")
    @MethodSource
    void isForward(CandidatePath candidatePath, Direction direction, boolean expected) {
        assertThat(candidatePath.isForward(direction)).isEqualTo(expected);
    }
}
