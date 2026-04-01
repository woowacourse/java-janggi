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

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("코끼리는 막힘이 없으면 8방향으로 이동할 수 있다")
    void shouldMoveInAllDirections_whenNoBlockExists() {
        // given
        ElephantMoveStrategy strategy = new ElephantMoveStrategy();
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = Map.of();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(2, 3),
                Position.of(2, 7),
                Position.of(8, 3),
                Position.of(8, 7),
                Position.of(3, 2),
                Position.of(7, 2),
                Position.of(3, 8),
                Position.of(7, 8)
        );
    }

    @Test
    @DisplayName("코끼리는 첫 번째 경유지가 막히면 해당 방향으로 이동할 수 없다")
    void shouldNotMove_whenFirstPathBlocked() {
        // given
        ElephantMoveStrategy strategy = new ElephantMoveStrategy();
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(4, 5), Piece.hanPieceOf(PieceType.SOLDIER)); // UP 막힘

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).doesNotContain(
                Position.of(2, 3),
                Position.of(2, 7)
        );
    }

    @Test
    @DisplayName("코끼리는 두 번째 경유지가 막히면 해당 방향으로 이동할 수 없다")
    void shouldNotMove_whenSecondPathBlocked() {
        // given
        ElephantMoveStrategy strategy = new ElephantMoveStrategy();
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(3, 4), Piece.hanPieceOf(PieceType.SOLDIER)); // UP → LEFT_UP

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).doesNotContain(
                Position.of(2, 3)
        );
    }

    @Test
    @DisplayName("코끼리는 일부 방향만 막히고 나머지 방향으로는 이동할 수 있다")
    void shouldMovePartially_whenSomePathsBlocked() {
        // given
        ElephantMoveStrategy strategy = new ElephantMoveStrategy();
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(4, 5), Piece.hanPieceOf(PieceType.SOLDIER)); // UP 막힘

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).doesNotContain(
                Position.of(2, 3),
                Position.of(2, 7)
        );

        assertThat(result).contains(
                Position.of(8, 3),
                Position.of(8, 7)
        );
    }
}
