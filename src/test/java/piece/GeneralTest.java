package piece;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.Team;

public class GeneralTest {

    @Test
    @DisplayName("궁은 위로 한 칸 이동할 수 있다.")
    void can_move_up() {
        // given
        Point start = new Point(5, 9);
        Point destination = new Point(5, 8);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 위로 두 칸 이상 이동할 수 없다.")
    void only_move_up_once() {
        // given
        Point start = new Point(5, 9);
        Point destination = new Point(5, 7);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 아래로 한 칸 이동할 수 있다.")
    void can_move_down() {
        // given
        Point start = new Point(5, 9);
        Point destination = new Point(5, 10);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 아래로 두 칸 이상 이동할 수 없다.")
    void only_move_down_once() {
        // given
        Point start = new Point(5, 8);
        Point destination = new Point(5, 10);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 왼쪽으로 한 칸 이동할 수 있다.")
    void can_move_left() {
        // given
        Point start = new Point(5, 9);
        Point destination = new Point(4, 9);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_left_once() {
        // given
        Point start = new Point(5, 9);
        Point destination = new Point(3, 9);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 오른쪽으로 한 칸 이동할 수 있다.")
    void can_move_right() {
        // given
        Point start = new Point(5, 9);
        Point destination = new Point(6, 9);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_right_once() {
        // given
        Point start = new Point(5, 9);
        Point destination = new Point(7, 9);

        General general = new General(Team.CHO, start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 궁성 중앙에 있을 때, 왼쪽 1칸, 위쪽 1칸 대각선 이동이 가능하다.")
    void castle_center_can_move_left_up() {
        // given
        Point destination = new Point(4, 8);
        General general = new General(Team.CHO, new Point(5, 9));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 궁성 중앙에 있을 때, 왼쪽 1칸, 아래쪽 1칸 대각선 이동이 가능하다.")
    void castle_center_can_move_left_down() {
        // given
        Point destination = new Point(4, 10);
        General general = new General(Team.CHO, new Point(5, 9));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 궁성 중앙에 있을 때, 오른쪽 1칸, 위쪽 1칸 대각선 이동이 가능하다.")
    void castle_center_can_move_right_up() {
        // given
        Point destination = new Point(6, 8);
        General general = new General(Team.CHO, new Point(5, 9));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 궁성 중앙에 있을 때, 오른쪽 1칸, 아래쪽 1칸 대각선 이동이 가능하다.")
    void castle_center_can_move_right_down() {
        // given
        Point destination = new Point(6, 10);
        General general = new General(Team.CHO, new Point(5, 9));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 왼쪽 1칸, 위쪽 1칸 궁성 코너에 있을 때, 오른쪽 1칸, 아래쪽 1칸 대각선 이동이 가능하다.")
    void castle_left_up_corner_can_move_right_down() {
        // given
        Point destination = new Point(5, 9);
        General general = new General(Team.CHO, new Point(4, 8));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 왼쪽 1칸, 아래쪽 1칸 궁성 코너에 있을 때, 궁성 중앙으로만 대각선 이동이 가능하다.")
    void castle_left_down_corner_can_move_right_up() {
        // given
        Point destination = new Point(5, 9);
        General general = new General(Team.CHO, new Point(4, 10));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 오른쪽 1칸, 위쪽 1칸 궁성 코너에 있을 때, 궁성 중앙으로만 대각선 이동이 가능하다.")
    void castle_right_up_corner_can_move_left_down() {
        // given
        Point destination = new Point(5, 9);
        General general = new General(Team.CHO, new Point(5, 8));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 오른쪽 1칸, 아래쪽 1칸 궁성 코너에 있을 때, 궁성 중앙으로만 대각선 이동이 가능하다.")
    void castle_right_down_corner_can_move_left_up() {
        // given
        Point destination = new Point(5, 9);
        General general = new General(Team.CHO, new Point(4, 10));
        Pieces pieces = new Pieces(List.of(general));

        // when
        general.move(pieces, destination);

        // then
        Assertions.assertThat(general)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("궁은 왼쪽 1칸, 위쪽 1칸 궁성 코너에 있을 때, 오른쪽 1칸, 아래쪽 1칸 대각선 이동 외에는 대각선 이동이 불가능하다.")
    void castle_left_up_corner_dont_move_except_right_down() {
        // given
        Point destination = new Point(5, 7);
        General general = new General(Team.CHO, new Point(4, 8));
        Pieces pieces = new Pieces(List.of(general));

        // when
        // then
        Assertions.assertThatThrownBy(() -> general.move(pieces, destination))
                        .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로 상 본인의 기물이 있으면 움직일 수 없다.")
    void dont_move_to_exist_piece_in_path() {
        // given
        General general = new General(Team.CHO, new Point(2, 2));
        Pieces pieces = new Pieces(List.of(general, new Horse(Team.CHO, new Point(2, 3))));

        Point destination = new Point(2, 3);

        //when
        //then
        Assertions.assertThatThrownBy(() -> general.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
