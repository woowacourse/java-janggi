package domain.rule;

import domain.board.Board;
import domain.board.Side;
import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BasicCaptureRuleTest {

    @Test
    @DisplayName("목적지가 비어있으면 이동할 수 있다.")
    void empty_Pass_Test() {
        // given
        Board board = new Board(Map.of());
        BasicCaptureRule rule = new BasicCaptureRule();

        Position start = new Position(4, 4);
        Position dest = new Position(3, 4);

        // when
        boolean result = rule.isValid(board, start, dest, new Cannon(Side.HAN));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상대 기물을 잡을 수 있다.")
    void capture_Test() {
        // given
        Map<Position, Piece> map = new HashMap<>();
        map.put(new Position(3, 4), new Pawn(Side.CHU));

        Board board = new Board(map);
        BasicCaptureRule rule = new BasicCaptureRule();

        Position start = new Position(4, 4);
        Position dest = new Position(3, 4);

        // when
        boolean result = rule.isValid(board, start, dest, new Cannon(Side.HAN));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아군 기물은 잡을 수 없다.")
    void doesNotCapture_Test() {
        // given
        Map<Position, Piece> map = new HashMap<>();
        map.put(new Position(3, 4), new Pawn(Side.HAN));

        Board board = new Board(map);
        BasicCaptureRule rule = new BasicCaptureRule();

        Position start = new Position(4, 4);
        Position dest = new Position(3, 4);

        // when
        boolean result = rule.isValid(board, start, dest, new Cannon(Side.HAN));

        // then
        assertThat(result).isFalse();
    }
}
