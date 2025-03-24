package domain.position;

import domain.MovingPattern;
import janggiexception.OutOfBoardException;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FileTest {

    @ParameterizedTest
    @MethodSource("provideFileValues")
    void 열을_기준으로_이동할_수_있다(File originalFile, MovingPattern movePattern, File expectedFile) {
        // when & then
        Assertions.assertThat(originalFile.moveFile(movePattern))
                .isEqualTo(expectedFile);
    }

    private static Stream<Arguments> provideFileValues() {
        return Stream.of(
                Arguments.of(File.FIVE, MovingPattern.MOVE_LEFT, File.FOUR),
                Arguments.of(File.NINE, MovingPattern.MOVE_LEFT, File.EIGHT),
                Arguments.of(File.EIGHT, MovingPattern.MOVE_RIGHT, File.NINE),
                Arguments.of(File.FIVE, MovingPattern.MOVE_DIAGONAL_UP_LEFT, File.FOUR)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFileValuesAndMovable")
    void 움직일_수_있는지_확인할_수_있다(File originalFile, MovingPattern movePattern, boolean expected) {
        // when & then
        Assertions.assertThat(originalFile.canMoveFile(movePattern))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideFileValuesAndMovable() {
        return Stream.of(
                Arguments.of(File.FIVE, MovingPattern.MOVE_LEFT, true),
                Arguments.of(File.NINE, MovingPattern.MOVE_RIGHT, false),
                Arguments.of(File.ONE, MovingPattern.MOVE_LEFT, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFilesAndNotMovable")
    void 더이상_움직일_수_없는_경우_예외를_발생시킨다(File file, MovingPattern pattern) {
        // when & then
        Assertions.assertThatThrownBy(() -> file.moveFile(pattern))
                .isInstanceOf(OutOfBoardException.class);
    }

    private static Stream<Arguments> provideFilesAndNotMovable() {
        return Stream.of(
                Arguments.of(File.ONE, MovingPattern.MOVE_LEFT),
                Arguments.of(File.NINE, MovingPattern.MOVE_RIGHT)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFilesAndComparingResults")
    void 열끼리_비교할_수_있다(File me, File other, boolean expected) {
        // when & expected
        Assertions.assertThat(me.isBiggerThan(other))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideFilesAndComparingResults() {
        return Stream.of(
                Arguments.of(File.FIVE, File.SIX, false),
                Arguments.of(File.NINE, File.ONE, true),
                Arguments.of(File.ONE, File.THREE, false),
                Arguments.of(File.ONE, File.ONE, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideFilesAndGap")
    void 열끼리의_차이를_알_수_있다(File me, File other, int expected) {
        // when & expected
        Assertions.assertThat(me.getGapBetween(other))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideFilesAndGap() {
        return Stream.of(
                Arguments.of(File.FIVE, File.SIX, 1),
                Arguments.of(File.NINE, File.ONE, 8),
                Arguments.of(File.ONE, File.THREE, 2),
                Arguments.of(File.ONE, File.ONE, 0)
        );
    }
}
