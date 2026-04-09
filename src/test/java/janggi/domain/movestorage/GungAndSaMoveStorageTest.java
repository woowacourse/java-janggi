package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Row;
import janggi.domain.Team;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GungAndSaMoveStorageTest {
    private static class ObstaclFakeBoard implements BoardView {
        private final Map<Position, Piece> obstacles;

        public ObstaclFakeBoard(Map<Position, Piece> obstacles) {
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
    void 초나라_왕과_사는_궁성_내부에서_위로_한_칸_이동할_수_있다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(8));
        Position to = Position.of(Row.of(4), Column.of(7));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성_내부에서_아래로_한_칸_이동할_수_있다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(8));
        Position to = Position.of(Row.of(4), Column.of(9));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성_내부에서_왼쪽으로_한_칸_이동할_수_있다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(8));
        Position to = Position.of(Row.of(3), Column.of(8));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성_내부에서_오른쪽으로_한_칸_이동할_수_있다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(8));
        Position to = Position.of(Row.of(5), Column.of(8));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성의_좌측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(7));
        Position to = Position.of(Row.of(2), Column.of(7));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_좌측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(8));
        Position to = Position.of(Row.of(2), Column.of(8));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_좌측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(9));
        Position to = Position.of(Row.of(2), Column.of(9));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_우측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(5), Column.of(7));
        Position to = Position.of(Row.of(6), Column.of(7));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_우측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(5), Column.of(8));
        Position to = Position.of(Row.of(6), Column.of(8));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_우측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(5), Column.of(9));
        Position to = Position.of(Row.of(6), Column.of(9));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성_제일_앞줄에서_앞으로_한_칸_더_전진하여_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(7));
        Position to = Position.of(Row.of(4), Column.of(6));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.CHO, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_좌측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(0));
        Position to = Position.of(Row.of(2), Column.of(0));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.HAN, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_좌측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(1));
        Position to = Position.of(Row.of(2), Column.of(1));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.HAN, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_좌측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(3), Column.of(2));
        Position to = Position.of(Row.of(2), Column.of(2));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.HAN, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_우측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(5), Column.of(0));
        Position to = Position.of(Row.of(6), Column.of(0));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.HAN, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_우측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(5), Column.of(1));
        Position to = Position.of(Row.of(6), Column.of(1));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.HAN, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_우측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(5), Column.of(2));
        Position to = Position.of(Row.of(6), Column.of(2));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.HAN, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성_제일_앞줄에서_앞으로_한_칸_더_전진하여_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStorage moveStorage = new GungAndSaMoveStorage();
        Position from = Position.of(Row.of(4), Column.of(2));
        Position to = Position.of(Row.of(4), Column.of(3));
        Piece piece = new Piece(new GungAndSaMoveStorage(), Team.HAN, 9, "士");
        BoardView boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }
}
