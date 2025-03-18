import domain.JanggiBoard;
import domain.Piece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {

    @DisplayName("장기판은 9*10의 크기이다.")
    @Test
    void test() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard();
        Piece[][] board = janggiBoard.getBoard();
        int column = board[0].length;
        int row = board.length;
        //when &then
        Assertions.assertThat(column).isEqualTo(10);
        Assertions.assertThat(row).isEqualTo(9);
    }
}
