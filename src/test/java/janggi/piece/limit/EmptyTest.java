package janggi.piece.limit;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.piece.Empty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmptyTest {

    @Test
    @DisplayName("빈 셀에서 이동 후보군 계산시 예외를 발생시킨다")
    void test() {
        Empty empty = new Empty();
        assertThatThrownBy(() -> empty.computeReachableDestinations(new Position(3, 10), JanggiBoard.initializeWithPieces().getBoard()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 프로그램에 오류가 발생했습니다.");
    }

}
