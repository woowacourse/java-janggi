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

import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

class ChariotMoveStrategyTest {

    private final ChariotMoveStrategy strategy = new ChariotMoveStrategy();

    @Test
    @DisplayName("장애물이 없으면 끝까지 이동한다")
    void move_withoutObstacle() {
        // given
        final Position from = Position.of(5, 5);
        final Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.of(new PieceStatus(CHARIOT, new ChariotMoveStrategy()), Team.CHO));

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

        pieces.put(from, Piece.of(new PieceStatus(CHARIOT, new ChariotMoveStrategy()), Team.CHO));
        pieces.put(Position.of(7, 5), Piece.of(new PieceStatus(SOLDIER, new SoldierMoveStrategy()), Team.HAN));

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

        pieces.put(from, Piece.of(new PieceStatus(CHARIOT, new ChariotMoveStrategy()), Team.CHO));

        // 아군
        pieces.put(Position.of(7, 5), Piece.of(new PieceStatus(SOLDIER, new SoldierMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(Position.of(6, 5));
        assertThat(result).doesNotContain(Position.of(7, 5));
    }

    @Test
    @DisplayName("궁성 바깥에서는 대각선으로 이동할 수 없다.")
    void cannot_move_diagonal_palace_outside() {
        // given
        final Position from = Position.of(10, 1); // 궁성 바깥, 초나라 가장 좌측 차의 기본 자리
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(CHARIOT, new ChariotMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).doesNotContain(Position.of(4, 4)); // 궁성 중앙
    }

    @Test
    @DisplayName("궁성 중앙에서는 네 가지 방향의 대각선으로 이동한다")
    void palace_diagonal_from_center() {
        // given
        final Position from = Position.of(9, 5);
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(CHARIOT, new ChariotMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        // when
        final List<Position> result = strategy.calculateMovablePositions(from, board);

        // then
        assertThat(result).contains(
                Position.of(8, 4),
                Position.of(8, 6),
                Position.of(10, 4),
                Position.of(10, 6)
        );
    }

    @Test
    @DisplayName("궁성 내에서 대각선 이동을 할 때 궁성 밖을 벗어나지 않는다.")
    void cannot_out_palace_boundary() {
        //given
        final Position from = Position.of(8, 4);// 초나라 궁성 좌측 상단 꼭짓점
        final Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.of(new PieceStatus(CHARIOT, new ChariotMoveStrategy()), Team.CHO));

        final Board board = Board.of(pieces);

        //when
        final List<Position> result = strategy.calculatePalaceMovablePositions(from, board);

        //then
        assertThat(result).allMatch(board::inPalace);
    }
}
