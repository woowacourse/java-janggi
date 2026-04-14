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

import static domain.piece.PieceType.*;
import static org.assertj.core.api.Assertions.assertThat;

class CannonMoveStrategyTest {

    private final CannonMoveStrategy strategy = new CannonMoveStrategy();

    @Test
    @DisplayName("포는 장애물이 없으면 이동할 수 없다")
    void shouldNotMove_whenNoScreenExists() {
        // given
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(5, 5);

        pieces.put(from, Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("포는 하나의 기물을 넘은 이후부터 이동할 수 있다")
    void shouldMoveAfterJumpingOverOnePiece() {
        // given
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(5, 5);

        pieces.put(from, Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        // 장애물 (screen)
        pieces.put(Position.of(6, 5), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(
                Position.of(7, 5),
                Position.of(8, 5),
                Position.of(9, 5),
                Position.of(10, 5)
        );
    }

    @Test
    @DisplayName("포는 두 번째 기물 위치까지 이동할 수 있지만 그 이후로는 불가능하다")
    void shouldStopAfterSecondPiece() {
        // given
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(5, 5);

        pieces.put(from, Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        // 첫 번째 장애물
        pieces.put(Position.of(6, 5), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

        // 두 번째 기물
        pieces.put(Position.of(8, 5), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.HAN));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(
                Position.of(7, 5),
                Position.of(8, 5)
        );

        assertThat(result).doesNotContain(Position.of(9, 5));
    }

    @Test
    @DisplayName("포는 다른 포를 넘을 수 없다")
    void shouldNotJumpOverAnotherCannon() {
        // given
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(5, 5);

        pieces.put(from, Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        // 첫 번째 기물이 포
        pieces.put(Position.of(6, 5), Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("아군 기물은 잡을 수 없다.")
    void cannon_cannot_capture_ally() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        // 뛰어넘을 기물
        pieces.put(Position.of(5, 6), Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.HAN));

        // 아군 기물
        final Position ally = Position.of(5, 7);
        pieces.put(ally, Piece.of(new PieceStatus(GUARD, MoveStrategyType.GUARD), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(ally);
    }

    @Test
    @DisplayName("포는 궁성 꼭짓점에서 대각선으로 점프할 수 있다.")
    void can_jump_diagonal_in_palace() {
        Position from = Position.of(10, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        pieces.put(Position.of(9, 5),
                Piece.of(new PieceStatus(GENERAL, MoveStrategyType.GENERAL), Team.CHO)); // 점프용 기물

        Board board = Board.of(pieces);

        List<Position> result = strategy.calculateMovablePositions(from, board);

        assertThat(result).contains(Position.of(8, 6));
    }

    @Test
    @DisplayName("포는 궁성 꼭짓점에서도 점프할 기물이 없으면 대각선으로 이동할 수 없다.")
    void cannot_jump_diagonal_without_jump_piece() {
        Position from = Position.of(10, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(CANNON, MoveStrategyType.CANNON), Team.CHO));

        Board board = Board.of(pieces);

        List<Position> result = strategy.calculateMovablePositions(from, board);

        assertThat(result).doesNotContain(Position.of(8, 6));
    }
}
