import domain.JanggiBoard;
import domain.Position;
import domain.piece.Move;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {

    @DisplayName("장기말은 앞으로 이동할 수 있다.")
    @Test
    void test2() {
        JanggiBoard janggiBoard = new JanggiBoard();

        boolean moveResult1 = janggiBoard.canMove(new Position(4, 1), Move.FRONT);
        boolean moveResult2 = janggiBoard.canMove(new Position(4, 5), Move.FRONT);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(true);
            softAssertions.assertThat(moveResult2).isEqualTo(true);

        });
    }

    @DisplayName("앞에 아군의 말이 있을 시 이동할 수 없다.")
    @Test
    void test3() {
        JanggiBoard janggiBoard = new JanggiBoard();

        boolean moveResult1 = janggiBoard.canMove(new Position(1, 1), Move.RIGHT);
        boolean moveResult3 = janggiBoard.canMove(new Position(1, 2), Move.LEFT);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(false);
            softAssertions.assertThat(moveResult3).isEqualTo(false);
        });
    }
}
