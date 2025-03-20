package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.HanPoint;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @DisplayName("상이 가는 방향에 기물이 없다면 이동할 수 있다.")
    @Test
    void test() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());
        Piece elephant = new Elephant();

        // when
        boolean isMovable = elephant.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(4, 3));

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("상이 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void test2() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());
        Piece elephant = new Elephant();

        // when
        boolean isMovable = elephant.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(4, 2));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("상이 가는 방향에 기물이 있다면 이동할 수 없다.")
    @Test
    void test3() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new HanPoint(2, 1), new Horse(), Dynasty.HAN)
        ));
        Piece elephant = new Elephant();

        // when
        boolean isMovable = elephant.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(4, 3));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void test4() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(4, 3), new Horse(), Dynasty.CHU)
        ));
        Elephant elephant = new Elephant();

        // when
        boolean isMovable = elephant.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(4, 3));

        // then
        assertThat(isMovable)
                .isTrue();
    }
}