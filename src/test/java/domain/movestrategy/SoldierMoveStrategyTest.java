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

class SoldierMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new SoldierMoveStrategy();
    }

    @Test
    @DisplayName("초나라의 졸은 상, 좌, 우로 이동 가능하다")
    public void choSoldierMoveTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(4, 3);
        pieces.put(from, Piece.choPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 2),
                Position.of(4, 4),
                Position.of(3, 3)
        );
    }

    @Test
    @DisplayName("한나라의 병은 하, 좌, 우로 이동 가능하다")
    void hanSoldierMoveTest() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(4, 3);
        pieces.put(from, Piece.hanPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 2),
                Position.of(4, 4),
                Position.of(5, 3)
        );
    }

    @Test
    @DisplayName("졸, 병은 이동할 위치에 아군이 있으면 이동할 수 없다.")
    void soldierCantMoveAllyPosition() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(4, 3);

        pieces.put(from, Piece.hanPieceOf(PieceType.SOLDIER));
        pieces.put(Position.of(4, 2), Piece.hanPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 4),
                Position.of(5, 3)
        );
    }

    @Test
    @DisplayName("졸, 병은 이동할 위치에 상대 기물이 있으면 잡을 수 있다.")
    void soldierCanCaptureOpposite() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(4, 3);

        pieces.put(from, Piece.hanPieceOf(PieceType.SOLDIER));
        pieces.put(Position.of(4, 2), Piece.choPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 2),
                Position.of(4, 4),
                Position.of(5, 3)
        );
    }

    @Test
    @DisplayName("초나라 졸은 궁성에서 상향 대각선으로 이동 가능하다")
    void choSoldierCanMoveUpperDiagonalInPalace() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(2, 5);
        pieces.put(from, Piece.choPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> movable = strategy.getMovablePositions(board, from);

        // then
        assertThat(movable).containsExactlyInAnyOrder(
                Position.of(1, 4),
                Position.of(1, 5),
                Position.of(1, 6),
                Position.of(2, 4),
                Position.of(2, 6)
        );
    }

    @Test
    @DisplayName("초나라 졸은 궁성에서 대각선이 없는 곳으로는 이동 불가능하다")
    void choSoldierCantMoveDiagonalInNotExistDigonalEdge() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(3, 5);
        pieces.put(from, Piece.choPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> movable = strategy.getMovablePositions(board, from);

        // then
        assertThat(movable).doesNotContain(
                Position.of(2, 4),
                Position.of(2, 6)
        );
    }

    @Test
    @DisplayName("한나라 병은 궁성에서 하향 대각선으로 이동 가능하다")
    void hanSoldierCanMoveUpperDiagonalInPalace() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(9, 5);
        pieces.put(from, Piece.hanPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> movable = strategy.getMovablePositions(board, from);

        // then
        assertThat(movable).containsExactlyInAnyOrder(
                Position.of(9, 4),
                Position.of(9, 6),
                Position.of(10, 4),
                Position.of(10, 5),
                Position.of(10, 6)
        );
    }

    @Test
    @DisplayName("한나라 병은 궁성에서 대각선이 없는 곳으로는 이동 불가능하다")
    void hanSoldierCantMoveDiagonalInNotExistDigonalEdge() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = Position.of(8, 5);
        pieces.put(from, Piece.hanPieceOf(PieceType.SOLDIER));

        Board board = Board.init(pieces);

        // when
        List<Position> movable = strategy.getMovablePositions(board, from);

        // then
        assertThat(movable).doesNotContain(
                Position.of(9, 4),
                Position.of(9, 6)
        );
    }
}
