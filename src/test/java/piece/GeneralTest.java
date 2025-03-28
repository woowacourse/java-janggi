package piece;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.Player;
import team.Team;

public class GeneralTest {

    @Test
    @DisplayName("궁은 위로 한 칸 이동할 수 있다.")
    void can_move_up() {
        // given
        Point start = new Point(2, 2);
        Point destination = new Point(2, 1);

        General general = new General(start);
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
        Point start = new Point(2, 4);
        Point destination = new Point(2, 2);

        General general = new General(start);
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
        Point start = new Point(2, 2);
        Point destination = new Point(2, 3);

        General general = new General(start);
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
        Point start = new Point(2, 2);
        Point destination = new Point(2, 4);

        General general = new General(start);
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
        Point start = new Point(2, 2);
        Point destination = new Point(1, 2);

        General general = new General(start);
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
        Point start = new Point(3, 2);
        Point destination = new Point(1, 2);

        General general = new General(start);
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
        Point start = new Point(2, 2);
        Point destination = new Point(3, 2);

        General general = new General(start);
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
        Point start = new Point(2, 2);
        Point destination = new Point(4, 2);

        General general = new General(start);
        Pieces pieces = new Pieces(List.of(general));

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로 상 본인의 기물이 있으면 움직일 수 없다.")
    void elephant_dont_move_to_exist_piece_in_path() {
        // given
        General general = new General(new Point(2, 2));
        Player player = new Player(List.of(general, new Horse(new Point(2, 3))), Team.HAN);

        Point destination = new Point(2, 3);

        //when
        //then
        Assertions.assertThatThrownBy(() -> general.move(new Pieces(player.getPieces()), destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
