package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.ElephantSetup;
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
    @DisplayName("차는 상하좌우로 이동 가능, 장애물 있으면 이후로 이동 불가, 적 장애물이면 잡을 수 있음")
    void chariotMoveTest() {
        // given
        Position from = Position.of(5, 5);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(3, 5), Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(7, 5), Piece.hanPieceOf(PieceType.CHARIOT));

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
}
