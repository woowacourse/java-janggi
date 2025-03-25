package move;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Cannon;
import piece.Guard;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Soldier;

class CannonTest {

    @Test
    @DisplayName("수평 방향으로 이동할 수 있다.")
    void test1() {
        //given
        Point from = new Point(2, 3);
        Point to = new Point(3, 3);
        Cannon cannon = new Cannon(PieceType.GREEN_CANNON, from);

        //when

        //then
        assertThatCode(() -> cannon.validateDestination(from, to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수직 방향으로 이동할 수 있다.")
    void test2() {
        //given
        Point from = new Point(2, 3);
        Point to = new Point(2, 10);
        Cannon cannon = new Cannon(PieceType.GREEN_CANNON, from);

        //when

        //then
        assertThatCode(() -> cannon.validateDestination(from, to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수평, 수직이 아닌 경우 이동할 수 없다.")
    void test9() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(6, 3);
        Cannon cannon = new Cannon(PieceType.GREEN_CANNON, from);

        //when
        //then
        assertThatThrownBy(() -> cannon.validateDestination(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자신이 있는 위치로 이동할 수 없다.")
    void test10() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 2);
        Cannon cannon = new Cannon(PieceType.GREEN_CANNON, from);

        //when
        //then
        assertThatThrownBy(() -> cannon.validateDestination(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물 하나를 넘으면 이동할 수 있다.")
    void test11() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 8);
        Cannon cannon = new Cannon(PieceType.GREEN_CANNON, from);
        Pieces pieces = new Pieces(List.of(
                cannon,
                new Guard(PieceType.GREEN_GUARD, new Point(2, 4))));

        //when
        //then
        assertThatCode(() -> cannon.checkPaths(pieces, from, to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기물을 두 개 이상 넘을 수 없다.")
    void test12() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 8);
        Cannon cannon = new Cannon(PieceType.GREEN_CANNON, from);
        Pieces pieces = new Pieces(List.of(
                cannon,
                new Guard(PieceType.GREEN_GUARD, new Point(2, 4)),
                new Guard(PieceType.GREEN_GUARD, new Point(2, 5))));

        //when
        //then
        assertThatThrownBy(() -> cannon.checkPaths(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포를 뛰어넘을 수 없다.")
    void test13() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 8);
        Cannon cannon = new Cannon(PieceType.GREEN_CANNON, from);
        Pieces pieces = new Pieces(List.of(
                cannon,
                new Cannon(PieceType.GREEN_CANNON, new Point(2, 4))));

        //when
        //then
        assertThatThrownBy(() -> cannon.checkPaths(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
