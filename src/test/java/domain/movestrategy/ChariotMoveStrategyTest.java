package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.player.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveStrategyTest {

    private final ChariotMoveStrategy strategy = new ChariotMoveStrategy();

    @Test
    @DisplayName("장애물이 없으면 끝까지 이동한다")
    void move_withoutObstacle() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(PieceType.CHARIOT, Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(
                Position.of(5, 6), Position.of(5, 7), Position.of(5, 8), Position.of(5, 9),
                Position.of(5, 4), Position.of(5, 3), Position.of(5, 2), Position.of(5, 1),
                Position.of(6, 5), Position.of(7, 5), Position.of(8, 5), Position.of(9, 5), Position.of(10, 5),
                Position.of(4, 5), Position.of(3, 5), Position.of(2, 5), Position.of(1, 5)
        );
    }

    @Test
    @DisplayName("처음 만난 적 기물의 위치를 포함한 경로는 모두 이동할 수 있다.")
    void capture_enemy() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(PieceType.CHARIOT, Team.CHO));
        pieces.put(Position.of(7, 5), Piece.of(PieceType.SOLDIER, Team.HAN));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(
                Position.of(6, 5),
                Position.of(7, 5)
        );
    }

    @Test
    @DisplayName("아군 기물이 있는 위치는 이동할 수 없다.")
    void cannot_move_to_ally() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(PieceType.CHARIOT, Team.CHO));

        // 아군
        pieces.put(Position.of(7, 5), Piece.of(PieceType.SOLDIER, Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(Position.of(6, 5));
        assertThat(result).doesNotContain(Position.of(7, 5)); // 아군은 못감
    }
}
