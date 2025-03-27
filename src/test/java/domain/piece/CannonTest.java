package domain.piece;

import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_FIVE;
import static domain.Fixtures.FIVE_NINE;
import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.FIVE_SIX;
import static domain.Fixtures.FOUR_SEVEN;
import static domain.Fixtures.SEVEN_SEVEN;
import static domain.Fixtures.SIX_SEVEN;
import static domain.Fixtures.THREE_SEVEN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.JanggiPosition;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CannonTest {
    @ParameterizedTest
    @MethodSource("provideCannonAfterPositions")
    void 포가_현재_위치에서_입력받은_위치로_이동_가능한지_알_수_있다(JanggiPosition afterPosition) {
        // given
        Piece piece = new Cannon(Side.CHO);

        // when & then
        assertDoesNotThrow(() -> piece.findMovablePath(FIVE_SEVEN, afterPosition));
    }

    static Stream<Arguments> provideCannonAfterPositions() {
        return Stream.of(
                Arguments.of(FOUR_SEVEN),
                Arguments.of(SIX_SEVEN),
                Arguments.of(FIVE_SIX),
                Arguments.of(FIVE_EIGHT),
                Arguments.of(FIVE_FIVE),
                Arguments.of(THREE_SEVEN),
                Arguments.of(FIVE_NINE),
                Arguments.of(SEVEN_SEVEN)
        );
    }
}
