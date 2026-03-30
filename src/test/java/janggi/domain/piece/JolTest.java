package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.point.Point;
import janggi.domain.status.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JolTest {

    @Test
    @DisplayName("초나라의 졸은 아래로는 못간다")
    void cho_can_move() {
        // given
        Piece piece = new Jol(Team.CHO);
        Point from = Point.of(3, 3);
        Point to = Point.of(3,2);

        // when & then
        assertThatThrownBy(() -> piece.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("한나라의 졸은 위로는 못간다")
    void han_can_move() {
        // given
        Piece piece = new Jol(Team.HAN);
        Point from = Point.of(3, 3);
        Point to = Point.of(3,4);

        // when & then
        assertThatThrownBy(() -> piece.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece jol = new Jol(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(7, 1);

        // when & then
        assertThatThrownBy(() -> jol.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 안에서는 대각선으로 이동 할 수 있다.")
    void can_move_diagonal() {
        Piece jol = new Jol(Team.HAN);
        Point from = Point.of(5, 2);
        Point to = Point.of(4, 1);
        assertThat(jol.getRoutePoints(from, to)
                .getPoints()
                .getFirst()
                .getColumn()
        ).isEqualTo(4);
        assertThat(jol.getRoutePoints(from, to)
                .getPoints()
                .getFirst()
                .getRow()
        ).isEqualTo(1);
    }

    @ParameterizedTest
    @DisplayName("궁성 안에서 한나라는 위로 대각선은 못간다.")
    @CsvSource(value = {"4:1:5:2", "4:1:3:2"}, delimiter = ':')
    void can_not_move_to_up_diagonal(int fromColumn, int fromRow, int toColumn, int toRow) {
        Piece jol = new Jol(Team.HAN);
        Point from = Point.of(fromColumn, fromRow);
        Point to = Point.of(toColumn, toRow);
        assertThatThrownBy(() -> jol.getRoutePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
    }
}
