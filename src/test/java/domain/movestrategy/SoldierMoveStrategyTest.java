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

import static domain.piece.PieceType.SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

class SoldierMoveStrategyTest {

    private final MoveStrategy strategy = new SoldierMoveStrategy();

    @Test
    @DisplayName("졸은 좌, 우, 위로 이동한다.")
    void cho_solider_moves_correct_directions() {
        // given
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(7, 3);

        pieces.put(from, Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

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

    @Test
    @DisplayName("병은 좌, 우, 아래로 이동한다.")
    void han_soldier_moves_correct_directions() {
        // given
        final Map<Position, Piece> pieces = new HashMap<>();
        final Position from = Position.of(4, 3);

        pieces.put(from, Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.HAN));

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
    @DisplayName("졸은 한나라 궁성에서 전진(위) 방향으로 대각선 이동할 수 있다.")
    void cho_soldier_can_move_diagonal_in_han_palace() {
        // given
        Position from = Position.of(2, 5); // 한나라 궁성 중앙

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

        Board board = Board.of(pieces);

        // when
        List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(
                Position.of(1, 4),
                Position.of(1, 6)
        );
    }

    @Test
    @DisplayName("병은 초나라 궁성에서 전진(아래) 방향으로 대각선 이동할 수 있다.")
    void han_soldier_can_move_diagonal_in_cho_palace() {
        // given
        Position from = Position.of(9, 5); // 초나라 궁성 중앙

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.HAN));

        Board board = Board.of(pieces);

        // when
        List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(
                Position.of(10, 4),
                Position.of(10, 6)
        );
    }

    @Test
    @DisplayName("졸은 후진(아래) 방향으로 대각선 이동할 수 없다.")
    void cho_soldier_cannot_backward_diagonal() {
        Position from = Position.of(2, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.CHO));

        Board board = Board.of(pieces);

        List<Position> result = strategy.calculateMovablePositions(from, board);

        assertThat(result).doesNotContain(
                Position.of(3, 4),
                Position.of(3, 6)
        );
    }

    @Test
    @DisplayName("병은 후진(위) 방향으로 대각선 이동할 수 없다.")
    void han_soldier_cannot_backward_diagonal() {
        Position from = Position.of(9, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(SOLDIER, MoveStrategyType.SOLDIER), Team.HAN));

        Board board = Board.of(pieces);

        List<Position> result = strategy.calculateMovablePositions(from, board);

        assertThat(result).doesNotContain(
                Position.of(8, 4),
                Position.of(8, 6)
        );
    }
}
