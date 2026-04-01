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

class SoldierMoveStrategyTest {

    @Test
    @DisplayName("초나라의 졸은 상, 좌, 우로 이동 가능하다")
    public void moveTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(4, 3);

        pieces.put(from, Piece.choPieceOf(PieceType.SOLDIER));

        List<Position> expected = List.of(
                Position.of(4, 2),
                Position.of(4, 4),
                Position.of(3, 3)
        );

        // when
        List<Position> movable = new SoldierMoveStrategy().calculateMovablePositions(from, pieces);

        // then
        assertThat(movable).containsAll(expected);
    }

    @Test
    @DisplayName("한나라의 병은 하, 좌, 우로 이동 가능하다")
    void hanSoldierMoveTest() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(4, 3);

        pieces.put(from, Piece.hanPieceOf(PieceType.SOLDIER));

        List<Position> expected = List.of(
                Position.of(4, 2),
                Position.of(4, 4),
                Position.of(5, 3)
        );

        // when
        List<Position> movable = new SoldierMoveStrategy().calculateMovablePositions(from, pieces);

        // then
        assertThat(movable).containsAll(expected);
    }
}
