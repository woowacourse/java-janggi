package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.movement.Vector;
import domain.movement.direction.Direction;
import domain.movement.direction.Up;
import org.junit.jupiter.api.Test;

class UpTest {

    private static final Intersection CURRENT_INTERSECTION = new Intersection(5, 5);

    @Test
    void 방향을_기준으로_전진하면_좌표가_위쪽으로_이동한다() {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - 1,
                CURRENT_INTERSECTION.getFile()
        );

        // when
        Vector forward = up.toForward();
        Intersection actual = forward.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_왼쪽으로_이동하면_좌표가_왼쪽으로_이동한다() {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() - 1
        );

        // when
        Vector left = up.toLeft();
        Intersection actual = left.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_오른쪽으로_이동하면_좌표가_오른쪽으로_이동한다() {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() + 1
        );

        // when
        Vector right = up.toRight();
        Intersection actual = right.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_왼쪽_대각선으로_이동하면_좌표가_왼쪽_위_대각선으로_이동한다() {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - 1,
                CURRENT_INTERSECTION.getFile() - 1
        );

        // when
        Vector forwardLeft = up.toForwardLeft();
        Intersection actual = forwardLeft.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_오른쪽_대각선으로_이동하면_좌표가_오른쪽_위_대각선으로_이동한다() {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - 1,
                CURRENT_INTERSECTION.getFile() + 1
        );

        // when
        Vector forwardRight = up.toForwardRight();
        Intersection actual = forwardRight.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향_계산이_반대인_객체를_반환한다() {
        // given
        Up up = new Up();
        Direction reverseDirection = up.reverse();

        Intersection startIntersection = CURRENT_INTERSECTION;

        // when
        Vector forward = up.toForward();
        Vector reverseForward = reverseDirection.toForward();

        Intersection movedIntersection = forward.next(startIntersection);
        Intersection returnedIntersection = reverseForward.next(movedIntersection);

        // then
        assertThat(returnedIntersection).isEqualTo(startIntersection);
    }
}
