import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("9(가로) x 10(세로) 크기의 장기판을 사용한다.")
    void isInboardTest() {
        Board board = new Board(List.of());
        assertThat(board.isInboard(new Position(9, 0))).isFalse();
        assertThat(board.isInboard(new Position(0, 10))).isFalse();
    }
}
