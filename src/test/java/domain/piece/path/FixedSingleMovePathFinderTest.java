package domain.piece.path;

import static fixtures.PositionFixture.*;
import static org.assertj.core.api.Assertions.*;

import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FixedSingleMovePathFinderTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("가능한 경로라면 예외를 발생시키지 않는다.")
    void findIntermediatePositionsTest(Position from, Position to){
        List<Direction> directions = List.of(Direction.RIGHT,Direction.LEFT,Direction.DOWN,Direction.UP);
        FixedSingleMovePathFinder pathFinder = new FixedSingleMovePathFinder(directions);

        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(from, to);
        assertThat(intermediatePositions).isEqualTo(List.of());
    }

    private static Stream<Arguments> findIntermediatePositionsTest(){
        return Stream.of(
                Arguments.of(E4,E3),
                Arguments.of(E4,E5),
                Arguments.of(E4,F4),
                Arguments.of(E4,D4),
                Arguments.of(D0,E1),
                Arguments.of(F0,E1),
                Arguments.of(D2,E1),
                Arguments.of(F2,E1),
                Arguments.of(E1,D0),
                Arguments.of(E1,F0),
                Arguments.of(E1,D2),
                Arguments.of(E1,F2)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("불가능한 경로라면 예외가 발생한다.")
    void findIntermediatePositionsException(Position from, Position to){
        List<Direction> directions = List.of(Direction.RIGHT,Direction.LEFT,Direction.DOWN,Direction.UP);
        FixedSingleMovePathFinder pathFinder = new FixedSingleMovePathFinder(directions);

        assertThatThrownBy(()->pathFinder.findIntermediatePositions(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    private static Stream<Arguments> findIntermediatePositionsException(){
        return Stream.of(
                Arguments.of(D1,E2),
                Arguments.of(G1,E2),
                Arguments.of(D1,E0),
                Arguments.of(G1,E0)
        );
    }
}