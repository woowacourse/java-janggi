package janggi.domain.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

    @ParameterizedTest
    @MethodSource("궁성_여부_판단_테스트케이스")
    @DisplayName("해당 Position이 궁성인지 여부를 반환한다.")
    public void isPalace_success(Position position, boolean result) throws Exception {
        // when
        boolean palace = Palace.isPalace(position);

        // then
        assertThat(palace).isEqualTo(result);
    }

    private static Stream<Arguments> 궁성_여부_판단_테스트케이스() {
        return Stream.of(
                Arguments.of(Position.from(1, 4), true),
                Arguments.of(Position.from(1, 6), true),
                Arguments.of(Position.from(2, 4), true),
                Arguments.of(Position.from(2, 6), true),
                Arguments.of(Position.from(3, 4), true),
                Arguments.of(Position.from(10, 6), true),
                Arguments.of(Position.from(10, 4), true),
                Arguments.of(Position.from(9, 6), true),
                Arguments.of(Position.from(9, 4), true),
                Arguments.of(Position.from(8, 6), true),
                Arguments.of(Position.from(1, 3), false),
                Arguments.of(Position.from(1, 7), false),
                Arguments.of(Position.from(3, 3), false),
                Arguments.of(Position.from(10, 7), false),
                Arguments.of(Position.from(10, 3), false),
                Arguments.of(Position.from(8, 7), false)
        );
    }

}
