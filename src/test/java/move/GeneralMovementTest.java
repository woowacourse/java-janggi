package move;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import fixture.GreenPieceFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import team.Team;

public class GeneralMovementTest {

    Pieces pieces = new Pieces(GreenPieceFixture.pieces);

    @Test
    @DisplayName("궁은 위로 한 칸 이동할 수 있다.")
    void can_move_up() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y-1);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 위로 두 칸 이상 이동할 수 없다.")
    void only_move_up_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y-2);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 아래로 한 칸 이동할 수 있다.")
    void can_move_down() {
        // given
        int x = 0;
        int y = 1;
        Point from = new Point(x, y);
        Point to = new Point(x, y+1);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 아래로 두 칸 이상 이동할 수 없다.")
    void only_move_down_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y+2);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 왼쪽으로 한 칸 이동할 수 있다.")
    void can_move_left() {
        // given
        int x = 1;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x-1, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_left_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x-2, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁은 오른쪽으로 한 칸 이동할 수 있다.")
    void can_move_right() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x+1, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_right_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x+2, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
