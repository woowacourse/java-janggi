package domain.piece;

import static domain.Fixtures.EIGHT_FIVE;
import static domain.Fixtures.EIGHT_NINE;
import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.SEVEN_FOUR;
import static domain.Fixtures.SEVEN_ZERO;
import static domain.Fixtures.THREE_FOUR;
import static domain.Fixtures.THREE_ZERO;
import static domain.Fixtures.TWO_FIVE;
import static domain.Fixtures.TWO_NINE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.JanggiPosition;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ElephantTest {
    @ParameterizedTest
    @MethodSource("provideElephantAfterPositions")
    void 상이_현재_위치에서_입력받은_위치로_이동_가능한지_알_수_있다(JanggiPosition afterPosition) {
        // given
        Piece piece = new Elephant(Side.CHO);

        // when & then
        assertDoesNotThrow(() -> piece.findMovablePath(FIVE_SEVEN, afterPosition));
    }

    static Stream<Arguments> provideElephantAfterPositions() {
        return Stream.of(
                Arguments.of(SEVEN_FOUR),
                Arguments.of(THREE_FOUR),
                Arguments.of(TWO_FIVE),
                Arguments.of(TWO_NINE),
                Arguments.of(THREE_ZERO),
                Arguments.of(SEVEN_ZERO),
                Arguments.of(EIGHT_NINE),
                Arguments.of(EIGHT_FIVE)

        );
    }
}
