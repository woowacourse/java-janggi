package piece;

import board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.LineDirection;
import position.Position;
import position.PositionFactory;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GuardTest {

    @DisplayName("인접 노드 한칸을 움직인다.")
    @Test
    void adjacentPosition() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();

        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);

        Position dumyPosition = new Position(2, 3);
        Position src = dumyPosition;
        final Guard guard = new Guard(src, dumyCountry);
        Board board = new Board(Map.of(
                src, guard
        ));

        // when & then : 1 : success
        Position canReachDestPosition = new Position(3, 3);
        assertThatCode(
                () -> guard.validateMove(src, canReachDestPosition, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        Position canNotReachDestPosition = new Position(4, 3);
        assertThatThrownBy(
                () -> guard.validateMove(src, canNotReachDestPosition, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
