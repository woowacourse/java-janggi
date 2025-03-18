import domain.Team;
import domain.piece.Horse;
import domain.piece.Move;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseTest {

    @DisplayName("마의 이동 경로를 계산할 수 있다")
    @Test
    void test1() {
        Horse horse = new Horse(Team.RED);

        List<Move> moves = horse.calculatePath(4, 4, 3, 6);
        List<Move> expected = List.of(Move.RIGHT, Move.FRONT_RIGHT);

        Assertions.assertThat(moves).isEqualTo(expected);
    }
}
