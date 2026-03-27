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


class GuardMoveStrategyTest {

    private final MoveStrategy strategy = new GuardMoveStrategy();

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

    @Test
    @DisplayName("아군 기물이 있는 위치로는 이동할 수 없다.")
    void cannot_move_to_ally_position() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.GUARD));

        // 아군 기물 배치
        pieces.put(Position.of(5, 6), Piece.choPieceOf(PieceType.GUARD));

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).doesNotContain(
                Position.of(5, 6)
        );
    }
}
