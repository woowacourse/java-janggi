import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceMoveTest {

    private Board board = new Board(List.of());

    @Test
    @DisplayName("장기판 밖으로 나갈 경우 예외를 반환한다.")
    void pawnMoveTest() {
        Piece p = new Palace(0,0);
        assertThatThrownBy(() -> p.move(board, 0, -1))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
