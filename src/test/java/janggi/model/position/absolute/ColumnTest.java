package janggi.model.position.absolute;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {
    @DisplayName("두 열 사이의 거리를 반환한다.")
    @Test
    void getDistanceTo() {
        //given
        Column eight = Column.EIGHT;
        Column zero = Column.SEVEN;

        //when & then
        assertThat(eight.getDistanceTo(zero))
                .isEqualTo(1);
    }
}
