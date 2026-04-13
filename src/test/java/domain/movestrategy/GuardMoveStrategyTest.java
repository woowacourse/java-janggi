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


class GuardMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new GuardMoveStrategy();
    }

    @Test
    @DisplayName("사는 궁성 중앙에서 8방향 중 한 칸 이동이 가능")
    void guardMoveTest() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.hanPieceOf(PieceType.GUARD));

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
    @DisplayName("사는 궁성 꼭짓점에서 중앙으로 대각선 이동 가능")
    void guardCanMoveVertexToMiddleOfPalace() {
        // given
        Position from = Position.of(1, 4);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.hanPieceOf(PieceType.GUARD));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).contains(
                Position.of(2, 5)
        );
    }

    @Test
    @DisplayName("사는 궁성 외곽 중앙에서 다른 외곽 중앙으로 이동 불가")
    void guardCantMoveSideToAnotherSide() {
        // given
        Position from = Position.of(2, 4);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.hanPieceOf(PieceType.GUARD));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).doesNotContain(
                Position.of(1, 5),
                Position.of(3, 5)
        );
    }

    @Test
    @DisplayName("사는 궁성 바깥으로 이동 불가")
    void guardCantMoveOutSidePalace() {
        // given
        Position from = Position.of(2, 4);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.hanPieceOf(PieceType.GUARD));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).doesNotContain(
                Position.of(1, 3),
                Position.of(2, 3),
                Position.of(3, 3)
        );
    }

    @Test
    @DisplayName("사는 이동할 위치에 아군이 있으면 이동 불가")
    void guardCantMoveAllyPosition() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();

        Position from = Position.of(2, 5);
        pieces.put(from, Piece.hanPieceOf(PieceType.GUARD));
        pieces.put(Position.of(1, 4), Piece.hanPieceOf(PieceType.GUARD));
        pieces.put(Position.of(1, 6), Piece.hanPieceOf(PieceType.HORSE));

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
