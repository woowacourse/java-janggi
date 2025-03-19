package domain.piece;

import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
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
        List<Move> move = pawn.calculatePath(new Position(4,1),new Position(4,2));

        // then
        Assertions.assertThat(move).isEqualTo(List.of(Move.RIGHT));
    }

    @DisplayName("졸(병)이 이동할 수 없는 위치라면 예외가 발생한다.")
    @Test
    void test1() {

        //given
        Pawn pawn = new Pawn(Team.RED);

        // when & then
        Assertions.assertThatThrownBy(() -> pawn.calculatePath(new Position(4,1),new Position(4,3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}