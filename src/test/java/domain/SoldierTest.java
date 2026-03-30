package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {
    @Test
    @DisplayName("초나라 졸은 상/좌/우 이동이 가능하고 한나라 졸은 하/좌/우 이동이 가능하다")
    void moveBySide() {
        Position choPos = Position.of(4, 3);
        Position hanPos = Position.of(4, 6);
        Board board = new Board(Map.of(
                choPos, PieceFactory.createSoldier(Side.CHO),
                hanPos, PieceFactory.createSoldier(Side.HAN)
        ));

        assertThat(board.findMovablePositions(choPos).getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 4), Position.of(3, 3), Position.of(5, 3)
        );
        assertThat(board.findMovablePositions(hanPos).getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 5), Position.of(3, 6), Position.of(5, 6)
        );
    }
}
