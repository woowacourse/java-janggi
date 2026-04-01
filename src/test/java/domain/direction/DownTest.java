package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.movement.Vector;
import domain.movement.direction.Direction;
import domain.movement.direction.Down;
import org.junit.jupiter.api.Test;

class DownTest {

    private static final Intersection CURRENT_INTERSECTION = new Intersection(5, 5);

    @Test
    void 방향을_기준으로_전진하면_좌표가_아래쪽으로_이동한다() {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + 1,
                CURRENT_INTERSECTION.getFile()
        );

        // when
        Vector forward = down.toForward();
        Intersection actual = forward.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_왼쪽으로_이동하면_좌표가_오른쪽으로_이동한다() {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() + 1
        );

        // when
        Vector left = down.toLeft();
        Intersection actual = left.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_오른쪽으로_이동하면_좌표가_왼쪽으로_이동한다() {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() - 1
        );

        // when
        Vector right = down.toRight();
        Intersection actual = right.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_왼쪽_대각선으로_이동하면_좌표가_오른쪽_아래_대각선으로_이동한다() {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + 1,
                CURRENT_INTERSECTION.getFile() + 1
        );

        // when
        Vector forwardLeft = down.toForwardLeft();
        Intersection actual = forwardLeft.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향을_기준으로_오른쪽_대각선으로_이동하면_좌표가_왼쪽_아래_대각선으로_이동한다() {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + 1,
                CURRENT_INTERSECTION.getFile() - 1
        );

        // when
        Vector forwardRight = down.toForwardRight();
        Intersection actual = forwardRight.next(CURRENT_INTERSECTION);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향_계산이_반대인_객체를_반환한다() {
        // given
        Down down = new Down();
        Direction reverseDirection = down.reverse();

        Intersection startIntersection = CURRENT_INTERSECTION;

        // when
        Vector forward = down.toForward();
        Vector reverseForward = reverseDirection.toForward();

        Intersection movedIntersection = forward.next(startIntersection);
        Intersection returnedIntersection = reverseForward.next(movedIntersection);

        // then
        assertThat(returnedIntersection).isEqualTo(startIntersection);
    }
}
