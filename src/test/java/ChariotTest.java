import domain.Team;
import domain.piece.Chariot;
import domain.piece.Horse;
import domain.piece.Move;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotTest {

    @DisplayName("차의 이동 경로를 계산할 수 있다")
    @Test
    void test1() {
        Chariot horse = new Chariot(Team.RED);

        List<Move> moves = horse.calculatePath(0, 0, 0, 6);
        List<Move> expected = List.of(Move.FRONT,Move.FRONT,Move.FRONT,Move.FRONT,Move.FRONT,Move.FRONT);

        Assertions.assertThat(moves).isEqualTo(expected);
    }
}
