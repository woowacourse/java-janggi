import domain.King;
import domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingTest {

    @DisplayName("킹은 앞으로 한 칸 이동할 수 있다")
    @Test
    void test() {
        King king = new King(Team.BLUE);
        boolean moveResult = king.canMove(0, 0, 1, 0);

        Assertions.assertThat(moveResult).isEqualTo(true);
    }
}
