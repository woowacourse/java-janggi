package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Row;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChaMoveStorageTest {
    private static class FakeBoard implements BoardView {
        @Override
        public boolean hasPieceAt(Position position) {
            return false;
        }

        @Override
        public Piece getPieceAt(Position position) {
            return null;
        }
    }

    private static class ObstaclFakeBoard implements BoardView {
        private final List<Position> obstacles;

        public ObstaclFakeBoard(List<Position> obstacles) {
            this.obstacles = new ArrayList<>(obstacles);
        }

        @Override
        public boolean hasPieceAt(Position position) {
            return obstacles.contains(position);
        }

        @Override
        public Piece getPieceAt(Position position) {
            return null;
        }
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높고_멱이_없다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));
        BoardView boardState = new FakeBoard();

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮고_멱이_없다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));
        BoardView boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높고_멱이_없다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));
        BoardView boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_낮고_멱이_없다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));
        BoardView boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 차가_아예_갈_수_없는_행마면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(1), Column.of(3));
        BoardView boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_높으면서_멱이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(3));
        Position obstacl = Position.of(Row.of(0), Column.of(2));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstacl));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_X축은_같고_Y축은_도착지점이_더_낮으면서_멱이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(0));
        Position obstacl = Position.of(Row.of(0), Column.of(1));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstacl));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_높으면서_멱이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(0), Column.of(0));
        Position to = Position.of(Row.of(3), Column.of(0));
        Position obstacl = Position.of(Row.of(2), Column.of(0));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstacl));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 출발지점과_도착지점이_Y축은_같고_X축은_도착지점이_더_낮으면서_멱이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new ChaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(0), Column.of(0));
        Position obstacl = Position.of(Row.of(1), Column.of(0));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstacl));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }
}
