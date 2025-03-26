package move;

import static org.assertj.core.api.Assertions.assertThat;

import direction.Point;
import fixture.ChoPiecesFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Horse;
import piece.Pieces;

class HorseTest {

    Pieces pieces = new Pieces(ChoPiecesFixture.pieces);

    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_up_up_left() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(1, 0);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("위쪽 1칸, 오른쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_up_up_right() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(3, 0);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_left_left_up() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, 1);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 아래쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_left_left_down() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, 3);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 왼쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_down_down_left() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(1, 4);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 오른쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_down_down_right() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(3, 4);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 위쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_right_right_up() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, 1);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 아래쪽 대각선 1칸으로 이동할 수 있다.")
    void can_move_right_right_down() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, 3);
        Horse horse = new Horse("h", from);

        //when
        horse.move(pieces, to);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("직선 1칸, 대각선 1칸을 제외하고 움직일 수 없다.")
    void only_move_one_straight_and_one_diagonal() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(6, 3);
        Horse horse = new Horse("h", from);

        //when
        //then
        Assertions.assertThatThrownBy(() -> horse.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
