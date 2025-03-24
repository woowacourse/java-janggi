package move;

import direction.Point;
import fixture.GreenPieceFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Pieces;

class CannonMovementTest {

    private final Pieces pieces = new Pieces(GreenPieceFixture.pieces);
//
//    @Test
//    @DisplayName("수평 방향으로 이동할 수 있다.")
//    void test1() {
//        //given
//        Point from = new Point(2, 3);
//        Point to = new Point(3, 3);
//        CannonMovement cannonMovement = new CannonMovement();
//
//
//        //when
//        Point result = cannonMovement.move(pieces, from, to);
//
//        //then
//        assertThat(result).isEqualTo(to);
//    }
//
//    @Test
//    @DisplayName("수직 방향으로 이동할 수 있다.")
//    void test2() {
//        //given
//        Point from = new Point(2, 3);
//        Point to = new Point(2, 10);
//        CannonMovement cannonMovement = new CannonMovement();
//
//        //when
//        Point result = cannonMovement.move(pieces, from, to);
//
//        //then
//        assertThat(result).isEqualTo(to);
//    }

    @Test
    @DisplayName("수평, 수직이 아닌 경우 이동할 수 없다.")
    void dont_move_diagonal() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(6, 3);
        CannonMovement cannonMovement = new CannonMovement();

        //when
        //then
        Assertions.assertThatThrownBy(() -> cannonMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자신이 있는 위치로 이동할 수 없다.")
    void dont_move_to_now_point() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 2);
        CannonMovement cannonMovement = new CannonMovement();

        //when
        //then
        Assertions.assertThatThrownBy(() -> cannonMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
