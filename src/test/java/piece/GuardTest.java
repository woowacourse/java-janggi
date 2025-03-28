package piece;

import board.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.LineDirection;
import position.Position;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GuardTest {

    @DisplayName("출발지에서 도착지까지의 거리는 1이여야 한다.")
    @Test
    void distance() {
        // given
        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 3);
        final Guard guard = new Guard(dumyPosition, dumyCountry);
        double expected = 1.0;

        // when
        double actual = guard.getExpectedDistance();
        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Guard은 주변 한칸으로 이동할 수 있다.")
    @Test
    void validateMove() {
        // given
        final Position src = new Position(1, 1);
        final Piece guard = new Guard(src, Country.HAN);
        final Board board = new Board(Map.of(src, guard));

        // when & then : 1 : success
        final Position validDest = new Position(1, 2);
        assertThatCode(() -> guard.validateMove(src, validDest, board))
                .doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position invalidDest = new Position(1, 3);
        assertThatThrownBy(() -> guard.validateMove(src, invalidDest, board))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
