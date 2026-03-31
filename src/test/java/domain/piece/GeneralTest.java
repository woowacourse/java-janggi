package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.MovablePositions;
import domain.Position;
import domain.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralTest {
    @Test
    @DisplayName("궁은 상하좌우 1칸 이동하며, 범위를 벗어나거나 아군이 있으면 이동할 수 없다")
    void move() {
        // (4,1)에 궁, (4,2)에 아군 사 배치 -> (4,2) 이동 불가, (4,0), (3,1), (5,1) 이동 가능
        Position current = Position.of(4, 1);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createGeneral(Side.CHO),
                Position.of(4, 2), PieceFactory.createGuard(Side.CHO)
        );
        Board board = new Board(pieces);

        MovablePositions movable = board.findMovablePositions(current);

        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 0), Position.of(3, 1), Position.of(5, 1)
        );
    }
}
