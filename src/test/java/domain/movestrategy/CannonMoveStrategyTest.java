package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CannonMoveStrategyTest {

    @Test
    void 포는_장애물이_없으면_이동_불가() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(5, 5);

        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void 포는_하나를_넘고_그_이후_이동_가능() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(5, 5);

        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));

        // 장애물
        pieces.put(Position.of(6, 5), Piece.choPieceOf(PieceType.SOLDIER));

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).contains(
                Position.of(7, 5),
                Position.of(8, 5),
                Position.of(9, 5),
                Position.of(10, 5)
        );
    }

    @Test
    void 포는_두번째_기물까지_이동_가능() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(5, 5);

        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));

        // 첫 번째 장애물
        pieces.put(Position.of(6, 5), Piece.choPieceOf(PieceType.SOLDIER));

        // 두 번째 기물
        pieces.put(Position.of(8, 5), Piece.hanPieceOf(PieceType.SOLDIER));

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).contains(
                Position.of(7, 5),
                Position.of(8, 5) // 여기까지 가능
        );

        assertThat(result).doesNotContain(Position.of(9, 5));
    }

    @Test
    void 포는_포를_넘을_수_없다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(5, 5);

        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));

        // 첫 번째 기물이 포
        pieces.put(Position.of(6, 5), Piece.choPieceOf(PieceType.CANNON));

        CannonMoveStrategy strategy = new CannonMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).isEmpty();
    }
}
