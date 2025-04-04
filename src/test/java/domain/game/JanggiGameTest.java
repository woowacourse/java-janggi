package domain.game;

import static domain.Fixtures.EIGHT_EIGHT;
import static domain.Fixtures.EIGHT_ONE;
import static domain.Fixtures.EIGHT_THREE;
import static domain.Fixtures.EIGHT_ZERO;
import static domain.Fixtures.FIVE_FOUR;
import static domain.Fixtures.FIVE_NINE;
import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.FIVE_TWO;
import static domain.Fixtures.FOUR_ONE;
import static domain.Fixtures.FOUR_ZERO;
import static domain.Fixtures.NINE_FOUR;
import static domain.Fixtures.NINE_ONE;
import static domain.Fixtures.NINE_SEVEN;
import static domain.Fixtures.NINE_ZERO;
import static domain.Fixtures.ONE_FOUR;
import static domain.Fixtures.ONE_ONE;
import static domain.Fixtures.ONE_SEVEN;
import static domain.Fixtures.ONE_ZERO;
import static domain.Fixtures.SEVEN_FOUR;
import static domain.Fixtures.SEVEN_ONE;
import static domain.Fixtures.SEVEN_SEVEN;
import static domain.Fixtures.SEVEN_ZERO;
import static domain.Fixtures.SIX_ONE;
import static domain.Fixtures.SIX_ZERO;
import static domain.Fixtures.THREE_FOUR;
import static domain.Fixtures.THREE_ONE;
import static domain.Fixtures.THREE_SEVEN;
import static domain.Fixtures.THREE_ZERO;
import static domain.Fixtures.TWO_EIGHT;
import static domain.Fixtures.TWO_ONE;
import static domain.Fixtures.TWO_THREE;
import static domain.Fixtures.TWO_ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import dao.JanggiDao;
import domain.JanggiPosition;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.Soldier;
import java.util.stream.Stream;
import service.JanggiService;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class JanggiGameTest {
    @ParameterizedTest
    @MethodSource("provideJanggiPieces")
    void 장기_게임을_시작하면_장기_기물들이_보드에_초기화된다(JanggiPosition position, Piece piece) {
        // given
        GameState state = new Start(new JanggiService(new JanggiDao()));

        // when & then
        assertThat(state.getBoard().get(position)).isInstanceOf(piece.getClass());
    }

    static Stream<Arguments> provideJanggiPieces() {
        return Stream.of(
                Arguments.of(ONE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(THREE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(FIVE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(SEVEN_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(NINE_SEVEN, new Soldier(Side.CHO)),
                Arguments.of(TWO_EIGHT, new Cannon(Side.CHO)),
                Arguments.of(EIGHT_EIGHT, new Cannon(Side.CHO)),
                Arguments.of(ONE_ZERO, new Chariot(Side.CHO)),
                Arguments.of(NINE_ZERO, new Chariot(Side.CHO)),
                Arguments.of(TWO_ZERO, new Horse(Side.CHO)),
                Arguments.of(EIGHT_ZERO, new Horse(Side.CHO)),
                Arguments.of(THREE_ZERO, new Elephant(Side.CHO)),
                Arguments.of(SEVEN_ZERO, new Elephant(Side.CHO)),
                Arguments.of(FOUR_ZERO, new Guard(Side.CHO)),
                Arguments.of(SIX_ZERO, new Guard(Side.CHO)),
                Arguments.of(FIVE_NINE, new General(Side.CHO)),

                Arguments.of(ONE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(THREE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(FIVE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(SEVEN_FOUR, new Soldier(Side.HAN)),
                Arguments.of(NINE_FOUR, new Soldier(Side.HAN)),
                Arguments.of(TWO_THREE, new Cannon(Side.HAN)),
                Arguments.of(EIGHT_THREE, new Cannon(Side.HAN)),
                Arguments.of(ONE_ONE, new Chariot(Side.HAN)),
                Arguments.of(NINE_ONE, new Chariot(Side.HAN)),
                Arguments.of(TWO_ONE, new Horse(Side.HAN)),
                Arguments.of(EIGHT_ONE, new Horse(Side.HAN)),
                Arguments.of(THREE_ONE, new Elephant(Side.HAN)),
                Arguments.of(SEVEN_ONE, new Elephant(Side.HAN)),
                Arguments.of(FOUR_ONE, new Guard(Side.HAN)),
                Arguments.of(SIX_ONE, new Guard(Side.HAN)),
                Arguments.of(FIVE_TWO, new General(Side.HAN)));
    }
}
