package janggi.rule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.value.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungSungRangeTest {

    @DisplayName("특정 궁성영역 내의 위치인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canCheckPositionInGungsung(Position position, boolean ExpectedIsInGungsung) {
        boolean isInGungSung = GungSungRange.isInAnyGungSung(position);
        assertThat(isInGungSung).isEqualTo(ExpectedIsInGungsung);
    }

    static Stream<Arguments> canCheckPositionInGungsung() {
        return Stream.of(
                Arguments.of(new Position(4, 1), true),
                Arguments.of(new Position(5, 1), true),
                Arguments.of(new Position(3, 1), true),
                Arguments.of(new Position(4, 0), true),
                Arguments.of(new Position(4, 2), true),
                Arguments.of(new Position(4, 8), true),
                Arguments.of(new Position(3, 8), true),
                Arguments.of(new Position(5, 8), true),
                Arguments.of(new Position(4, 7), true),
                Arguments.of(new Position(4, 9), true),
                Arguments.of(new Position(6, 5), false),
                Arguments.of(new Position(0, 9), false)
        );
    }
}