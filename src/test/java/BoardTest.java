import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.Point;
import model.Board;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 보드에_특정_위치에_있는_기물을_움직일_수_있다() {
        Board board = new Board();
        board.putPiece(new Point(1, 1), "마");

        board.move(new Point(1, 1), new Point(1,4));

        assertThat(board.isExist(new Point(1, 4))).isTrue();
    }

    @Test
    void 기물이_존재하지_않는_위치를_움직이려_하면_예외가_발생한다() {
        Board board = new Board();

        assertThatThrownBy(() -> board.move(new Point(1, 1), new Point(1, 4)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
