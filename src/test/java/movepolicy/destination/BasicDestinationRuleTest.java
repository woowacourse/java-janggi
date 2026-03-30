package movepolicy.destination;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import pieces.EmptyPiece;
import pieces.Gung;
import pieces.Piece;
import pieces.Side;

class BasicDestinationRuleTest {

    @Test
    void 출발지_기물과_도착지_기물이_같은_진영이면_예외를_던진다() {
        // given
        Piece departurePiece = new Gung(Side.HAN);
        Piece destinationPiece = new Gung(Side.HAN);
        DestinationRule destinationRule = new BasicDestinationRule();
        // when & then
        assertThatThrownBy(() ->
            destinationRule.validateDestination(departurePiece, destinationPiece))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발지_기물과_도착지_기물이_다른_진영이면_이동할_수_있다() {
        // given
        Piece departurePiece = new Gung(Side.CHO);
        Piece destinationPiece = new Gung(Side.HAN);
        DestinationRule destinationRule = new BasicDestinationRule();
        // when & then
        assertThatCode(() -> destinationRule.validateDestination(departurePiece, destinationPiece))
            .doesNotThrowAnyException();
    }

    @Test
    void 도착지_기물이_비어있으면_이동할_수_있다() {
        // given
        Piece departurePiece = new Gung(Side.CHO);
        Piece destinationPiece = new EmptyPiece();
        DestinationRule destinationRule = new BasicDestinationRule();
        // when & then
        assertThatCode(() -> destinationRule.validateDestination(departurePiece, destinationPiece))
            .doesNotThrowAnyException();
    }
}
