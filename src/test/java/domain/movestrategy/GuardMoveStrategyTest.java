package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class GuardMoveStrategyTest {

    private final GuardMoveStrategy strategy = new GuardMoveStrategy();

    @Test
    @DisplayName("사는 8방향 한 칸 이동이 가능하다")
    void move_all_directions() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.GUARD));

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 5),
                Position.of(6, 5),
                Position.of(5, 4),
                Position.of(5, 6),
                Position.of(4, 4),
                Position.of(4, 6),
                Position.of(6, 4),
                Position.of(6, 6)
        );
    }
}
