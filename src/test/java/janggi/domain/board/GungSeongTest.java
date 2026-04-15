package janggi.domain.board;

import janggi.domain.Location;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungSeongTest {

    private static final GungSeong GUNG_SEONG = GungSeong.of(10, 9);

    @Nested
    class ContainsTest {

        @ParameterizedTest
        @DisplayName("해당 위치가 궁성 영역에 속하면 True를 반환한다.")
        @MethodSource("provideLocationsInGungSeongArea")
        void shouldReturnTrueWhenLocationIsAtGungSeong(Location location) {
            // when & then
            Assertions.assertThat(GUNG_SEONG.contains(location)).isTrue();
        }

        static List<Location> provideLocationsInGungSeongArea() {
            return List.of(
                    // 한 진영의 궁성 영역
                    new Location(0, 3),
                    new Location(0, 4),
                    new Location(0, 5),
                    new Location(1, 3),
                    new Location(1, 4),
                    new Location(1, 5),
                    new Location(2, 3),
                    new Location(2, 4),
                    new Location(2, 5),

                    // 초 진영의 궁성영역
                    new Location(9, 3),
                    new Location(9, 4),
                    new Location(9, 5),
                    new Location(8, 3),
                    new Location(8, 4),
                    new Location(8, 5),
                    new Location(7, 3),
                    new Location(7, 4),
                    new Location(7, 5)
            );
        }

        @ParameterizedTest
        @DisplayName("해당 위치가 궁성 영역에 속하지 않으면 False를 반환한다.")
        @MethodSource("provideLocationsOutOfGungSeongArea")
        void shouldReturnFalseWhenLocationIsNotAtGungSeong(Location location) {
            // when & then
            Assertions.assertThat(GUNG_SEONG.contains(location)).isFalse();
        }

        static List<Location> provideLocationsOutOfGungSeongArea() {
            return List.of(
                    new Location(0, 0),
                    new Location(0, 1),
                    new Location(0, 2),
                    new Location(0, 6),
                    new Location(1, 6),
                    new Location(2, 6),
                    new Location(3, 3),
                    new Location(3, 4),
                    new Location(3, 5),

                    new Location(9, 6),
                    new Location(9, 7),
                    new Location(9, 8),
                    new Location(9, 2),
                    new Location(8, 2),
                    new Location(7, 2),
                    new Location(6, 3),
                    new Location(6, 4),
                    new Location(6, 5)
            );
        }
    }

    @Nested
    class FindValidDiagonalPathTest {

        @ParameterizedTest
        @DisplayName("해당 위치가 궁성 영역에서 궁성의 대각선 경로로 이동 가능하다면 이동 경로를 반환한다.")
        @MethodSource("provideMovableLocationsInGungSeongArea")
        void shouldReturnTrueWhenLocationIsAtGungSeong(Location from, Location to, List<Location> validPath) {
            // when & then
            Assertions.assertThat(GUNG_SEONG.findValidDiagonalPath(from, to)).hasValue(validPath);
        }

        static Stream<Arguments> provideMovableLocationsInGungSeongArea() {
            return Stream.of(
                    // 한 진영
                    Arguments.of(
                            new Location(0, 3),
                            new Location(1, 4),
                            List.of(new Location(1, 4))
                    ),
                    Arguments.of(
                            new Location(0, 3),
                            new Location(2, 5),
                            List.of(new Location(1, 4), new Location(2, 5))
                    ),

                    // 초 진영
                    Arguments.of(
                            new Location(9, 5),
                            new Location(8, 4),
                            List.of(new Location(8, 4))
                    ),
                    Arguments.of(
                            new Location(9, 5),
                            new Location(7, 3),
                            List.of(new Location(8, 4), new Location(7, 3))
                    )
            );
        }

        @ParameterizedTest
        @DisplayName("해당 위치가 궁성 영역에서 궁성의 대각선 경로로 이동 불가능하다면 빈 결과를 반환한다.")
        @MethodSource("provideImmovableLocationsInGungSeongArea")
        void shouldReturnFalseWhenLocationIsNotAtGungSeong(Location from, Location to) {
            // when & then
            Assertions.assertThat(GUNG_SEONG.findValidDiagonalPath(from, to)).isEmpty();
        }

        static Stream<Arguments> provideImmovableLocationsInGungSeongArea() {
            return Stream.of(
                    // 한 진영
                    Arguments.of(
                            new Location(0, 4),
                            new Location(1, 3)
                    ),
                    Arguments.of(
                            new Location(0, 4),
                            new Location(1, 5)
                    ),

                    // 초 진영
                    Arguments.of(
                            new Location(7, 4),
                            new Location(8, 3)
                    ),
                    Arguments.of(
                            new Location(7, 4),
                            new Location(8, 5)
                    )
            );
        }
    }
}
