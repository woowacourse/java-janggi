package piece;

import static org.assertj.core.api.Assertions.assertThat;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.Team;

class ElephantTest {

    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 2칸 이동할 수 있다.")
    void can_move_up_up_up_left_left() {
        //given
        Point start = new Point(3, 5);
        Point destination = new Point(1, 2);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("위쪽 3칸, 오른쪽 2칸 이동할 수 있다.")
    void can_move_up_up_up_right_right() {
        //given
        Point start = new Point(2, 4);
        Point destination = new Point(4, 1);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 2칸 이동할 수 있다.")
    void can_move_left_left_left_up_up() {
        //given
        Point start = new Point(4, 4);
        Point destination = new Point(1, 2);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("왼쪽 3칸, 아래쪽 2칸 이동할 수 있다.")
    void can_move_left_left_left_down_down() {
        //given
        Point start = new Point(4, 4);
        Point destination = new Point(1, 6);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("아래쪽 3칸, 왼쪽 대각선 2칸 이동할 수 있다.")
    void can_move_down_down_down_left_left() {
        //given
        Point start = new Point(3, 3);
        Point destination = new Point(1, 6);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("아래쪽 3칸, 오른쪽 2칸 이동할 수 있다.")
    void can_move_down_down_down_right_right() {
        //given
        Point start = new Point(2, 2);
        Point destination = new Point(4, 5);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("오른쪽 3칸, 위쪽 2칸 이동할 수 있다.")
    void can_move_right_right_right_up_up() {
        //given
        Point start = new Point(2, 2);
        Point destination = new Point(5, 0);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("오른쪽 3칸, 아래쪽 2칸으로 이동할 수 있다.")
    void can_move_right_right_right_down_down() {
        //given
        Point start = new Point(2, 2);
        Point destination = new Point(5, 4);

        Elephant elephant = new Elephant(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(elephant));

        //when
        elephant.move(pieces, destination);

        //then
        assertThat(elephant).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("직선 1칸, 대각선 2칸을 제외하고 움직일 수 없다.")
    void only_can_move_one_straight_and_twice_diagonal() {
        //given
        Elephant elephant = new Elephant(Team.CHO, new Point(2, 2));
        Pieces pieces = new Pieces(List.of(elephant, new Horse(Team.CHO, new Point(2, 3))));
        Point destination = new Point(8, 3);

        //when
        //then
        Assertions.assertThatThrownBy(() -> elephant.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로 상 기물이 있으면 움직일 수 없다.")
    void dont_move_to_exist_piece_in_path() {
        // given
        Elephant elephant = new Elephant(Team.CHO, new Point(2, 2));
        Pieces pieces = new Pieces(List.of(elephant, new Horse(Team.CHO, new Point(2, 3))));
        Point destination = new Point(4, 5);

        //when
        //then
        Assertions.assertThatThrownBy(() -> elephant.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
