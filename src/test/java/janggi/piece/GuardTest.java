package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class GuardTest {

    @Nested
    @DisplayName("Red 위치의 Guard 테스트")
    class RedGuardTest {
        @ParameterizedTest
        @DisplayName("시작점과 끝점이 주어졌을 때, 사의 이동 경로를 반환한다.")
        @CsvSource(value = {
                "5, 1", // 상
                "5, 3", // 하
                "4, 2", // 좌
                "6, 2", // 우

                "4, 1", // 좌상
                "4, 3", // 좌하
                "6, 1", // 우상
                "6, 3"  // 우하
        })
        void should_return_path_by_start_and_end_position(int destX, int destY) {
            // given
            Guard guard = new Guard(Color.RED);
            Position start = new Position(5, 2);
            Position end = new Position(destX, destY);

            // when
            List<Position> path = guard.calculatePath(start, end);

            // then
            assertThat(path).isEmpty();
        }

        @ParameterizedTest
        @DisplayName("사가 왕궁을 벗어나면 예외가 발생한다.")
        @MethodSource("calculateDirectionExceptionArguments")
        void should_throw_exception_when_unfollow_guard_moving_rule(Position start, Position end) {
            // given
            Guard guard = new Guard(Color.RED);

            // when
            assertThatThrownBy(() -> guard.calculatePath(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> calculateDirectionExceptionArguments() {
            return Stream.of(
                    Arguments.of(new Position(4, 1), new Position(3, 1)), // 좌
                    Arguments.of(new Position(4, 2), new Position(3, 2)), // 좌
                    Arguments.of(new Position(4, 3), new Position(3, 3)), // 좌

                    Arguments.of(new Position(4, 3), new Position(4, 4)), // 하
                    Arguments.of(new Position(5, 3), new Position(5, 4)), // 하
                    Arguments.of(new Position(6, 3), new Position(6, 4)), // 하

                    Arguments.of(new Position(6, 3), new Position(7, 3)), // 우
                    Arguments.of(new Position(6, 2), new Position(7, 2)), // 우
                    Arguments.of(new Position(6, 1), new Position(7, 1))  // 우
            );
        }
    }

    @Nested
    @DisplayName("Blue 위치의 Guard 테스트")
    class BlueGuardTest {
        @ParameterizedTest
        @DisplayName("시작점과 끝점이 주어졌을 때, 사의 이동 경로를 반환한다.")
        @CsvSource(value = {
                "5, 8",  // 상
                "5, 10", // 하
                "4, 9",  // 좌
                "6, 9",  // 우

                "4, 8",  // 좌상
                "4, 10", // 좌하
                "6, 8",  // 우상
                "6, 10"  // 우하
        })
        void should_return_path_by_start_and_end_position(int destX, int destY) {
            // given
            Guard guard = new Guard(Color.BLUE);
            Position start = new Position(5, 9);
            Position end = new Position(destX, destY);

            // when
            List<Position> path = guard.calculatePath(start, end);

            // then
            assertThat(path).isEmpty();
        }

        @ParameterizedTest
        @DisplayName("사가 왕궁을 벗어나면 예외가 발생한다.")
        @MethodSource("calculateDirectionExceptionArguments")
        void should_throw_exception_when_unfollow_guard_moving_rule(Position start, Position end) {
            // given
            Guard guard = new Guard(Color.BLUE);

            // when
            assertThatThrownBy(() -> guard.calculatePath(start, end))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> calculateDirectionExceptionArguments() {
            return Stream.of(
                    Arguments.of(new Position(4, 10), new Position(3, 10)), // 좌
                    Arguments.of(new Position(4, 9), new Position(3, 9)),   // 좌
                    Arguments.of(new Position(4, 8), new Position(3, 8)),   // 좌

                    Arguments.of(new Position(4, 8), new Position(4, 7)),   // 상
                    Arguments.of(new Position(5, 8), new Position(5, 7)),   // 상
                    Arguments.of(new Position(6, 8), new Position(6, 7)),   // 상

                    Arguments.of(new Position(6, 8), new Position(7, 8)),   // 우
                    Arguments.of(new Position(6, 9), new Position(7, 9)),   // 우
                    Arguments.of(new Position(6, 10), new Position(7, 10))  // 우
            );
        }
    }
}
