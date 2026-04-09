package janggi.domain.board;

import janggi.domain.Location;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class GungSeongTest {

    private static final GungSeong GUNG_SEONG = GungSeong.of(10, 9);

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
