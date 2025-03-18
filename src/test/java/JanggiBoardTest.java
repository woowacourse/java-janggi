import domain.JanggiBoard;
import domain.piece.Piece;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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

    @DisplayName("장기말은 앞으로 이동할 수 있다.")
    @Test
    void test2() {
        JanggiBoard janggiBoard = new JanggiBoard();

        boolean moveResult1 = janggiBoard.canMove(3, 0, 3, 1);
        boolean moveResult2 = janggiBoard.canMove(3, 4, 4, 4);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(true);
            softAssertions.assertThat(moveResult2).isEqualTo(true);

        });
    }
    @DisplayName("앞에 아군의 말이 있을 시 이동할 수 없다.")
    @Test
    void test1() {
        JanggiBoard janggiBoard = new JanggiBoard();

        boolean moveResult1 = janggiBoard.canMove(0, 0, 0, 1);
        boolean moveResult3 = janggiBoard.canMove(0, 8, 3,8 );
        boolean moveResult2 = janggiBoard.canMove(3, 0, 3, 1);
        boolean moveResult4 = janggiBoard.canMove(3, 4, 4, 4);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(false);
            softAssertions.assertThat(moveResult3).isEqualTo(false);
            softAssertions.assertThat(moveResult2).isEqualTo(true);
            softAssertions.assertThat(moveResult4).isEqualTo(true);

        });
    }
}
