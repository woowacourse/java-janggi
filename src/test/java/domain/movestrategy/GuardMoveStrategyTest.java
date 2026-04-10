package domain.movestrategy;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceStatus;
import domain.piece.Position;
import domain.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static domain.piece.PieceType.GUARD;
import static org.assertj.core.api.Assertions.assertThat;


class GuardMoveStrategyTest {

    private final MoveStrategy strategy = new GuardMoveStrategy();

    @Test
    @DisplayName("사는 궁성 내에서 한 칸 이동이 가능하다")
    void move_all_directions() {
        // given
        final Position from = Position.of(9, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(GUARD, new GuardMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).containsExactlyInAnyOrder(
                Position.of(8, 4),
                Position.of(8, 5),
                Position.of(8, 6),
                Position.of(9, 4),
                Position.of(9, 6),
                Position.of(10, 4),
                Position.of(10, 5),
                Position.of(10, 6)
        );
    }

    @Test
    @DisplayName("아군 기물이 있는 위치로는 이동할 수 없다.")
    void cannot_move_to_ally_position() {
        // given
        final Position from = Position.of(9, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(GUARD, new GuardMoveStrategy()), Team.CHO));

        // 아군 기물 배치
        pieces.put(Position.of(9, 6), Piece.of(new PieceStatus(GUARD, new GuardMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(Position.of(9, 6));
    }

    @Test
    @DisplayName("사는 궁성 밖으로 이동할 수 없다.")
    void cannot_move_outer_palace() {
        //given
        final Position from = Position.of(10, 6); // 초나라 우측 사 초기 자리
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(GUARD, new GeneralMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        //when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        //then
        assertThat(result).doesNotContain(Position.of(10, 7)); // 우측은 궁성 바깥
    }
}
