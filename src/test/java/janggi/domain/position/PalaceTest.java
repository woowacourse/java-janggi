package janggi.domain.position;

import janggi.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static janggi.domain.position.Direction.*;
import static janggi.domain.position.Palace.INVALID_PALACE_POSITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PalaceTest {

    @ParameterizedTest
    @MethodSource("isPalace_success_테스트케이스")
    @DisplayName("해당 Position이 궁성인지 여부를 반환한다.")
    public void isPalace_success(Position position, boolean result) throws Exception {
        // when
        boolean palace = Palace.isPalace(position);

        // then
        assertThat(palace).isEqualTo(result);
    }

    private static Stream<Arguments> isPalace_success_테스트케이스() {
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

    @Test
    @DisplayName("해당 위치가 궁성이 아니면 예외가 발생한다.")
    public void getMovableDirectionsAtPalace_fail() throws Exception {
        // given
        int notPalaceRow = 1;
        int notPalaceColumn = 1;
        Position notPalacePosition = Position.from(notPalaceRow, notPalaceColumn);

        // when then
        assertThatThrownBy(() -> Palace.getMovableDirectionsAtPalace(notPalacePosition))
                .isInstanceOf(DomainException.class)
                .hasMessage(String.format(INVALID_PALACE_POSITION, notPalaceRow, notPalaceColumn));
    }

    @ParameterizedTest
    @MethodSource("getMovableDirectionsAtPalace_success_테스트케이스")
    @DisplayName("특정 궁성 영역에서 갈 수 있는 방향 목록을 반환한다.")
    public void getMovableDirectionsAtPalace_success(Position position, List<Direction> expectedResult) {
        // when
        List<Direction> directions = Palace.getMovableDirectionsAtPalace(position);

        // then
        assertThat(directions).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> getMovableDirectionsAtPalace_success_테스트케이스() {
        return Stream.of(
                Arguments.of(Position.from(1, 6), List.of(WEST, SOUTHWEST, SOUTH)),
                Arguments.of(Position.from(8, 6), List.of(WEST, SOUTHWEST, SOUTH))
        );
    }


}
