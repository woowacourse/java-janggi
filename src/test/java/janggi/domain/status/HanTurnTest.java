package janggi.domain.status;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import janggi.domain.Board;
import janggi.domain.Boards;
import janggi.domain.Point;
import janggi.dto.PositionInfo;
import janggi.fixture.PositionInfoFixture;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HanTurnTest {

    private Boards boards;

    @BeforeEach
    void setUp() {
        Board board = new Board();
        List<PositionInfo> info = new ArrayList<>();
        info.add(PositionInfoFixture.from(List.of("HAN","JANG", "4", "1")));
        info.add(PositionInfoFixture.from(List.of("CHO","JANG", "4", "8")));
        info.add(PositionInfoFixture.from(List.of("HAN", "CHA", "1", "1")));
        info.add(PositionInfoFixture.from(List.of("CHO", "CHA", "2", "3")));
        board.init(info);
        boards = new Boards(board);
    }

    @Test
    @DisplayName("한나라에서 초나라로 턴을 넘기는 기능")
    void turn_change() {
        // given
        Point from = Point.of(1, 1);
        Point to = Point.of(1, 2);

        // when
        GameStatus status = new HanTurn();
        GameStatus gameStatus = status.move(from, to, boards);

        //then
        assertInstanceOf(ChoTurn.class, gameStatus);
    }

    @Test
    @DisplayName("초나라의 기물을 움직일 시 예외 발생")
    void unavailable_move() {
        // given
        Board board = new Board();
        List<PositionInfo> info = new ArrayList<>();
        info.add(PositionInfoFixture.from(List.of("CHO", "CHA", "1", "1")));
        board.init(info);
        Boards boards = new Boards(board);
        Point from = Point.of(1, 1);
        Point to = Point.of(2, 3);

        // when
        GameStatus status = new HanTurn();

        //then
        assertThatThrownBy(() -> status.move(from, to, boards))
                .isInstanceOf(IllegalStateException.class);
    }
}
