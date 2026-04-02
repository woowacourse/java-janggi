package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(point.getX()).isEqualTo(column);
        assertThat(point.getY()).isEqualTo(row);
    }

    @Test
    @DisplayName("보드의 범위가 벗어난 곳에서 Point 생성시 예외 발생")
    void validate_of() {
        // given
        int column = 10;
        int row = 10;

        // when & then
        assertThatThrownBy(() -> Point.of(column, row))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 위치 범위입니다.");
    }

    @Test
    @DisplayName("from, to의 위치가 궁성 내에 있는지 판단")
    void is_in_palace() {
        // given
        Point from = Point.of(3, 1);
        Point to = Point.of(4, 1);

        // when
        boolean result = from.isInSamePalace(to);

        // then
        assertThat(result).isTrue();
    }
}
