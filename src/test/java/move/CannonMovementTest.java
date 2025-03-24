package move;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;

class CannonMovementTest {

    @Test
    @DisplayName("수평 방향으로 이동할 수 있다.")
    void test1() {
        //given
        Point from = new Point(2, 3);
        Point to = new Point(3, 3);
        CannonMovement cannonMovement = new CannonMovement();

        //when

        //then
        assertThatCode(() -> cannonMovement.validateDestination(from, to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수직 방향으로 이동할 수 있다.")
    void test2() {
        //given
        Point from = new Point(2, 3);
        Point to = new Point(2, 10);
        CannonMovement cannonMovement = new CannonMovement();

        //when

        //then
        assertThatCode(() -> cannonMovement.validateDestination(from, to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수평, 수직이 아닌 경우 이동할 수 없다.")
    void test9() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(6, 3);
        CannonMovement cannonMovement = new CannonMovement();

        //when
        //then
        assertThatThrownBy(() -> cannonMovement.validateDestination(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자신이 있는 위치로 이동할 수 없다.")
    void test10() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 2);
        CannonMovement cannonMovement = new CannonMovement();

        //when
        //then
        assertThatThrownBy(() -> cannonMovement.validateDestination(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물 하나를 넘으면 이동할 수 있다.")
    void test11() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 8);
        CannonMovement cannonMovement = new CannonMovement();
        Pieces pieces = new Pieces(List.of(
                new Piece("n", new Point(2, 2), cannonMovement),
                new Piece("r", new Point(2, 4), new GuardMovement())
        ));

        //when
        //then
        assertThatCode(() -> cannonMovement.checkPaths(pieces, from, to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기물을 두 개 이상 넘을 수 없다.")
    void test12() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 8);
        CannonMovement cannonMovement = new CannonMovement();
        Pieces pieces = new Pieces(List.of(
                new Piece("n", new Point(2, 2), cannonMovement),
                new Piece("r", new Point(2, 4), new GuardMovement()),
                new Piece("r", new Point(2, 5), new GuardMovement())
        ));

        //when
        //then
        assertThatThrownBy(() -> cannonMovement.checkPaths(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포를 뛰어넘을 수 없다.")
    void test13() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 8);
        CannonMovement cannonMovement = new CannonMovement();
        Pieces pieces = new Pieces(List.of(
                new Piece("n", new Point(2, 2), cannonMovement),
                new Piece("n", new Point(2, 4), cannonMovement)
        ));

        //when
        //then
        assertThatThrownBy(() -> cannonMovement.checkPaths(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
