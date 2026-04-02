package janggi.domain.movestrategy;

import janggi.domain.BoardState;
import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Row;
import janggi.domain.Team;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GungseongBoundMoveStrategyTest {
    private static class ObstaclFakeBoard implements BoardState {
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
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(8), Column.of(4));
        Position to = Position.of(Row.of(7), Column.of(4));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성_내부에서_아래로_한_칸_이동할_수_있다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(8), Column.of(4));
        Position to = Position.of(Row.of(9), Column.of(4));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성_내부에서_왼쪽으로_한_칸_이동할_수_있다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(8), Column.of(4));
        Position to = Position.of(Row.of(8), Column.of(3));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성_내부에서_오른쪽으로_한_칸_이동할_수_있다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(8), Column.of(4));
        Position to = Position.of(Row.of(8), Column.of(5));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isTrue();
    }

    @Test
    void 초나라_왕과_사는_궁성의_좌측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(7), Column.of(3));
        Position to = Position.of(Row.of(7), Column.of(2));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_좌측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(8), Column.of(3));
        Position to = Position.of(Row.of(8), Column.of(2));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_좌측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(9), Column.of(3));
        Position to = Position.of(Row.of(9), Column.of(2));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_우측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(7), Column.of(5));
        Position to = Position.of(Row.of(7), Column.of(6));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_우측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(8), Column.of(5));
        Position to = Position.of(Row.of(8), Column.of(6));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성의_우측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(9), Column.of(5));
        Position to = Position.of(Row.of(9), Column.of(6));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 초나라_왕과_사는_궁성_제일_앞줄에서_앞으로_한_칸_더_전진하여_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(7), Column.of(4));
        Position to = Position.of(Row.of(6), Column.of(4));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.CHO, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_좌측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(0), Column.of(3));
        Position to = Position.of(Row.of(0), Column.of(2));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_좌측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(1), Column.of(3));
        Position to = Position.of(Row.of(1), Column.of(2));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_좌측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(2), Column.of(3));
        Position to = Position.of(Row.of(2), Column.of(2));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_우측_상단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(0), Column.of(5));
        Position to = Position.of(Row.of(0), Column.of(6));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_우측_중단에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(1), Column.of(5));
        Position to = Position.of(Row.of(1), Column.of(6));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성의_우측_하단_끝에서_궁성_밖으로_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(2), Column.of(5));
        Position to = Position.of(Row.of(2), Column.of(6));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }

    @Test
    void 한나라_왕과_사는_궁성_제일_앞줄에서_앞으로_한_칸_더_전진하여_이탈하는_경우_실패를_반환한다() {
        // given
        MoveStrategy moveStorage = new GungseongBoundMoveStrategy();
        Position from = Position.of(Row.of(2), Column.of(4));
        Position to = Position.of(Row.of(3), Column.of(4));
        Piece piece = new Piece(new GungseongBoundMoveStrategy(), Team.HAN, PieceType.SA);
        BoardState boardState = new ObstaclFakeBoard(Map.of(from, piece));
        // when & then
        assertThat(moveStorage.canMove(from, to, boardState)).isFalse();
    }
}
