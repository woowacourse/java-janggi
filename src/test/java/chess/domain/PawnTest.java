package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {

    @DisplayName("폰은 상좌우 한칸 움직일 수 있다")
    @Test
    void move() {
        //givenR
        Map<int[], Piece> pieces = Map.of(
                new int[] {0,1}, new HanPawn()
        );
        HanPawn hanPawn = new HanPawn();

        //when
        boolean result = hanPawn.isMovable(pieces, new int[]{0,1}, new int[]{1,1});

        //then
        assertThat(result).isTrue();
    }
}
