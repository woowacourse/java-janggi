package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.HanPoint;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SoldierTest {

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
        Soldier soldier = new Soldier();

        //when
        boolean result = soldier.isMovable(janggiBoard, new HanPoint(x1, y1), new HanPoint(x2, y2));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void test4() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(1, 2), new Soldier(), Dynasty.CHU)
        ));
        Piece pawn = new Soldier();

        // when
        boolean isMovable = pawn.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(1, 2));

        // then
        assertThat(isMovable)
                .isTrue();
    }
}
