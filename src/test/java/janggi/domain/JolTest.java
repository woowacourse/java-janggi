package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JolTest {

    @Test
    @DisplayName("초나라의 졸은 아래로는 못간다")
    void cho_can_move() {
        // given
        Piece piece = new Jol(Team.CHO);
        Point from = Point.of(3, 3);
        Point to = Point.of(3,2);

        // when
        List<Point> route = piece.getRoute(from, to);
        List<Piece> pieces = board.getPieces(route);

        // then
        assertThat(route.size()).isEqualTo(0);
        assertThat(piece.canMove(pieces)).isFalse();
    }

    @Test
    @DisplayName("한나라의 졸은 위로는 못간다")
    void han_can_move() {
        // given
        Piece piece = new Jol(Team.HAN);
        Point from = Point.of(3, 3);
        Point to = Point.of(3,4);

        // when
        List<Point> route = piece.getRoute(from, to);
        List<Piece> pieces = board.getPieces(route);

        // then
        assertThat(route.size()).isEqualTo(0);
        assertThat(piece.canMove(pieces)).isFalse();
    }

    @Test
    @DisplayName("이동할 수 없는 위치를 목적지로 할 경우 예외 발생")
    void destination_exception() {
        // given
        Piece cha = new Cha(Team.CHO);
        Point from = Point.of(0,0);
        Point to = Point.of(7, 1);

        // when & then
        assertThatThrownBy(() -> cha.getRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
