package domain.piece;

import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_TWO;
import static domain.Fixtures.FOUR_SEVEN;
import static domain.Fixtures.FOUR_THREE;
import static domain.Fixtures.NINE_EIGHT;
import static domain.Fixtures.NINE_TWO;
import static domain.Fixtures.SEVEN_FIVE;
import static domain.Fixtures.ZERO_SEVEN;
import static domain.Fixtures.ZERO_THREE;
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
        assertDoesNotThrow(() -> piece.findMovablePath(SEVEN_FIVE, afterPosition));
    }

    static Stream<Arguments> provideElephantAfterPositions() {
        return Stream.of(
                Arguments.of(FOUR_SEVEN),
                Arguments.of(FOUR_THREE),
                Arguments.of(FIVE_TWO),
                Arguments.of(NINE_TWO),
                Arguments.of(ZERO_THREE),
                Arguments.of(ZERO_SEVEN),
                Arguments.of(NINE_EIGHT),
                Arguments.of(FIVE_EIGHT)
        );
    }
}
