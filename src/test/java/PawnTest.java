import static org.assertj.core.api.Assertions.assertThat;

import domain.Pawn;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {


    @Test
    @DisplayName("폰이 이동 가능한 위치를 리스트로 반환한다")
    void test1() {
        //given
        final int cR = 0;
        final int cC = 0;
        final List<int[]> pos = List.of(new int[]{-1, 0}, new int[]{1, 0}, new int[]{0, -1}, new int[]{0, 1});

        //when
        final Pawn pawn = new Pawn(cR, cC);
        final List<int[]> availablePositions = pawn.getAvailablePositions();

        //then
        assertThat(availablePositions).containsAll(pos);

    }
}
