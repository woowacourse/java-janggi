package domain.movestrategy;

import static domain.piece.PieceType.GUARD;
import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceStatus;
import domain.piece.Position;
import domain.player.Team;
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
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(GUARD, new GuardMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

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
    @DisplayName("아군 기물이 있는 위치로는 이동할 수 없다.")
    void cannot_move_to_ally_position() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(GUARD, new GuardMoveStrategy()), Team.CHO));

        // 아군 기물 배치
        pieces.put(Position.of(5, 6), Piece.of(new PieceStatus(GUARD, new GuardMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(
                Position.of(5, 6)
        );
    }
}
