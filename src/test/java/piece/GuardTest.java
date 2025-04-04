package piece;

import domain.board.Board;
import domain.board.Palace;
import domain.piece.Country;
import domain.piece.Guard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.position.LineDirection;
import domain.position.Position;
import domain.position.PositionFactory;

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

        Position dumyPosition = new Position(5, 2);
        Position src = dumyPosition;
        final Guard guard = new Guard(src, dumyCountry);
        Board board = new Board(Map.of(
                src, guard
        ));

        // when & then : 1 : success
        Position canReachDestPosition = new Position(4, 2);
        assertThatCode(
                () -> guard.validateMove(src, canReachDestPosition, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        Position canNotReachDestPosition = new Position(5, 5);
        assertThatThrownBy(
                () -> guard.validateMove(src, canNotReachDestPosition, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("인접 노드 한칸을 움직인다. : 대각선")
    @Test
    void adjacentPositionWithDiagonalSetting() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getAllPositions());

        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);

        Position dumyPosition = new Position(4, 3);
        Position src = dumyPosition;
        final Guard guard = new Guard(src, dumyCountry);
        Board board = new Board(Map.of(
                src, guard
        ));

        // when & then : 1 : success
        Position canReachDestPosition = new Position(5, 2);
        assertThatCode(
                () -> guard.validateMove(src, canReachDestPosition, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        Position canNotReachDestPosition = new Position(5, 4);
        assertThatThrownBy(
                () -> guard.validateMove(src, canNotReachDestPosition, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("궁성 밖을 빠져나갈 수 없다.")
    @Test
    void validateMoveByPalaceBound() {
        // given
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getAllPositions());

        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);

        Position dumyPosition = new Position(4, 3);
        Position src = dumyPosition;
        final Guard guard = new Guard(src, dumyCountry);
        Board board = new Board(Map.of(
                src, guard
        ));

        // when & then : 1 : success
        Position canReachDestPosition = new Position(5, 3);
        assertThatCode(
                () -> guard.validateMove(src, canReachDestPosition, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        Position canNotReachDestPosition = new Position(4, 4);
        assertThatThrownBy(
                () -> guard.validateMove(src, canNotReachDestPosition, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
