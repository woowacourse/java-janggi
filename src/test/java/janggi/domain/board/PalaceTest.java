package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.piece.Camp;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {

    private static Stream<Arguments> friendlyPalacePositions() {
        return Stream.of(
                Arguments.of(Camp.CHO, new Position(0, 3)),
                Arguments.of(Camp.CHO, new Position(1, 4)),
                Arguments.of(Camp.HAN, new Position(8, 4)),
                Arguments.of(Camp.HAN, new Position(9, 5))
        );
    }

    @ParameterizedTest
    @MethodSource("friendlyPalacePositions")
    void 아군_궁성_내부를_판별한다(Camp camp, Position position) {
        assertDoesNotThrow(() ->
                Palace.validateFriendlyPalace(camp, position)
        );
    }

    private static Stream<Arguments> palacePositions() {
        return Stream.of(
                Arguments.of(new Position(0, 3), true),
                Arguments.of(new Position(1, 4), true),
                Arguments.of(new Position(2, 5), true),
                Arguments.of(new Position(7, 3), true),
                Arguments.of(new Position(8, 4), true),
                Arguments.of(new Position(9, 5), true),
                Arguments.of(new Position(0, 2), false),
                Arguments.of(new Position(3, 3), false),
                Arguments.of(new Position(6, 4), false),
                Arguments.of(new Position(9, 6), false)
        );
    }

    @ParameterizedTest
    @MethodSource("palacePositions")
    void 궁성을_판별한다(Position position, boolean expected) {
        assertThat(Palace.isPalace(position)).isEqualTo(expected);
    }

    private static Stream<Arguments> palaceCenterPositions() {
        return Stream.of(
                Arguments.of(new Position(1, 4), true),
                Arguments.of(new Position(8, 4), true),
                Arguments.of(new Position(0, 4), false),
                Arguments.of(new Position(1, 3), false),
                Arguments.of(new Position(9, 4), false),
                Arguments.of(new Position(8, 5), false)
        );
    }

    @ParameterizedTest
    @MethodSource("palaceCenterPositions")
    void 궁성_중앙을_판별한다(Position position, boolean expected) {
        assertThat(Palace.isPalaceCenter(position)).isEqualTo(expected);
    }

    private static Stream<Arguments> invalidFriendlyPalacePositions() {
        return Stream.of(
                Arguments.of(Camp.CHO, new Position(8, 4)),
                Arguments.of(Camp.CHO, new Position(3, 3)),
                Arguments.of(Camp.HAN, new Position(1, 4)),
                Arguments.of(Camp.HAN, new Position(6, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidFriendlyPalacePositions")
    void 상대_궁성이나_궁성_밖은_예외가_발생한다(Camp camp, Position position) {
        assertThatThrownBy(() -> Palace.validateFriendlyPalace(camp, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 아군 궁성 영역 밖으로 이동할 수 없습니다.");
    }
}
