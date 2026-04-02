package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CannonMoveStrategy();
    }

    @Test
    @DisplayName("포는 장애물이 없으면 이동할 수 없다")
    void shouldNotMove_whenNoScreenExists() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));
        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("포는 하나의 기물을 넘은 이후부터 이동할 수 있다")
    void shouldMoveAfterJumpingOverOnePiece() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));
        pieces.put(Position.of(6, 5), Piece.choPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

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
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));
        pieces.put(Position.of(6, 5), Piece.choPieceOf(PieceType.SOLDIER));
        pieces.put(Position.of(8, 5), Piece.hanPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

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
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CANNON));
        pieces.put(Position.of(6, 5), Piece.choPieceOf(PieceType.CANNON));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).isEmpty();
    }
}
