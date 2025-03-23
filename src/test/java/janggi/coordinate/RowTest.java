package janggi.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RowTest {

    @Test
    @DisplayName("Row는 1부터 10까지의 정수만 허용한다")
    void validateRange() {
        // given
        // when
        // then
        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> new Row(0));
            assertThrows(IllegalArgumentException.class, () -> new Row(11));

            assertThat(new Row(1).value()).isEqualTo(1);
            assertThat(new Row(5).value()).isEqualTo(5);
            assertThat(new Row(10).value()).isEqualTo(10);
        });
    }

    @Test
    @DisplayName("add(delta)를 통해 Row를 이동할 수 있다")
    void addRow() {
        // given
        Row row = new Row(4);

        // when
        Row moved = row.add(3);

        // then
        assertThat(moved.value()).isEqualTo(7);
    }

    @Test
    @DisplayName("다른 Row와의 절댓값 거리를 계산할 수 있다")
    void calculateDistanceTo() {
        // given
        Row row1 = new Row(3);
        Row row2 = new Row(7);

        // when
        Distance distance = row1.distanceTo(row2);

        // then
        assertThat(distance).isEqualTo(new Distance(4, 0));
    }

    @Test
    @DisplayName("다른 Row와의 벡터를 계산할 수 있다")
    void calculateVectorTo() {
        // given
        Row from = new Row(8);
        Row to = new Row(5);

        // when
        Vector vector = from.vectorTo(to);

        // then
        assertThat(vector).isEqualTo(new Vector(-3, 0));
    }
}
