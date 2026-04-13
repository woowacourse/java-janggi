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

class ChariotMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new ChariotMoveStrategy();
    }

    @Test
    @DisplayName("차는 상하좌우로 이동 가능")
    void chariotMoveTest() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));

        Board board = Board.init(pieces);

        // when
        List<Position> movablePositions = strategy.getMovablePositions(board, from);

        // then
        assertThat(movablePositions).containsExactlyInAnyOrder(
                Position.of(1, 5), Position.of(2, 5), Position.of(3, 5), Position.of(4, 5),
                Position.of(6, 5), Position.of(7, 5), Position.of(8, 5), Position.of(9, 5), Position.of(10, 5),
                Position.of(5, 1), Position.of(5, 2), Position.of(5, 3), Position.of(5, 4),
                Position.of(5, 6), Position.of(5, 7), Position.of(5, 8), Position.of(5, 9)
        );
    }

    @Test
    @DisplayName("차는 장애물을 넘어서는 갈 수 없음")
    void chariotCantMoveOverPiece() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(4, 5), Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(7, 5), Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(5, 1), Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(5, 9), Piece.choPieceOf(PieceType.CHARIOT));

        Board board = Board.init(pieces);

        // when
        List<Position> movablePositions = strategy.getMovablePositions(board, from);

        // then
        assertThat(movablePositions).containsExactlyInAnyOrder(
                Position.of(6, 5),
                Position.of(5, 2), Position.of(5, 3), Position.of(5, 4),
                Position.of(5, 6), Position.of(5, 7), Position.of(5, 8)
        );
    }

    @Test
    @DisplayName("장애물이 상대편이면 차가 잡을 수 있음. 장애물 넘어서는 갈 수 없음")
    void chariotCatchOpposite() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.hanPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(4, 5), Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(7, 5), Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(5, 1), Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(5, 9), Piece.choPieceOf(PieceType.CHARIOT));

        Board board = Board.init(pieces);

        // when
        List<Position> movablePositions = strategy.getMovablePositions(board, from);

        // then
        assertThat(movablePositions).containsExactlyInAnyOrder(
                Position.of(4, 5),
                Position.of(6, 5), Position.of(7, 5),
                Position.of(5, 1), Position.of(5, 2), Position.of(5, 3), Position.of(5, 4),
                Position.of(5, 6), Position.of(5, 7), Position.of(5, 8), Position.of(5, 9)
        );
    }

    @Test
    @DisplayName("차는 궁성 내부에서 대각선으로 이동 가능")
    void chariotCanMoveDiagonalInPalace() {
        // given
        Position from = Position.of(1, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.hanPieceOf(PieceType.CHARIOT));

        Board board = Board.init(pieces);

        // when
        List<Position> movablePositions = strategy.getMovablePositions(board, from);

        // then
        assertThat(movablePositions).contains(
                Position.of(2, 5),
                Position.of(3, 6)
        );
    }

    @Test
    @DisplayName("차는 대각선 경로에 장애물이 있으면 이동 불가능")
    void chariotCantMoveDiagonalWhenBlocked() {
        // given
        Position from = Position.of(1, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.hanPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(2, 5), Piece.hanPieceOf(PieceType.CHARIOT));

        Board board = Board.init(pieces);

        // when
        List<Position> movablePositions = strategy.getMovablePositions(board, from);

        // then
        assertThat(movablePositions).doesNotContain(
                Position.of(2, 5),
                Position.of(3, 6)
        );
    }
}
