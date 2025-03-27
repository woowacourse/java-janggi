package domain;

import static domain.Fixtures.FIVE_NINE;
import static domain.Fixtures.FOUR_EIGHT;
import static domain.Fixtures.FOUR_ZERO;
import static domain.Fixtures.ONE_NINE;
import static domain.Fixtures.ONE_THREE;
import static domain.Fixtures.ONE_ZERO;
import static domain.Fixtures.SIX_EIGHT;
import static domain.Fixtures.SIX_ZERO;
import static domain.Fixtures.THREE_SEVEN;
import static domain.Fixtures.TWO_ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.pattern.Pattern;
import domain.piece.Side;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiPositionTest {

    @Test
    void 보드판_밖을_벗어나면_예외를_발생시킨다() {
        // given
        JanggiPosition janggiPosition = new JanggiPosition(10, 0);

        // when & then
        assertThatThrownBy(() -> janggiPosition.validateBound())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 좌표를_위로_한_칸_이동시킬_수_있다() {
        // when
        JanggiPosition newPosition = ONE_ZERO.moveOnePosition(Pattern.UP);

        // then
        Assertions.assertThat(newPosition).isEqualTo(ONE_NINE);
    }

    @Test
    void 좌표를_이동시킬_수_있다() {
        // when
        JanggiPosition newPosition = ONE_ZERO.move(
                List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT, Pattern.DIAGONAL_UP_RIGHT));

        // then
        Assertions.assertThat(newPosition).isEqualTo(THREE_SEVEN);
    }

    @Test
    void x좌표끼리_비교하여_더_큰_좌표를_찾을_수_있다() {
        // when
        boolean isBiggerX = TWO_ZERO.isBiggerRankThan(ONE_ZERO);

        // then
        Assertions.assertThat(isBiggerX).isTrue();
    }

    @Test
    void y좌표끼리_비교하여_더_큰_좌표를_찾을_수_있다() {
        // when
        boolean isBiggerY = ONE_ZERO.isBiggerFileThan(ONE_THREE);

        // then
        Assertions.assertThat(isBiggerY).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"8, 0, 8", "7, 3, 4", "0, 8, 8"})
    void x좌표끼리의_차이를_구할_수_있다(int beforeX, int afterX, int expected) {
        // given
        int beforeY = 1;
        int afterY = 1;

        JanggiPosition beforePosition = new JanggiPosition(beforeX, beforeY);
        JanggiPosition afterPosition = new JanggiPosition(afterX, afterY);

        // when
        int gap = afterPosition.getRankGap(beforePosition);

        // then
        Assertions.assertThat(gap).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"8, 9, 1", "7, 3, 4", "8, 1, 7"})
    void y좌표끼리의_차이를_구할_수_있다(int beforeY, int afterY, int expected) {
        // given
        int beforeX = 1;
        int afterX = 1;

        JanggiPosition beforePosition = new JanggiPosition(beforeX, beforeY);
        JanggiPosition afterPosition = new JanggiPosition(afterX, afterY);

        // when
        int gap = afterPosition.getFileGap(beforePosition);

        // then
        Assertions.assertThat(gap).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideDiagonalPositions")
    void 이전_좌표와_이후_좌표가_대각선_방향임을_알_수_있다(JanggiPosition afterPosition) {
        // when & then
        assertThat(FIVE_NINE.isDiagonalTo(afterPosition)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideDiagonalPositions")
    void 궁성의_중앙을_지나는_이동임을_알_수_있다(JanggiPosition afterPosition) {
        // when & then
        assertThat(FIVE_NINE.isPassThroughCenter(Side.CHO, afterPosition)).isTrue();
    }

    static Stream<Arguments> provideDiagonalPositions() {
        return Stream.of(
                Arguments.of(SIX_EIGHT),
                Arguments.of(FOUR_EIGHT),
                Arguments.of(FOUR_ZERO),
                Arguments.of(SIX_ZERO));
    }
}
