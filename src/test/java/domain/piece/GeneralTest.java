package domain.piece;

import static domain.Fixtures.EIGHT_FIVE;
import static domain.Fixtures.EIGHT_FOUR;
import static domain.Fixtures.EIGHT_SIX;
import static domain.Fixtures.NINE_FIVE;
import static domain.Fixtures.NINE_FOUR;
import static domain.Fixtures.NINE_SIX;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.ZERO_FIVE;
import static domain.Fixtures.ZERO_FOUR;
import static domain.Fixtures.ZERO_SIX;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.JanggiPosition;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GeneralTest {
    @ParameterizedTest
    @MethodSource("provideGeneralAfterPositions")
    void 궁을_궁성안에서_이동시킬_수_있다(JanggiPosition afterPosition) {
        // given
        Piece piece = new General(Side.CHO);

        // when & then
        assertDoesNotThrow(() -> piece.findMovablePath(NINE_FIVE, afterPosition));
    }

    static Stream<Arguments> provideGeneralAfterPositions() {
        return Stream.of(
                Arguments.of(ZERO_FOUR),
                Arguments.of(ZERO_FIVE),
                Arguments.of(ZERO_SIX),
                Arguments.of(NINE_FOUR),
                Arguments.of(NINE_SIX),
                Arguments.of(EIGHT_FOUR),
                Arguments.of(EIGHT_FIVE),
                Arguments.of(EIGHT_SIX)
        );
    }

    @Test
    void 궁을_궁성밖에서_이동시킬_수_없다() {
        // given
        Piece piece = new General(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(EIGHT_FIVE, SEVEN_FIVE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된_대각선_방향으로_이동시킬_수_없다() {
        // given
        Piece piece = new General(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(EIGHT_FIVE, NINE_FOUR))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
