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

class HorseMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new HorseMoveStrategy();
    }

    @Test
    @DisplayName("말은 경유지가 막히지 않으면 8방향 이동 가능")
    void horse_moves_all_when_not_blocked() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.HORSE));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

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
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.HORSE));
        pieces.put(Position.of(4, 5), Piece.choPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).doesNotContain(
                Position.of(3, 4),
                Position.of(3, 6)
        );

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
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.HORSE));
        pieces.put(Position.of(4, 5), Piece.choPieceOf(PieceType.SOLDIER));
        pieces.put(Position.of(5, 6), Piece.choPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).doesNotContain(
                Position.of(3, 4),
                Position.of(3, 6),
                Position.of(4, 7),
                Position.of(6, 7)
        );
    }
}
