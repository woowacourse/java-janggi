package janggi.domain.piece.movement.normal.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class PawnMovementStrategyTest {

    private static final Side ALLY_SIDE = Side.HAN;
    private static final Side ENEMY_SIDE = Side.CHO;
    private static final PawnMovementStrategy PAWN_MOVEMENT_STRATEGY = new PawnMovementStrategy();

    public static Stream<Arguments> 앞과_양옆으로_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(6, 5)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(5, 6)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(4, 5)),
            Arguments.of(ENEMY_SIDE, new Position(5, 5), new Position(6, 5)),
            Arguments.of(ENEMY_SIDE, new Position(5, 5), new Position(5, 4)),
            Arguments.of(ENEMY_SIDE, new Position(5, 5), new Position(4, 5))
        );
    }


    public static Stream<Arguments> 앞과_양옆외에는_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(7, 5)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(5, 7)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(3, 5)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(7, 5)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(5, 3)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(3, 5)),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(5, 4)),
            Arguments.of(ENEMY_SIDE, new Position(5, 5), new Position(5, 6))
        );
    }

    private static Stream<Arguments> 한나라는_위로만_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(5, 6), true),
            Arguments.of(ALLY_SIDE, new Position(5, 5), new Position(5, 4), false)
        );
    }

    private static Stream<Arguments> 초나라는_위로만_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(ENEMY_SIDE, new Position(5, 5), new Position(5, 6), false),
            Arguments.of(ENEMY_SIDE, new Position(5, 5), new Position(5, 4), true)
        );
    }

    @ParameterizedTest
    @MethodSource("앞과_양옆으로_이동할_수_있다_테스트_케이스")
    void 앞과_양옆으로_이동할_수_있다(Side side, Position origin, Position destination) {
        assertThat(PAWN_MOVEMENT_STRATEGY.isLegalDestination(side, origin, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("앞과_양옆외에는_이동할_수_없다_테스트_케이스")
    void 앞과_양옆외에는_이동할_수_없다(Side side, Position origin, Position destination) {
        assertThat(PAWN_MOVEMENT_STRATEGY.isLegalDestination(side, origin, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("한나라는_위로만_이동할_수_있다_테스트_케이스")
    void 한나라는_위로만_이동할_수_있다(Side side, Position origin, Position destination, boolean expected) {
        assertThat(PAWN_MOVEMENT_STRATEGY.isLegalDestination(side, origin, destination)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("초나라는_위로만_이동할_수_있다_테스트_케이스")
    void 초나라는_위로만_이동할_수_있다(Side side, Position origin, Position destination, boolean expected) {
        assertThat(PAWN_MOVEMENT_STRATEGY.isLegalDestination(side, origin, destination)).isEqualTo(expected);
    }
}
