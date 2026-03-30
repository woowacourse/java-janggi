package view;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardRendererTest {

    @Test
    @DisplayName("보드 하단에 x축 좌표를 함께 출력한다.")
    void renderColumnLabelsAtBottom() {
        Board board = new Board(new HashMap<>());

        String renderedBoard = new BoardRenderer().render(board);
        String[] lines = renderedBoard.split(System.lineSeparator());

        assertThat(lines[lines.length - 1]).isEqualTo("   1   2   3   4   5   6   7   8   9");
    }
}
