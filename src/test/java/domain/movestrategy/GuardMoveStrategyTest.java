package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class GuardMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new GuardMoveStrategy();
    }

    @Test
    @DisplayName("사는 8방향 한 칸 이동이 가능")
    void guardMoveTest() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.GUARD));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(1, 6),
                Position.of(2, 4),
                Position.of(2, 6),
                Position.of(3, 4),
                Position.of(3, 5),
                Position.of(3, 6)
        );
    }

    @Test
    @DisplayName("사는 이동할 위치에 아군이 있으면 이동 불가")
    void guardCantMoveAllyPosition() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.GUARD));
        pieces.put(Position.of(1, 4), Piece.choPieceOf(PieceType.GUARD));
        pieces.put(Position.of(1, 6), Piece.choPieceOf(PieceType.HORSE));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(1, 5),
                Position.of(2, 4),
                Position.of(2, 6),
                Position.of(3, 4),
                Position.of(3, 5),
                Position.of(3, 6)
        );
    }
}
