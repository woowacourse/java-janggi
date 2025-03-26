package move;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import fixture.ChoPiecesFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.General;
import piece.Pieces;

public class GeneralTest {

    Pieces pieces = new Pieces(ChoPiecesFixture.pieces);

    @Test
    @DisplayName("궁은 위로 한 칸 이동할 수 있다.")
    void can_move_up() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 1);
        General general = new General("g", from);

        // when
        general.move(pieces, to);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 위로 두 칸 이상 이동할 수 없다.")
    void only_move_up_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 2);
        General general = new General("g", from);

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 아래로 한 칸 이동할 수 있다.")
    void can_move_down() {
        // given
        int x = 0;
        int y = 1;
        Point from = new Point(x, y);
        Point to = new Point(x, y + 1);
        General general = new General("g", from);

        // when
        general.move(pieces, to);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 아래로 두 칸 이상 이동할 수 없다.")
    void only_move_down_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y + 2);
        General general = new General("g", from);

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 왼쪽으로 한 칸 이동할 수 있다.")
    void can_move_left() {
        // given
        int x = 1;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 1, y);
        General general = new General("g", from);

        // when
        general.move(pieces, to);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_left_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 2, y);
        General general = new General("g", from);

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 오른쪽으로 한 칸 이동할 수 있다.")
    void can_move_right() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 1, y);
        General general = new General("g", from);

        // when
        general.move(pieces, to);

        // then
        assertThat(general).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_right_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 2, y);
        General general = new General("g", from);

        // when
        // then
        assertThatThrownBy(() -> general.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
