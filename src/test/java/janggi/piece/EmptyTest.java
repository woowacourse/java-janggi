package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptyTest {

    @Test
    @DisplayName("빈 셀에서 이동 후보군 계산시 예외를 발생시킨다")
    void test() {
        Empty empty = new Empty();
        JanggiBoard board = JanggiBoard.initialize();

        assertThatThrownBy(() -> empty.filterReachableDestinations(new Position(3, 10), board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이 위치에는 말이 존재하지 않습니다.");
    }

}
