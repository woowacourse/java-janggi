package domain.movement.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

    @Nested
    class 궁성_영역에_해당하는_경로들을_반환한다 {

        @Test
        void 궁성으로_이동_가능한_모든_방향에_대한_경로를_반환한다() {
            // given
            MoveAmount moveAmount = new MoveAmount(1);
            ForwardAndDiagonal forwardAndDiagonal = new ForwardAndDiagonal(moveAmount);

            Intersection palaceIntersection = new Intersection(3, 4);

            // when
            List<Route> routes = forwardAndDiagonal.getPalaceRoutes(palaceIntersection);

            // then
            assertThat(routes).isNotEmpty();
            assertThat(routes).allMatch(Route::containsOnlyPalace);
        }

        @Test
        void 궁성으로_이동_가능한_방향_중_허용된_방향에_대한_경로만_반환한다() {
            // given
            MoveAmount moveAmount = new MoveAmount(1);
            ForwardAndDiagonal forwardAndDiagonal = new ForwardAndDiagonal(moveAmount);

            Vector allowedVector = new Vector(1, 1);
            Intersection palaceIntersection = new Intersection(1, 4);

            Intersection expectedForwardNode = allowedVector.next(palaceIntersection);
            Intersection expectedLeftDestination = allowedVector.turnLeft45Degrees()
                    .next(expectedForwardNode);
            Intersection expectedRightDestination = allowedVector.turnRight45Degrees()
                    .next(expectedForwardNode);

            List<Route> expected = List.of(
                    new Route(List.of(
                            expectedForwardNode,
                            expectedLeftDestination
                    )),
                    new Route(List.of(
                            expectedForwardNode,
                            expectedRightDestination
                    ))
            );

            // when
            List<Route> actual = forwardAndDiagonal.getPalaceRoutes(palaceIntersection, List.of(allowedVector));

            // then
            assertThat(actual).containsAll(expected);
            assertThat(actual).allMatch(Route::containsOnlyPalace);
        }
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
