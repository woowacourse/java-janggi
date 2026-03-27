package janggi.domain.status;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import janggi.domain.Board;
import janggi.domain.Point;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Jang;
import janggi.domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HanTurnTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(4, 1), new Jang(Team.HAN));
        pieces.put(Point.of(4, 8), new Jang(Team.CHO));
        pieces.put(Point.of(1, 1), new Cha(Team.HAN));
        pieces.put(Point.of(2, 3), new Cha(Team.CHO));
        board.init(pieces);
    }

    @Test
    @DisplayName("한나라에서 초나라로 턴을 넘기는 기능")
    void turn_change() {
        // given
        Point from = Point.of(1, 1);
        Point to = Point.of(1, 2);

        // when
        GameStatus status = new HanTurn();
        GameStatus gameStatus = status.move(from, to, board);

        //then
        assertInstanceOf(ChoTurn.class, gameStatus);
    }

    @Test
    @DisplayName("초나라의 기물을 움직일 시 예외 발생")
    void unavailable_move() {
        // given
        Board board = new Board();
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(1, 1), new Cha(Team.CHO));
        board.init(pieces);
        Point from = Point.of(1, 1);
        Point to = Point.of(2, 3);

        // when
        GameStatus status = new HanTurn();

        //then
        assertThatThrownBy(() -> status.move(from, to, board))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
