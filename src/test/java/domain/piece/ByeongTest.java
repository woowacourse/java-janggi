package domain.piece;

import static domain.Fixtures.FIVE_FIVE;
import static domain.Fixtures.FOUR_FIVE;
import static domain.Fixtures.FOUR_FOUR;
import static domain.Fixtures.FOUR_SIX;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.JanggiPosition;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ByeongTest {
    @ParameterizedTest
    @MethodSource("provideByeongAfterPositions")
    void 병이_현재_위치에서_입력받은_위치로_이동_가능한지_알_수_있다(JanggiPosition afterPosition) {
        // given
        Piece piece = new Soldier(Side.HAN);

        // when & then
        assertDoesNotThrow(() -> piece.findMovablePath(FOUR_FIVE, afterPosition));
    }

    static Stream<Arguments> provideByeongAfterPositions() {
        return Stream.of(
                Arguments.of(FOUR_FOUR),
                Arguments.of(FOUR_SIX),
                Arguments.of(FIVE_FIVE)
        );
    }
}
