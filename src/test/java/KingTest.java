import domain.JanggiBoard;
import domain.Team;
import domain.piece.King;
import domain.piece.Move;
import domain.piece.Piece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingTest {

    @DisplayName("킹은 오른쪽으로 이동할 수 있다.")
    @Test
    void test() {

        //given
        King king = new King(Team.RED);

        //when
        List<Move> move = king.calculatePath(4,1,4,2);

        // then
        Assertions.assertThat(move).isEqualTo(List.of(Move.RIGHT));
    }
}
