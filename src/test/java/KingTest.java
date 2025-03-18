import domain.JanggiBoard;
import domain.piece.King;
import domain.piece.Piece;
import domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingTest {

    @DisplayName("킹은 앞으로 한 칸 이동할 수 있다")
    @Test
    void test() {
        JanggiBoard janggiBoard = new JanggiBoard();
        Piece[][] board = janggiBoard.getBoard();
        King king = new King(Team.BLUE);
        board[4][1] = king;

        boolean moveResult = janggiBoard.canMove(0, 0, 0, 1);
        Assertions.assertThat(moveResult).isEqualTo(true);
    }

    @DisplayName("앞에 아군의 말이 있을 시 이동할 수 없다.")
    @Test
    void test1() {
        JanggiBoard janggiBoard = new JanggiBoard();
        Piece[][] board = janggiBoard.getBoard();
        King king = new King(Team.BLUE);
        board[4][1] = king;
        board[4][2] = king;

        boolean moveResult = janggiBoard.canMove(0, 0, 4, 2);
        Assertions.assertThat(moveResult).isEqualTo(false);
    }
}
