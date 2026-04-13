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

class ElephantMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new ElephantMoveStrategy();
    }

    @Test
    @DisplayName("코끼리는 막힘이 없으면 8방향으로 이동할 수 있다")
    void elephantMoveTest() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.ELEPHANT));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(2, 3),
                Position.of(2, 7),
                Position.of(3, 8),
                Position.of(7, 8),
                Position.of(8, 7),
                Position.of(8, 3),
                Position.of(7, 2),
                Position.of(3, 2)
        );
    }

    @Test
    @DisplayName("코끼리는 첫 번째 경유지가 막히면 해당 방향으로 이동할 수 없다")
    void elephantCantMoveWhenFirstPathBlocked() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.ELEPHANT));
        pieces.put(Position.of(4, 5), Piece.hanPieceOf(PieceType.CHARIOT));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).doesNotContain(
                Position.of(2, 3),
                Position.of(2, 7)
        );
    }

    @Test
    @DisplayName("코끼리는 두 번째 경유지가 막히면 해당 방향으로 이동할 수 없다")
    void elephantCantMoveWhenSecondPathBlocked() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.ELEPHANT));
        pieces.put(Position.of(3, 4), Piece.hanPieceOf(PieceType.CHARIOT));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).doesNotContain(
                Position.of(2, 3)
        );
    }
}
