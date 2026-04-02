package janggi.domain.movestrategy;

import janggi.domain.BoardState;
import janggi.domain.position.Column;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.domain.Team;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JolMoveStrategyTest {
    private static class FakeBoard implements BoardState {
        @Override
        public boolean hasPieceAt(Position position) {
            return false;
        }

        @Override
        public Piece getPieceAt(Position position) {
            return null;
        }
    }

    private static class JolFakeBoard implements BoardState {
        private final Map<Position, Piece> obstacles;

        public JolFakeBoard(Map<Position, Piece> obstacles) {
            this.obstacles = new HashMap<>(obstacles);
        }

        @Override
        public boolean hasPieceAt(Position position) {
            return obstacles.containsKey(position);
        }

        @Override
        public Piece getPieceAt(Position position) {
            return obstacles.get(position);
        }
    }

    @Test
    void 초나라_졸은_위로_한_칸_전진할_수_있다() {
        // given
        MoveStrategy moveStorage = new JolMoveStrategy();
        Position from = Position.of(Row.of(6), Column.of(4));
        Position to = Position.of(Row.of(5), Column.of(4));
        Piece piece = new Piece(Team.CHO, PieceType.CHO_JOL);
        BoardState boardState = new JolFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_졸은_아래로_한_칸_후퇴할_수_없다() {
        // given
        MoveStrategy moveStorage = new JolMoveStrategy();
        Position from = Position.of(Row.of(6), Column.of(4));
        Position to = Position.of(Row.of(7), Column.of(4));
        Piece piece = new Piece(Team.CHO, PieceType.CHO_JOL);
        BoardState boardState = new JolFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_졸은_아래로_한_칸_전진할_수_있다() {
        // given
        MoveStrategy moveStorage = new JolMoveStrategy();
        Position from = Position.of(Row.of(3), Column.of(4));
        Position to = Position.of(Row.of(4), Column.of(4));
        Piece piece = new Piece(Team.HAN, PieceType.HAN_JOL);
        BoardState boardState = new JolFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 한나라_졸은_위로_한_칸_후퇴할_수_없다() {
        // given
        MoveStrategy moveStorage = new JolMoveStrategy();
        Position from = Position.of(Row.of(3), Column.of(4));
        Position to = Position.of(Row.of(2), Column.of(4));
        Piece piece = new Piece(Team.HAN, PieceType.HAN_JOL);
        BoardState boardState = new JolFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 졸은_왼쪽으로_이동할_수_있다() {
        // given
        MoveStrategy moveStorage = new JolMoveStrategy();
        Position from = Position.of(Row.of(3), Column.of(4));
        Position to = Position.of(Row.of(3), Column.of(3));
        Piece piece = new Piece(Team.CHO, PieceType.HAN_JOL);
        BoardState boardState = new JolFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 졸은_오른쪽으로_이동할_수_있다() {
        // given
        MoveStrategy moveStorage = new JolMoveStrategy();
        Position from = Position.of(Row.of(3), Column.of(4));
        Position to = Position.of(Row.of(3), Column.of(5));
        Piece piece = new Piece(Team.CHO, PieceType.HAN_JOL);
        BoardState boardState = new JolFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 이동할_수_없는_행마면_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new JolMoveStrategy();
        Position from = Position.of(Row.of(3), Column.of(4));
        Position to = Position.of(Row.of(5), Column.of(5));
        Piece piece = new Piece(Team.CHO, PieceType.HAN_JOL);
        BoardState boardState = new JolFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }
}
