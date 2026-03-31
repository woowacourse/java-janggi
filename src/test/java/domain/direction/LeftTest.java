package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LeftTest {

    private static final Intersection CURRENT_INTERSECTION = new Intersection(5, 5);
    private static final MoveAmount DEFAULT_MOVE_AMOUNT = new MoveAmount(1);

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_전진하면_좌표가_왼쪽으로_이동한다(int moveAmount) {
        // given
        Left left = new Left();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() - moveAmount
        );

        // when
        Intersection actual = left.moveForward(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_왼쪽으로_이동하면_좌표가_아래쪽으로_이동한다(int moveAmount) {
        // given
        Left left = new Left();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + moveAmount,
                CURRENT_INTERSECTION.getFile()
        );

        // when
        Intersection actual = left.moveLeft(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_오른쪽으로_이동하면_좌표가_위쪽으로_이동한다(int moveAmount) {
        // given
        Left left = new Left();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - moveAmount,
                CURRENT_INTERSECTION.getFile()
        );

        // when
        Intersection actual = left.moveRight(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_왼쪽_대각선으로_이동하면_좌표가_왼쪽_아래_대각선으로_이동한다(int moveAmount) {
        // given
        Left left = new Left();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + moveAmount,
                CURRENT_INTERSECTION.getFile() - moveAmount
        );

        // when
        Intersection actual = left.moveForwardLeft(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_오른쪽_대각선으로_이동하면_좌표가_왼쪽_위_대각선으로_이동한다(int moveAmount) {
        // given
        Left left = new Left();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - moveAmount,
                CURRENT_INTERSECTION.getFile() - moveAmount
        );

        // when
        Intersection actual = left.moveForwardRight(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 방향_계산이_반대인_객체를_반환한다() {
        // given
        Left left = new Left();
        Direction reverseDirection = left.reverse();

        Intersection startIntersection = CURRENT_INTERSECTION;

        // when
        Intersection movedIntersection = left.moveForward(
                startIntersection,
                DEFAULT_MOVE_AMOUNT
        );
        Intersection returnedIntersection = reverseDirection.moveForward(
                movedIntersection,
                DEFAULT_MOVE_AMOUNT
        );

        // then
        assertThat(returnedIntersection).isEqualTo(startIntersection);
    }
}
