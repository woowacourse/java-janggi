package janggi.piece;

import janggi.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmptyTest {

    @Test
    @DisplayName("빈 셀에서 이동 후보군 계산시 예외를 발생시킨다")
    void test() {
        Empty empty = new Empty();
        assertThatThrownBy(() -> empty.computeCandidatePositions(new Position(3, 10)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이 위치에는 말이 존재하지 않습니다.");
    }

}