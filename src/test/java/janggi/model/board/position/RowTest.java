package janggi.model.board.position;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @DisplayName("두 행 사이의 거리를 반환한다.")
    @Test
    void getDistanceTo() {
        //given
        Row eight = Row.EIGHT;
        Row zero = Row.ZERO;

        //when & then
        assertThat(eight.getDistanceTo(zero))
                .isEqualTo(-2);
    }
}