package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Point;
import janggi.domain.status.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaTest {

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece sa = new Sa(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(7, 1);

        // when & then
        assertThatThrownBy(() -> sa.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내에서 이동하지 않을 시 예외 발생")
    void not_in_palace() {
        // given
        Piece jang = new Sa(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(1, 1);

        // when & then
        assertThatThrownBy(() -> jang.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("궁성");
    }
}
