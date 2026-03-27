package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierMoveStrategyTest {

    private final MoveStrategy strategy = new SoldierMoveStrategy();

    @Test
    @DisplayName("병은 좌, 우, 아래로 이동한다.")
    public void moveTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(4, 3);

        pieces.put(from, Piece.hanPieceOf(PieceType.SOLDIER));

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 2), // LEFT
                Position.of(4, 4), // RIGHT
                Position.of(5, 3)  // DOWN
        );
    }

    @Test
    @DisplayName("졸은 좌, 우, 위로 이동한다.")
    void han_moves_correct_directions() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(7, 3);

        pieces.put(from, Piece.choPieceOf(PieceType.SOLDIER));

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(7, 2), // LEFT
                Position.of(7, 4), // RIGHT
                Position.of(6, 3)  // UP
        );
    }
}
