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

class SangMoveStorageTest {

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
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(7), Column.of(2));
        BoardView boardState = new FakeBoard();
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 상이_갈_수_없는_좌표가_들어오면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(7), Column.of(3));
        BoardView boardState = new FakeBoard();

        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_위로_3칸_왼쪽으로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(2), Column.of(1));

        Position obstaclPosition = Position.of(Row.of(3), Column.of(2));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_위로_3칸_오른쪽으로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(6), Column.of(1));

        Position obstaclPosition = Position.of(Row.of(5), Column.of(2));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_오른쪽으로_3칸_위로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(7), Column.of(2));

        Position obstaclPosition = Position.of(Row.of(6), Column.of(3));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_오른쪽으로_3칸_아래로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(7), Column.of(6));

        Position obstaclPosition = Position.of(Row.of(6), Column.of(5));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_아래로_3칸_오른쪽으로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(6), Column.of(7));

        Position obstaclPosition = Position.of(Row.of(5), Column.of(6));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_아래로_3칸_왼쪽으로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(2), Column.of(7));

        Position obstaclPosition = Position.of(Row.of(3), Column.of(6));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_왼쪽으로_3칸_아래로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(1), Column.of(6));

        Position obstaclPosition = Position.of(Row.of(2), Column.of(5));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 상이_왼쪽으로_3칸_위로_2칸_이동하려_할_때_경로에_기물이_있으면_실패를_반환한다() {
        MoveStorage moveStorage = new SangMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(4));
        Position to = Position.of(Row.of(1), Column.of(2));

        Position obstaclPosition = Position.of(Row.of(2), Column.of(3));
        BoardView boardState = new ObstaclFakeBoard(List.of(obstaclPosition));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }
}
