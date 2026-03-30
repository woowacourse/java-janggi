package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardTest {
    @Test
    @DisplayName("사는 상하좌우 1칸 이동하며 아군 기물이 있으면 이동할 수 없다")
    void move() {
        Position current = Position.of(3, 1);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createGuard(Side.CHO),
                Position.of(3, 2), PieceFactory.createChariot(Side.CHO)
        );
        Board board = new Board(pieces);

        MovablePositions movable = board.findMovablePositions(current);

        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 1), Position.of(2, 1), Position.of(3,0)
        );
    }
}
