package janggi.domain.position;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RowTest {

    @Test
    void 행좌표_1로_행을_생성하면_좌표값_1을_가지고있다() {
        Row row = new Row(1);
        int rowValue = row.getValue();

        assertThat(rowValue).isEqualTo(1);
    }

    @Test
    void 행좌표_10로_행을_생성하면_좌표값_10을_가지고있다() {
        Row row = new Row(10);
        int rowValue = row.getValue();

        assertThat(rowValue).isEqualTo(10);
    }

    @Test
    void 범위_1에서10_사이가_아닌_행좌표로_행을_생성하면_에러가_발생한다() {

        assertAll(
                () -> assertThatThrownBy(() -> new Row(0))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다"),
                () -> assertThatThrownBy(() -> new Row(11))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다")
        );
    }
}
