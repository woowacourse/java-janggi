package domain.movestrategy;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceStatus;
import domain.piece.Position;
import domain.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

class HorseMoveStrategyTest {

    private final MoveStrategy strategy = new HorseMoveStrategy();

    @Test
    @DisplayName("말은 경유지가 막히지 않으면 8방향 이동 가능")
    void horse_moves_all_when_not_blocked() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(HORSE, MoveStrategyType.HORSE), Team.CHO));
        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

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
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(HORSE, MoveStrategyType.HORSE), Team.CHO));

        // UP 경유지 막기
        pieces.put(Position.of(4, 5), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

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
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(HORSE, MoveStrategyType.HORSE), Team.CHO));

        // UP, RIGHT 막기
        pieces.put(Position.of(4, 5), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO)); // UP
        pieces.put(Position.of(5, 6), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO)); // RIGHT

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(
                Position.of(3, 4),
                Position.of(3, 6),
                Position.of(4, 7),
                Position.of(6, 7)
        );
    }

    @Test
    @DisplayName("도착지에 아군 기물이 있으면 이동할 수 없다.")
    void horse_cannot_move_to_ally() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(HORSE, MoveStrategyType.HORSE), Team.CHO));
        pieces.put(Position.of(3, 4), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(Position.of(3, 4));
    }
}
