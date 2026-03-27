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

class GeneralMoveStrategyTest {

    @Test
    @DisplayName("장군은 8방향으로 한 칸 이동할 수 있다")
    void general_moves_all_directions() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(1, 1), Piece.hanPieceOf(PieceType.GENERAL));

        GeneralMoveStrategy strategy = new GeneralMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 4),
                Position.of(4, 5),
                Position.of(4, 6),
                Position.of(5, 4),
                Position.of(5, 6),
                Position.of(6, 4),
                Position.of(6, 5),
                Position.of(6, 6)
        );
    }

    @Test
    @DisplayName("장군은 상대 장군과 마주보게 되는 위치로 이동할 수 없다")
    void general_cannot_face_opposite() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(7, 5), Piece.hanPieceOf(PieceType.GENERAL));

        GeneralMoveStrategy strategy = new GeneralMoveStrategy();

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(1, 4),
                Position.of(2, 4),
                Position.of(3, 4),
                Position.of(1, 6),
                Position.of(2, 6),
                Position.of(3, 6)
        );
    }
}
