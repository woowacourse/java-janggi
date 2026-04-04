package domain.movepolicy.destination;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.movepolicy.exception.InvalidDestinationException;
import domain.movepolicy.exception.MovePolicyErrorMessage;
import org.junit.jupiter.api.Test;
import domain.pieces.EmptyPiece;
import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.Side;

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
            .isInstanceOf(InvalidDestinationException.class)
            .hasMessage(MovePolicyErrorMessage.SAME_SIDE_ATTACK.message());
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
