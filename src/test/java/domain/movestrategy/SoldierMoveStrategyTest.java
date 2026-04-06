package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.player.Team;
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
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(4, 3);

        pieces.put(from, Piece.of(PieceType.SOLDIER, Team.HAN));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

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
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(7, 3);

        pieces.put(from, Piece.of(PieceType.SOLDIER, Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(7, 2), // LEFT
                Position.of(7, 4), // RIGHT
                Position.of(6, 3)  // UP
        );
    }
}
