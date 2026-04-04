package janggi.domain.piece;

import janggi.domain.board.BoardState;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.exception.business.InvalidMoveException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PieceTest {
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
    void 다른_진영의_기물을_잡았을_경우_예외가_발생하지_않는다() {
        Position from = Position.of(Row.of(4), Column.of(3));
        Position to = Position.of(Row.of(4), Column.of(4));

        Piece pieceHan = new Piece(Team.HAN, PieceType.HAN_JOL);
        Piece pieceCho = new Piece(Team.CHO, PieceType.CHO_JOL);

        Map<Position, Piece> fakeBoard = new HashMap<>();
        fakeBoard.put(from, pieceHan);
        fakeBoard.put(to, pieceCho);

        BoardState boardState = new ObstaclFakeBoard(fakeBoard);

        // when & then
        assertThatCode(() -> pieceHan.verifyMove(from, to, boardState))
                .doesNotThrowAnyException();
    }

    @Test
    void 같은_진영의_기물을_잡았을_경우_예외가_발생한다() {
        // given
        Position from = Position.of(Row.of(4), Column.of(3));
        Position to = Position.of(Row.of(4), Column.of(4));

        Piece pieceHan1 = new Piece(Team.HAN, PieceType.HAN_JOL);
        Piece pieceHan2 = new Piece(Team.HAN, PieceType.HAN_JOL);

        Map<Position, Piece> fakeBoard = new HashMap<>();
        fakeBoard.put(from, pieceHan1);
        fakeBoard.put(to, pieceHan2);

        BoardState boardState = new ObstaclFakeBoard(fakeBoard);

        // when & then
        assertThatThrownBy(() -> pieceHan1.verifyMove(from, to, boardState))
                .isInstanceOf(InvalidMoveException.class);
    }
}
