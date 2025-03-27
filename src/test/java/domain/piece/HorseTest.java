package domain.piece;

import static domain.Fixtures.EIGHT_SEVEN;
import static domain.Fixtures.EIGHT_THREE;
import static domain.Fixtures.FIVE_FOUR;
import static domain.Fixtures.FIVE_SIX;
import static domain.Fixtures.NINE_FOUR;
import static domain.Fixtures.NINE_SIX;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.SIX_SEVEN;
import static domain.Fixtures.SIX_THREE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.JanggiPosition;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HorseTest {
    @ParameterizedTest
    @MethodSource("provideHorseAfterPositions")
    void 마가_현재_위치에서_입력받은_위치로_이동_가능한지_알_수_있다(JanggiPosition afterPosition) {
        // given
        Piece piece = new Horse(Side.CHO);

        // when & then
        assertDoesNotThrow(() -> piece.findMovablePath(SEVEN_FIVE, afterPosition));
    }

    static Stream<Arguments> provideHorseAfterPositions() {
        return Stream.of(
                Arguments.of(FIVE_SIX),
                Arguments.of(FIVE_FOUR),
                Arguments.of(SIX_THREE),
                Arguments.of(EIGHT_THREE),
                Arguments.of(NINE_FOUR),
                Arguments.of(NINE_SIX),
                Arguments.of(EIGHT_SEVEN),
                Arguments.of(SIX_SEVEN)
        );
    }
}
