package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PointTest {

    @Test
    @DisplayName("조회 시 해당 포인트가 나온다")
    void of() {
        // given
        int column = 1;
        int row = 2;

        // when
        Point point = Point.of(column, row);

        // then
        assertThat(point.getColumn()).isEqualTo(column);
        assertThat(point.getRow()).isEqualTo(row);
    }
}
