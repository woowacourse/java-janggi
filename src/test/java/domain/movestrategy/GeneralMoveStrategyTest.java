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

class GeneralMoveStrategyTest {

    private final MoveStrategy strategy = new GeneralMoveStrategy();

    @Test
    @DisplayName("장군은 8방향으로 한 칸 이동할 수 있다")
    void general_moves_all_directions() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(PieceType.GENERAL, Team.CHO));
        pieces.put(Position.of(1, 1), Piece.of(PieceType.GENERAL, Team.HAN));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(4, 4),
                Position.of(4, 5),
                Position.of(4, 6),
                Position.of(5, 4),
                Position.of(5, 6),
                Position.of(6, 4),
                Position.of(6, 5),
                Position.of(6, 6)
        );
    }

    @Test
    @DisplayName("장군은 상대 장군과 마주보게 되는 위치로 이동할 수 없다")
    void general_cannot_face_opposite() {
        // given
        final Position from = Position.of(2, 4);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(PieceType.GENERAL, Team.CHO));
        pieces.put(Position.of(7, 5), Piece.of(PieceType.GENERAL, Team.HAN));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(1, 3),
                Position.of(1, 4),
                Position.of(2, 3),
                Position.of(3, 3),
                Position.of(3, 4)
        );
    }

    @Test
    @DisplayName("아군 기물이 있는 위치로는 이동할 수 없다.")
    void cannot_move_to_ally_position() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(PieceType.GENERAL, Team.CHO));

        // 아군 기물 배치
        pieces.put(Position.of(5, 6), Piece.of(PieceType.GUARD, Team.CHO));
        pieces.put(Position.of(6, 5), Piece.of(PieceType.GUARD, Team.CHO));

        pieces.put(Position.of(1, 1), Piece.of(PieceType.GENERAL, Team.HAN));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(
                Position.of(5, 6),
                Position.of(6, 5)
        );
    }
}
