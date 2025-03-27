package domain.piece;

import static domain.Fixtures.EIGHT_FIVE;
import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_NINE;
import static domain.Fixtures.FIVE_ZERO;
import static domain.Fixtures.FOUR_EIGHT;
import static domain.Fixtures.FOUR_NINE;
import static domain.Fixtures.FOUR_ZERO;
import static domain.Fixtures.NINE_FOUR;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.SIX_EIGHT;
import static domain.Fixtures.SIX_NINE;
import static domain.Fixtures.SIX_ZERO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.JanggiPosition;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GuardTest {
    @ParameterizedTest
    @MethodSource("provideGuardAfterPositions")
    void 사가_궁성_안의_현재_위치에서_입력받은_위치로_이동_가능한지_알_수_있다(JanggiPosition afterPosition) {
        // given
        Piece piece = new Guard(Side.CHO);

        // when & then
        assertDoesNotThrow(() -> piece.findMovablePath(FIVE_NINE, afterPosition));
    }

    static Stream<Arguments> provideGuardAfterPositions() {
        return Stream.of(
                Arguments.of(FOUR_ZERO),
                Arguments.of(FIVE_ZERO),
                Arguments.of(SIX_ZERO),
                Arguments.of(FOUR_NINE),
                Arguments.of(SIX_NINE),
                Arguments.of(FOUR_EIGHT),
                Arguments.of(FIVE_EIGHT),
                Arguments.of(SIX_EIGHT)
        );
    }

    @Test
    void 사를_궁성밖에서_이동시킬_수_없다() {
        // given
        Piece piece = new Guard(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(EIGHT_FIVE, SEVEN_FIVE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된_대각선_방향으로_이동시킬_수_없다() {
        // given
        Piece piece = new Guard(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(EIGHT_FIVE, NINE_FOUR))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
