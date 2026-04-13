package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.movement.Destinations;
import domain.common.Position;
import domain.common.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralTest {
    @Test
    @DisplayName("궁은 궁성 내부에서 상하좌우와 대각선으로 1칸 이동할 수 있고, 아군 위치는 이동할 수 없다")
    void move() {
        Position current = Position.of(4, 1);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createGeneral(Side.CHO),
                Position.of(4, 2), PieceFactory.createGuard(Side.CHO)
        );
        Board board = new Board(pieces);

        Destinations movable = board.findDestinations(current);

        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 0), Position.of(3, 1), Position.of(5, 1),
                Position.of(3, 0), Position.of(5, 0), Position.of(3, 2), Position.of(5, 2)
        );
    }
}
