package domain.direction;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UpTest {

    private static final Intersection CURRENT_INTERSECTION = new Intersection(5, 5);

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_전진하면_좌표가_위쪽으로_이동한다(int moveAmount) {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - moveAmount,
                CURRENT_INTERSECTION.getFile()
        );

        // when
        Intersection actual = up.moveForward(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_왼쪽으로_이동하면_좌표가_왼쪽으로_이동한다(int moveAmount) {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() - moveAmount
        );

        // when
        Intersection actual = up.moveLeft(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_오른쪽으로_이동하면_좌표가_오른쪽으로_이동한다(int moveAmount) {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow(),
                CURRENT_INTERSECTION.getFile() + moveAmount
        );

        // when
        Intersection actual = up.moveRight(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_왼쪽_대각선으로_이동하면_좌표가_왼쪽_위_대각선으로_이동한다(int moveAmount) {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - moveAmount,
                CURRENT_INTERSECTION.getFile() - moveAmount
        );

        // when
        Intersection actual = up.moveForwardLeft(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3
    })
    void 방향을_기준으로_오른쪽_대각선으로_이동하면_좌표가_오른쪽_위_대각선으로_이동한다(int moveAmount) {
        // given
        Up up = new Up();
        Intersection expected = new Intersection(
                CURRENT_INTERSECTION.getRow() - moveAmount,
                CURRENT_INTERSECTION.getFile() + moveAmount
        );

        // when
        Intersection actual = up.moveForwardRight(CURRENT_INTERSECTION, new MoveAmount(moveAmount));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 반대_방향으로_아래쪽을_반환한다() {
        // given
        Up up = new Up();

        // when
        Direction reverseDirection = up.reverse();

        // then
        assertThat(reverseDirection).isInstanceOf(Down.class);
    }
}
