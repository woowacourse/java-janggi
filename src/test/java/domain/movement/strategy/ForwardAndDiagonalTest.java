package domain.movement.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ForwardAndDiagonalTest {

    private static final Intersection START_INTERSECTION = new Intersection(5, 5);

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3, 4, 5
    })
    void 직진_후_지정된_만큼_대각선으로_이동한_경로를_반환한다(int diagonalAmount) {
        // given
        MoveAmount moveAmount = new MoveAmount(diagonalAmount);
        ForwardAndDiagonal forwardAndDiagonal = new ForwardAndDiagonal(moveAmount);

        Vector forward = new Vector(1, 0);
        Intersection forwardNode = forward.next(START_INTERSECTION);
        Vector leftDiagonal = forward.turnLeft45Degrees();
        Vector rightDiagonal = forward.turnRight45Degrees();

        Set<Route> expected = Set.of(
                new Route(diagonalPathFrom(forwardNode, diagonalAmount, leftDiagonal)),
                new Route(diagonalPathFrom(forwardNode, diagonalAmount, rightDiagonal))
        );

        // when
        List<Route> actual = forwardAndDiagonal.getRoutes(START_INTERSECTION, List.of(forward));

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    private static List<Intersection> diagonalPathFrom(
            Intersection forwardNode,
            int diagonalAmount,
            Vector diagonalVector
    ) {
        List<Intersection> path = new ArrayList<>();

        path.add(forwardNode);
        Intersection current = forwardNode;
        for (int i = 0; i < diagonalAmount; i++) {
            current = diagonalVector.next(current);
            path.add(current);
        }

        return path;
    }
}
