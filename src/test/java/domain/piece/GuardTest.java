package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Destinations;
import domain.game.Position;
import domain.game.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardTest {
    @Test
    @DisplayName("사는 궁성 내부에서만 1칸 이동할 수 있다")
    void move() {
        Position current = Position.of(3, 1);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createGuard(Side.CHO),
                Position.of(3, 2), PieceFactory.createChariot(Side.CHO)
        );
        Board board = new Board(pieces);

        Destinations movable = board.findDestinations(current);

        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 1), Position.of(3, 0)
        );
    }

    @Test
    @DisplayName("사는 궁성 중앙에서 대각선 1칸 이동할 수 있다")
    void moveDiagonal() {
        Position current = Position.of(4, 1);
        Board board = new Board(Map.of(current, PieceFactory.createGuard(Side.CHO)));

        Destinations movable = board.findDestinations(current);

        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 0), Position.of(3, 1), Position.of(5, 1), Position.of(4, 2),
                Position.of(3, 0), Position.of(5, 0), Position.of(3, 2), Position.of(5, 2)
        );
    }
}
