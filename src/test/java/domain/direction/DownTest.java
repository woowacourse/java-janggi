package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DownTest {

    private static final Intersection CURRENT_INTERSECTION = new Intersection(5, 5);

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_전진하면_좌표가_아래쪽으로_이동한다(int moveAmount) {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + moveAmount,
                CURRENT_INTERSECTION.getFile()
        );

        // when
        Intersection actual = down.moveForward(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_왼쪽으로_이동하면_좌표가_왼쪽으로_이동한다(int moveAmount) {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() + moveAmount
        );

        // when
        Intersection actual = down.moveLeft(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_오른쪽으로_이동하면_좌표가_오른쪽으로_이동한다(int moveAmount) {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() - moveAmount
        );

        // when
        Intersection actual = down.moveRight(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_왼쪽_대각선으로_이동하면_좌표가_오른쪽_아래_대각선으로_이동한다(int moveAmount) {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + moveAmount,
                CURRENT_INTERSECTION.getFile() + moveAmount
        );

        // when
        Intersection actual = down.moveForwardLeft(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_오른쪽_대각선으로_이동하면_좌표가_왼쪽_아래_대각선으로_이동한다(int moveAmount) {
        // given
        Down down = new Down();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() + moveAmount,
                CURRENT_INTERSECTION.getFile() - moveAmount
        );

        // when
        Intersection actual = down.moveForwardRight(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 반대_방향으로_위쪽을_반환한다() {
        // given
        Down down = new Down();

        // when
        Direction reverseDirection = down.reverse();

        // then
        assertThat(reverseDirection).isInstanceOf(Up.class);
    }
}
