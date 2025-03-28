package piece;

import board.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.LineDirection;
import position.Position;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GeneralTest {

    @DisplayName("출발지에서 도착지까지의 거리는 1이여야 한다.")
    @Test
    void distance() {
        // given
        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 3);
        final General general = new General(dumyPosition, dumyCountry);
        double expected = 1.0;

        // when
        double actual = general.getExpectedDistance();
        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("General은 주변 한칸으로 이동할 수 있다.")
    @Test
    void validateMove() {
        // given
        final Position src = new Position(1, 1);
        final Piece general = new General(src, Country.HAN);
        final Board board = new Board(new HashMap<>());

        // when & then : 1 : success
        Position validDest = new Position(1, 2);
        assertThatCode(() -> general.validateMove(src, validDest, board))
                .doesNotThrowAnyException();

        // when & then : 2 : failure
        Position invalidDest = new Position(1, 3);
        assertThatThrownBy(() -> general.validateMove(src, invalidDest, board))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
