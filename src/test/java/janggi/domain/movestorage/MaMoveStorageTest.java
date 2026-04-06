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

class MaMoveStorageTest {
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
    void 행마_성공_테스트() {
        // given
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(6), Column.of(3));
        BoardView boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 마가_갈_수_없는_좌표가_들어오면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(6), Column.of(4));
        BoardView boardState = new FakeBoard();

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_위로_2칸_왼쪽으로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(3), Column.of(2));

        Position obstaclPosition = Position.of(Row.of(4), Column.of(3));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_위로_2칸_오른쪽으로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(5), Column.of(2));

        Position obstaclPosition = Position.of(Row.of(4), Column.of(3));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_오른쪽으로_2칸_위로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(6), Column.of(3));

        Position obstaclPosition = Position.of(Row.of(5), Column.of(4));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_오른쪽으로_2칸_아래로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(6), Column.of(5));

        Position obstaclPosition = Position.of(Row.of(5), Column.of(4));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_아래로_2칸_오른쪽으로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(5), Column.of(6));

        Position obstaclPosition = Position.of(Row.of(4), Column.of(5));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_아래로_2칸_왼쪽으로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(3), Column.of(6));

        Position obstaclPosition = Position.of(Row.of(4), Column.of(5));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_왼쪽으로_2칸_아래로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(2), Column.of(5));

        Position obstaclPosition = Position.of(Row.of(3), Column.of(4));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 마가_왼쪽으로_2칸_위로_1칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        MoveStorage moveStorage = new MaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(2), Column.of(3));

        Position obstaclPosition = Position.of(Row.of(3), Column.of(4));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }
}
