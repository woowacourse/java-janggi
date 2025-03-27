package domain.piece;

import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.FOUR_FIVE;
import static domain.Fixtures.FOUR_NINE;
import static domain.Fixtures.SEVEN_EIGHT;
import static domain.Fixtures.SEVEN_SIX;
import static domain.Fixtures.SIX_FIVE;
import static domain.Fixtures.SIX_NINE;
import static domain.Fixtures.THREE_EIGHT;
import static domain.Fixtures.THREE_SIX;
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
        assertDoesNotThrow(() -> piece.findMovablePath(FIVE_SEVEN, afterPosition));
    }

    static Stream<Arguments> provideHorseAfterPositions() {
        return Stream.of(
                Arguments.of(SIX_FIVE),
                Arguments.of(FOUR_FIVE),
                Arguments.of(THREE_SIX),
                Arguments.of(THREE_EIGHT),
                Arguments.of(FOUR_NINE),
                Arguments.of(SIX_NINE),
                Arguments.of(SEVEN_EIGHT),
                Arguments.of(SEVEN_SIX)
        );
    }
}
