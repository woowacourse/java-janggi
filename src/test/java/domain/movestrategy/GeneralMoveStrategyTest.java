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

class GeneralMoveStrategyTest {

    MoveStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new GeneralMoveStrategy();
    }

    @Test
    @DisplayName("장군은 8방향으로 한 칸 이동할 수 있다")
    void generalMoveTest() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(1, 1), Piece.hanPieceOf(PieceType.GENERAL));

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
    @DisplayName("장군은 상대 장군과 마주보게 되는 위치로 이동할 수 없다")
    void generalCannotFaceOpponentGeneral() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(9, 6), Piece.hanPieceOf(PieceType.GENERAL));

        Board board = Board.init(pieces);

        // when
        List<Position> result = strategy.getMovablePositions(board, from);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(1, 4),
                Position.of(2, 4),
                Position.of(3, 4),
                Position.of(1, 5),
                Position.of(3, 5)
        );
    }

    @Test
    @DisplayName("장군은 상대 장군 사이에 다른 기물이 있으면 이동할 수 있다")
    void generalCanMoveWhenAnotherPieceExistsBetweenGenerals() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(9, 5), Piece.hanPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(5, 5), Piece.choPieceOf(PieceType.SOLDIER));

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
    @DisplayName("장군은 이동할 위치에 아군이 있으면 이동할 수 없다.")
    void generalCantMoveToAlly() {
        // given
        Position from = Position.of(2, 5);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(1, 4), Piece.choPieceOf(PieceType.GUARD));
        pieces.put(Position.of(1, 6), Piece.choPieceOf(PieceType.GUARD));
        pieces.put(Position.of(1, 1), Piece.hanPieceOf(PieceType.GENERAL));

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
