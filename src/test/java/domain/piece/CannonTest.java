package domain.piece;

import static domain.Fixtures.EIGHT_FIVE;
import static domain.Fixtures.FIVE_FIVE;
import static domain.Fixtures.NINE_FIVE;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.SEVEN_FOUR;
import static domain.Fixtures.SEVEN_SEVEN;
import static domain.Fixtures.SEVEN_SIX;
import static domain.Fixtures.SEVEN_THREE;
import static domain.Fixtures.SIX_FIVE;
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
        assertDoesNotThrow(() -> piece.findMovablePath(SEVEN_FIVE, afterPosition));
    }

    static Stream<Arguments> provideCannonAfterPositions() {
        return Stream.of(
                Arguments.of(SEVEN_FOUR),
                Arguments.of(SEVEN_SIX),
                Arguments.of(SIX_FIVE),
                Arguments.of(EIGHT_FIVE),
                Arguments.of(FIVE_FIVE),
                Arguments.of(SEVEN_THREE),
                Arguments.of(NINE_FIVE),
                Arguments.of(SEVEN_SEVEN)
        );
    }
}
