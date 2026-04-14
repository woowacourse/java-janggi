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

import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

class ElephantMoveStrategyTest {

    private final MoveStrategy strategy = new ElephantMoveStrategy();

    @Test
    @DisplayName("코끼리는 막힘이 없으면 8방향으로 이동할 수 있다")
    void shouldMoveInAllDirections_whenNoBlockExists() {
        // given
        final Position from = Position.of(5, 5);

        final Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

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
        final Position from = Position.of(5, 5);

        final Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT), Team.CHO));

        pieces.put(Position.of(4, 5), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO)); // UP 막힘

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

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
        final Position from = Position.of(5, 5);

        final Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT), Team.CHO));

        pieces.put(Position.of(3, 4),
                Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.HAN)); // UP → LEFT_UP

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(
                Position.of(2, 3)
        );
    }

    @Test
    @DisplayName("코끼리는 일부 방향만 막히고 나머지 방향으로는 이동할 수 있다")
    void shouldMovePartially_whenSomePathsBlocked() {
        // given
        final Position from = Position.of(5, 5);

        final Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT), Team.CHO));

        pieces.put(Position.of(4, 5), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.HAN)); // UP 막힘

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

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

    @Test
    @DisplayName("도착지에 아군 기물이 있으면 이동할 수 없다.")
    void shouldNotMoveToAllyDestination() {
        // given
        final Position from = Position.of(5, 5);

        final Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT), Team.CHO));

        // 도착지
        pieces.put(Position.of(2, 3), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(Position.of(2, 3));
    }
}
