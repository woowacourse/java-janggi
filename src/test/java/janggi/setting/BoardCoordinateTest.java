package janggi.setting;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.value.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardCoordinateTest {

    @DisplayName("정해진 보드의 좌표 내에 있는 위치값인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canCheckRange(Position position, boolean ExpectedIsInGungsung) {
        boolean isInGungSung = GungSung.isInAnyGungSung(position);
        assertThat(isInGungSung).isEqualTo(ExpectedIsInGungsung);
    }

    static Stream<Arguments> canCheckRange() {
        return Stream.of(
                Arguments.of(new Position(0, 0), false),
                Arguments.of(new Position(9, 0), false),
                Arguments.of(new Position(0, 9), false),
                Arguments.of(new Position(9, 9), false),
                Arguments.of(new Position(0, -1), false),
                Arguments.of(new Position(-1, 0), false),
                Arguments.of(new Position(0, 10), false),
                Arguments.of(new Position(10, 0), false)
        );
    }
}