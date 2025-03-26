package move;

import static org.assertj.core.api.Assertions.assertThat;

import direction.Point;
import fixture.ChoPiecesFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Elephant;
import piece.Pieces;

class ElephantTest {

    private final Pieces pieces = new Pieces(ChoPiecesFixture.pieces);

    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void can_move_up_up_left_up_left() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, -1);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("위쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void can_move_up_up_right_up_right() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, -1);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void test3() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(-1, 0);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void can_move_left_left_down_left_down() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(-1, 4);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void can_move_down_down_left_down_left() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, 5);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void can_move_down_down_right_down_right() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, 5);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void can_move_right_right_up_right_up() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(5, 0);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void can_move_right_right_down_right_down() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(5, 4);
        Elephant elephant = new Elephant("e", from);

        //when
        elephant.move(pieces, to);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("직선 1칸, 대각선 2칸을 제외하고 움직일 수 없다.")
    void only_can_move_one_straight_and_twice_diagonal() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(8, 3);
        Elephant elephant = new Elephant("e", from);

        //when
        //then
        Assertions.assertThatThrownBy(() -> elephant.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
