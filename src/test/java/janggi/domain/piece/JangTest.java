package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.point.Point;
import janggi.domain.status.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JangTest {

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece jang = new Jang(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(7, 1);

        // when & then
        assertThatThrownBy(() -> jang.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장은 궁성 밖으로 나갈 수 없습니다.")
    void can_not_move_outside_castle() {
        Piece jang = new Jang(Team.CHO);
        Point from = Point.of(3, 0);
        Point to = Point.of(2, 0);

        assertThatThrownBy(() -> jang.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 장은 궁성 밖으로 나갈 수 없습니다.");
    }
}
