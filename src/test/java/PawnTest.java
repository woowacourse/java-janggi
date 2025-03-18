import static org.assertj.core.api.Assertions.assertThat;

import domain.Pawn;
import domain.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {

    @Test
    @DisplayName("폰이 이동 가능한 위치를 리스트로 반환한다")
    void test1() {
        //given
        final Position position = new Position(0, 0);
        final List<Position> pos = List.of(new Position(-1,0), new Position(1,0), new Position(0,-1), new Position(0,1));

        //when
        final Pawn pawn = new Pawn();
        final List<Position> availablePositions = pawn.getAvailablePositions(position);

        //then
        assertThat(availablePositions).containsAll(pos);
    }
}
