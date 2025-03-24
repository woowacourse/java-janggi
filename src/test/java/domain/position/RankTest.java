package domain.position;

import domain.MovingPattern;
import janggiexception.OutOfBoardException;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RankTest {

    @ParameterizedTest
    @MethodSource("provideRankValues")
    void 행을_기준으로_이동할_수_있다(Rank originalRank, MovingPattern movePattern, Rank expectedRank) {
        // when & then
        Assertions.assertThat(originalRank.moveRank(movePattern))
                .isEqualTo(expectedRank);
    }

    private static Stream<Arguments> provideRankValues() {
        return Stream.of(
                Arguments.of(Rank.FIVE, MovingPattern.MOVE_DOWN, Rank.SIX),
                Arguments.of(Rank.ONE, MovingPattern.MOVE_DOWN, Rank.TWO),
                Arguments.of(Rank.NINE, MovingPattern.MOVE_DOWN, Rank.ZERO),
                Arguments.of(Rank.FIVE, MovingPattern.MOVE_UP, Rank.FOUR),
                Arguments.of(Rank.NINE, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, Rank.ZERO)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRankValuesAndMovable")
    void 움직일_수_있는지_확인할_수_있다(Rank originalRank, MovingPattern movePattern, boolean expected) {
        // when & then
        Assertions.assertThat(originalRank.canMoveRank(movePattern))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideRankValuesAndMovable() {
        return Stream.of(
                Arguments.of(Rank.FIVE, MovingPattern.MOVE_DOWN, true),
                Arguments.of(Rank.ZERO, MovingPattern.MOVE_DOWN, false),
                Arguments.of(Rank.ONE, MovingPattern.MOVE_UP, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRanksAndNotMovable")
    void 더이상_움직일_수_없는_경우_예외를_발생시킨다(Rank rank, MovingPattern pattern) {
        // when & then
        Assertions.assertThatThrownBy(() -> rank.moveRank(pattern))
                .isInstanceOf(OutOfBoardException.class);
    }

    private static Stream<Arguments> provideRanksAndNotMovable() {
        return Stream.of(
                Arguments.of(Rank.ZERO, MovingPattern.MOVE_DOWN),
                Arguments.of(Rank.ONE, MovingPattern.MOVE_UP)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRanksAndComparingResults")
    void 행끼리_비교할_수_있다(Rank me, Rank other, boolean expected) {
        // when & expected
        Assertions.assertThat(me.isBiggerThan(other))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideRanksAndComparingResults() {
        return Stream.of(
                Arguments.of(Rank.NINE, Rank.ZERO, false),
                Arguments.of(Rank.NINE, Rank.ONE, true),
                Arguments.of(Rank.ONE, Rank.THREE, false),
                Arguments.of(Rank.ONE, Rank.ONE, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRanksAndGap")
    void 행끼리의_차이를_알_수_있다(Rank me, Rank other, int expected) {
        // when & expected
        Assertions.assertThat(me.getGapBetween(other))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideRanksAndGap() {
        return Stream.of(
                Arguments.of(Rank.FIVE, Rank.SIX, 1),
                Arguments.of(Rank.ZERO, Rank.NINE, 1),
                Arguments.of(Rank.ONE, Rank.THREE, 2),
                Arguments.of(Rank.ONE, Rank.ONE, 0)
        );
    }
}
