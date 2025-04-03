package piece;

import static org.assertj.core.api.Assertions.assertThat;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.Team;

class HorseTest {

    @Test
    @DisplayName("위쪽 2칸, 왼쪽 1칸 이동할 수 있다.")
    void can_move_up_up_left() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(2, 1);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("위쪽 2칸, 오른쪽 1칸 이동할 수 있다.")
    void can_move_up_up_right() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(4, 1);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("왼쪽 2칸, 위쪽 1칸 이동할 수 있다.")
    void can_move_left_left_up() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(1, 2);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("왼쪽 2칸, 아래쪽 1칸 이동할 수 있다.")
    void can_move_left_left_down() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(1, 4);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("아래쪽 2칸, 왼쪽 1칸 이동할 수 있다.")
    void can_move_down_down_left() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(2, 5);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("아래쪽 2칸, 오른쪽 1칸 이동할 수 있다.")
    void can_move_down_down_right() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(4, 5);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("오른쪽 2칸, 위쪽 1칸 이동할 수 있다.")
    void can_move_right_right_up() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(5, 2);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("오른쪽 2칸, 아래쪽 1칸 이동할 수 있다.")
    void can_move_right_right_down() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(5, 4);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        horse.move(pieces, destination);

        //then
        assertThat(horse).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("직선 1칸, 대각선 1칸을 제외하고 움직일 수 없다.")
    void only_move_one_straight_and_one_diagonal() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(7, 5);

        Horse horse = new Horse(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(horse));

        //when
        //then
        Assertions.assertThatThrownBy(() -> horse.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 움직일 수 없다.")
    void dont_move_to_exist_piece_in_path() {
        // given
        Horse horse = new Horse(Team.CHO, new Point(2, 2));
        Pieces pieces = new Pieces(List.of(horse, new Horse(Team.CHO, new Point(2, 3))));
        Point destination = new Point(1, 4);

        //when
        //then
        Assertions.assertThatThrownBy(() -> horse.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
