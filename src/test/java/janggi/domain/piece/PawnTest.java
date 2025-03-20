package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.HanPoint;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PawnTest {

    @DisplayName("폰은 상좌우 한칸 움직일 수 있다")
    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 1",
            "1, 2, 1, 1",
            "1, 2, 1, 3",
    })
    void move(int x1, int y1, int x2, int y2) {
        //givenR
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());
        Pawn pawn = new Pawn();

        //when
        boolean result = pawn.isMovable(janggiBoard, new HanPoint(x1, y1), new HanPoint(x2, y2));

        //then
        assertThat(result).isTrue();
    }
}
