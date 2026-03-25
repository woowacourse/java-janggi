package janggi.domain;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HanTurnTest {

    @Test
    @DisplayName("한나라에서 초나라로 턴을 넘기는 기능")
    void turn_change() {
        // given
        Point from = Point.of(1, 1);
        Point to = Point.of(2, 3);

        // when
        GameStatus status = new HanTurn();
        GameStatus gameStatus = status.move(from, to);

        //then
        assertInstanceOf(ChoTurn.class, gameStatus);
    }
}
