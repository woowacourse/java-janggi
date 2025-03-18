package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KnightTest {

    @DisplayName("마가 가는 방향에 기물이 없다면 이동할 수 있다.")
    @Test
    void test() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Piece knight = new Knight(Dynasty.HAN);

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new Point(1, 1), new Point(2, 3));

        // then
        assertThat(isMovable)
                .isTrue();
    }

    @DisplayName("마가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void test2() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Piece knight = new Knight(Dynasty.HAN);

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new Point(1, 1), new Point(2, 2));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("마가 가는 방향에 기물이 있다면 이동할 수 없다.")
    @Test
    void test3() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(0, 1), new Knight(Dynasty.HAN)
        ));
        Piece knight = new Knight(Dynasty.HAN);

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new Point(1, 1), new Point(2, 3));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("마가 목적지에 같은 나라의 기물이 있으면 갈 수 없다.")
    @Test
    void test4() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(2, 3), new Elephant(Dynasty.HAN)
        ));
        Piece knight = new Knight(Dynasty.HAN);

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new Point(1, 1), new Point(2, 3));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("마가 목적지에 상대 나라의 기물이 있으면 갈 수 있다.")
    @Test
    void test5() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(2, 3), new Elephant(Dynasty.CHU)
        ));
        Piece knight = new Knight(Dynasty.HAN);

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new Point(1, 1), new Point(2, 3));

        // then
        assertThat(isMovable)
                .isTrue();
    }
}