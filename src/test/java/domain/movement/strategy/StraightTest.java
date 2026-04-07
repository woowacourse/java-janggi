package domain.movement.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import java.util.List;
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
