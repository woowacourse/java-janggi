package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Point;
import janggi.domain.status.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JolTest {

    @Test
    @DisplayName("초나라의 졸은 아래로는 못간다")
    void cho_can_move() {
        // given
        Piece piece = new Jol(Team.CHO, "졸");
        Point from = Point.of(3, 3);
        Point to = Point.of(3,2);

        // when & then
        assertThatThrownBy(() -> piece.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("한나라의 졸은 위로는 못간다")
    void han_can_move() {
        // given
        Piece piece = new Jol(Team.HAN, "졸");
        Point from = Point.of(3, 3);
        Point to = Point.of(3,4);

        // when & then
        assertThatThrownBy(() -> piece.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece jol = new Jol(Team.CHO, "졸");
        Point from = Point.of(0,0);
        Point to = Point.of(7, 1);

        // when & then
        assertThatThrownBy(() -> jol.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
