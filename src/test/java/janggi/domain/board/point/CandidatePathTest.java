package janggi.domain.board.point;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.path.CandidatePath;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CandidatePathTest {

    public static Stream<Arguments> takeUntil() {
        return Stream.of(
                Arguments.of(
                        new CandidatePath(List.of(
                                Point.of(0, 1),
                                Point.of(0, 2),
                                Point.of(0, 3),
                                Point.of(0, 4),
                                Point.of(0, 5))),
                        Point.of(0, 4),
                        new CandidatePath(List.of(
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
    void takeUntil(CandidatePath candidatePath, Point point, CandidatePath expected) {
        assertThat(candidatePath.takeUntil(point)).isEqualTo(expected);
    }
}
