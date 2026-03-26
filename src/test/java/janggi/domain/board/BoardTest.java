package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.turn.TurnState;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    @Test
    void 보드는_왕이_잡혔을_경우_종료_상태를_반환한다() {
        Position start = new Position(1, 2);
        Position end = new Position(2, 2);
        Map<Position, Piece> pieces = new HashMap<>(Map.of(
                start, Pawn.from(Side.HAN),
                end, new Gung(Side.CHO)
        ));
        Board board = new Board(pieces);

        assertThat(board.move(start, end)).isEqualTo(TurnState.FINISH);
    }

    @Test
    void 보드는_왕이_잡히지_않았을_경우_진행_상태를_반환한다() {
        Position start = new Position(1, 2);
        Position end = new Position(2, 2);
        Map<Position, Piece> pieces = new HashMap<>(Map.of(
                start, Pawn.from(Side.HAN),
                end, Pawn.from(Side.CHO)
        ));
        Board board = new Board(pieces);

        assertThat(board.move(start, end)).isEqualTo(TurnState.RUNNING);
    }
}