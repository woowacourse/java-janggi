package domain.direction;

import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.position.NormalPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiDirectionsTest {
    @DisplayName("이동 범위가 보드판 영역을 벗어나는 경우 적용할 수 없음을 알 수 있다.")
    @ParameterizedTest
    @MethodSource
    void outOfBound(JanggiPosition startPosition, JanggiDirections directions) {
        // given

        // when
        final boolean canApply = directions.canApplyFrom(startPosition);

        // then
        assertThat(canApply).isFalse();
    }

    private static Stream<Arguments> outOfBound() {
        return Stream.of(
                Arguments.of(
                        JanggiPositionFactory.of(1, 1),
                        new JanggiDirections(List.of(
                                Direction.LEFT, Direction.LEFT
                        ))
                ),
                Arguments.of(
                        JanggiPositionFactory.of(1, 1),
                        new JanggiDirections(List.of(
                                Direction.UP, Direction.UP
                        ))
                ),
                Arguments.of(
                        JanggiPositionFactory.of(1, 1),
                        new JanggiDirections(List.of(
                                Direction.LEFT_UP, Direction.LEFT_UP
                        ))
                )
        );
    }

    @DisplayName("시작 지점으로부터 주어진 방향으로 이동했을 때, 경로를 반환할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void correctPath(JanggiPosition startPosition, JanggiDirections directions, List<JanggiPosition> expected) {
        // given
        // when
        Path path = directions.getPathFrom(startPosition);

        // then
        List<JanggiPosition> positions = path.getPath();
        assertThat(positions).containsExactlyElementsOf(expected);
    }

    private static Stream<Arguments> correctPath() {
        return Stream.of(
                Arguments.of(
                        JanggiPositionFactory.of(5, 4),
                        new JanggiDirections(List.of(
                                Direction.UP, Direction.LEFT_UP
                        )),
                        List.of(
                                new NormalPosition(4, 4),
                                new NormalPosition(3, 3)
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(5, 4),
                        new JanggiDirections(List.of(
                                Direction.UP, Direction.UP, Direction.RIGHT_UP
                        )),
                        List.of(
                                new NormalPosition(4, 4),
                                new NormalPosition(3, 4),
                                new NormalPosition(2, 5)
                        )
                ),
                Arguments.of(
                        JanggiPositionFactory.of(5, 4),
                        new JanggiDirections(List.of(
                                Direction.DOWN, Direction.DOWN, Direction.LEFT_DOWN
                        )),
                        List.of(
                                new NormalPosition(6, 4),
                                new NormalPosition(7, 4),
                                new NormalPosition(8, 3)
                        )
                )
        );
    }
}
