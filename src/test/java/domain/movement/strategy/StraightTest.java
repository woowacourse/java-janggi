package domain.movement.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StraightTest {

    private static final Intersection DEFAULT_INTERSECTION = new Intersection(5, 5);

    @Test
    void 지정한_거리_이하로_직선_이동한_경로들을_모두_반환한다() {
        // given
        MoveAmount moveAmount = new MoveAmount(2);
        Straight straight = new Straight(moveAmount);

        int fromRow = 3;
        int fromFile = 4;
        Intersection from = new Intersection(fromRow, fromFile);

        Intersection forwardOneRow = new Intersection(fromRow + 1, fromFile);
        Intersection forwardTwoRow = new Intersection(fromRow + 2, fromFile);

        Vector vector = new Vector(1, 0);
        List<Route> expected = List.of(
                new Route(List.of(forwardOneRow)),
                new Route(List.of(forwardOneRow, forwardTwoRow))
        );

        // when
        List<Route> actual = straight.getRoutes(from, List.of(vector));

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Nested
    class 궁성_영역에_해당하는_경로들을_반환한다 {

        @Test
        void 궁성으로_이동_가능한_모든_방향에_대한_경로를_반환한다() {
            // given
            Straight straight = new Straight(MoveAmount.maximum());

            Intersection palaceIntersection = new Intersection(2, 5);

            // when
            List<Route> routes = straight.getPalaceRoutes(palaceIntersection);

            // then=
            assertThat(routes).isNotEmpty();
            assertThat(routes).allMatch(Route::containsOnlyPalace);
        }

        @Test
        void 궁성으로_이동_가능한_방향_중_허용된_방향에_대한_경로만_반환한다() {
            // given
            Straight straight = new Straight(new MoveAmount(1));

            Intersection palceIntersection = new Intersection(2, 5);
            Vector allowedVector = new Vector(1, 1);
            Route expected = new Route(List.of(
                    allowedVector.next(palceIntersection)
            ));

            // when
            List<Route> actual = straight.getPalaceRoutes(palceIntersection, List.of(allowedVector));

            // then
            assertThat(actual).containsExactly(expected);
        }
    }

    @Test
    void 이동_거리가_없다면_빈_목록을_반환한다() {
        // given
        MoveAmount zeroMoveAmount = new MoveAmount(0);
        Straight straight = new Straight(zeroMoveAmount);

        Vector vector = Side.HAN.toForward();

        // when
        List<Route> routes = straight.getRoutes(DEFAULT_INTERSECTION, List.of(vector));

        // then
        assertThat(routes).isEmpty();
    }
}
