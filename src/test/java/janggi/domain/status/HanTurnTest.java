package janggi.domain.status;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import janggi.domain.Board;
import janggi.domain.Point;
import janggi.domain.piece.Piece;
import janggi.dto.PositionInfo;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HanTurnTest {

    @Test
    @DisplayName("한나라에서 초나라로 턴을 넘기는 기능")
    void turn_change() {
        // given
        Board board = new Board();
        Point from = Point.of(1, 1);
        Point to = Point.of(2, 3);

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
        List<PositionInfo> info = new ArrayList<>();
        info.add(PositionInfo.from(List.of("CHO", "Cha", "1", "1")));
        board.init(info);
        Point from = Point.of(1, 1);
        Point to = Point.of(2, 3);

        // when
        GameStatus status = new HanTurn();

        //then
        assertThatThrownBy(() -> status.move(from, to, board))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
