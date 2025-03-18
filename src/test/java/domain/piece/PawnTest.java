package domain.piece;

import static org.junit.jupiter.api.Assertions.*;

import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PawnTest {

    @DisplayName("졸(병)은 오른쪽으로 이동할 수 있다.")
    @Test
    void test() {

        //given
        Pawn pawn = new Pawn(Team.RED);

        //when
        List<Move> move = pawn.calculatePath(4,1,4,2);

        // then
        Assertions.assertThat(move).isEqualTo(List.of(Move.RIGHT));
    }
}