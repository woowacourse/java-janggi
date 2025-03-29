package janggi.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ColumnTest {

    @Test
    @DisplayName("defaults를 통해서 기본 열들을 생성할 수 있다")
    void defaults() {
        // given
        // when
        final List<Column> defaults = Column.defaults();

        // then
        assertThat(defaults).containsExactlyElementsOf(
                List.of(new Column(1),
                        new Column(2),
                        new Column(3),
                        new Column(4),
                        new Column(5),
                        new Column(6),
                        new Column(7),
                        new Column(8),
                        new Column(9)));
    }

    @Test
    @DisplayName("Column은 1부터 9까지의 정수만 허용한다")
    void validateRange() {
        // given
        // when
        // then
        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> new Column(0));
            assertThrows(IllegalArgumentException.class, () -> new Column(10));

            assertThat(new Column(1).value()).isEqualTo(1);
            assertThat(new Column(5).value()).isEqualTo(5);
            assertThat(new Column(9).value()).isEqualTo(9);
        });
    }

    @Test
    @DisplayName("add(delta)를 통해 Column을 이동할 수 있다")
    void addColumn() {
        // given
        final Column column = new Column(4);

        // when
        final Column moved = column.add(2);

        // then
        assertThat(moved.value()).isEqualTo(6);
    }

    @Test
    @DisplayName("다른 Column과의 절댓값 거리를 계산할 수 있다")
    void calculateDistanceTo() {
        // given
        final Column col1 = new Column(2);
        final Column col2 = new Column(6);

        // when
        final Distance distance = col1.distanceTo(col2);


        // then
        assertThat(distance).isEqualTo(new Distance(0, 4));
    }

    @Test
    @DisplayName("다른 Column과의 벡터를 계산할 수 있다")
    void calculateVectorTo() {
        // given
        final Column from = new Column(3);
        final Column to = new Column(1);

        // when
        final Vector vector = from.vectorTo(to);

        // then
        assertThat(vector).isEqualTo(new Vector(0, -2));
    }
}
