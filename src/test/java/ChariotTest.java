import domain.Team;
import domain.piece.Chariot;
import domain.piece.Horse;
import domain.piece.Move;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotTest {

    @DisplayName("차의 이동 경로를 계산할 수 있다")
    @Test
    void test1() {
        Chariot chariot = new Chariot(Team.RED);

        List<Move> moves1 = chariot.calculatePath(0, 0, 0, 6);
        List<Move> moves2 = chariot.calculatePath(0, 0, 7, 0);
        List<Move> moves3 = chariot.calculatePath(6, 0, 1, 0);
        List<Move> expected1 = List.of(Move.RIGHT,Move.RIGHT,Move.RIGHT,Move.RIGHT,Move.RIGHT,Move.RIGHT);
        List<Move> expected2 = List.of(Move.BACK,Move.BACK,Move.BACK,Move.BACK,Move.BACK,Move.BACK,Move.BACK);
        List<Move> expected3 = List.of(Move.FRONT,Move.FRONT,Move.FRONT,Move.FRONT,Move.FRONT);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moves1).isEqualTo(expected1);
            softAssertions.assertThat(moves2).isEqualTo(expected2);
            softAssertions.assertThat(moves3).isEqualTo(expected3);

        });
    }
}
