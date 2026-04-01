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

class HorseMoveStrategyTest {

    @Test
    @DisplayName("말은 경유지가 막히지 않으면 8방향 이동 가능")
    void horse_moves_all_when_not_blocked() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.HORSE));

        HorseMoveStrategy strategy = new HorseMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(3, 4),
                Position.of(3, 6),
                Position.of(4, 7),
                Position.of(6, 7),
                Position.of(7, 4),
                Position.of(7, 6),
                Position.of(4, 3),
                Position.of(6, 3)
        );
    }

    @Test
    @DisplayName("말은 경유지가 막히면 해당 방향으로 이동 불가")
    void horse_blocked_by_path() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.HORSE));

        // UP 경유지 막기
        pieces.put(Position.of(4, 5), Piece.choPieceOf(PieceType.SOLDIER));

        HorseMoveStrategy strategy = new HorseMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        // (-2,-1), (-2,1) 둘 다 막힘
        assertThat(result).doesNotContain(
                Position.of(3, 4),
                Position.of(3, 6)
        );

        // 나머지는 가능
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 7),
                Position.of(6, 7),
                Position.of(7, 4),
                Position.of(7, 6),
                Position.of(4, 3),
                Position.of(6, 3)
        );
    }

    @Test
    @DisplayName("여러 경유지가 막히면 해당 방향들만 제외된다")
    void horse_multiple_blocks() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.HORSE));

        // UP, RIGHT 막기
        pieces.put(Position.of(4, 5), Piece.choPieceOf(PieceType.SOLDIER)); // UP
        pieces.put(Position.of(5, 6), Piece.choPieceOf(PieceType.SOLDIER)); // RIGHT

        HorseMoveStrategy strategy = new HorseMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).doesNotContain(
                Position.of(3, 4),
                Position.of(3, 6),
                Position.of(4, 7),
                Position.of(6, 7)
        );
    }
}
