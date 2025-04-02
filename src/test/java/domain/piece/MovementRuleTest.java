package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MovementRuleTest {
    @ParameterizedTest
    @MethodSource("piecesAndResult")
    void 해당_위치가_유효한_범위인지_판단한다(final Position position, final MovementRule rule, final boolean expected) {
        // when
        boolean result = rule.isValidRangePosition(position);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> piecesAndResult() {
        return Stream.of(
                Arguments.of(Position.ofUnverified(3, 1), MovementRule.HAN_GENERAL, false),
                Arguments.of(Position.ofUnverified(6, 3), MovementRule.HAN_GENERAL, true),
                Arguments.of(Position.ofUnverified(3, 8), MovementRule.CHO_GENERAL, false),
                Arguments.of(Position.ofUnverified(6, 10), MovementRule.CHO_GENERAL, true),
                Arguments.of(Position.ofUnverified(0, 10), MovementRule.CANNON, false),
                Arguments.of(Position.ofUnverified(1, 10), MovementRule.CANNON, true)
        );
    }
}
