package janggi.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RowTest {

    @Test
    @DisplayName("defaults를 통해서 기본 행들을 생성할 수 있다")
    void defaults() {
        // given
        // when
        final List<Row> defaults = Row.defaults();

        // then
        assertThat(defaults).containsExactlyElementsOf(
                List.of(new Row(1),
                        new Row(2),
                        new Row(3),
                        new Row(4),
                        new Row(5),
                        new Row(6),
                        new Row(7),
                        new Row(8),
                        new Row(9),
                        new Row(10)));
    }

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
        final Row row = new Row(4);

        // when
        final Row moved = row.add(3);

        // then
        assertThat(moved.value()).isEqualTo(7);
    }

    @Test
    @DisplayName("다른 Row와의 절댓값 거리를 계산할 수 있다")
    void calculateDistanceTo() {
        // given
        final Row row1 = new Row(3);
        final Row row2 = new Row(7);

        // when
        final Distance distance = row1.distanceTo(row2);

        // then
        assertThat(distance).isEqualTo(new Distance(4, 0));
    }

    @Test
    @DisplayName("다른 Row와의 벡터를 계산할 수 있다")
    void calculateVectorTo() {
        // given
        final Row from = new Row(8);
        final Row to = new Row(5);

        // when
        final Vector vector = from.vectorTo(to);

        // then
        assertThat(vector).isEqualTo(new Vector(-3, 0));
    }
}
