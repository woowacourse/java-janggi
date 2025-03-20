package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.HanPoint;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KnightTest {

    @DisplayName("마가 가는 방향에 기물이 없다면 이동할 수 있다.")
    @Test
    void test() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());
        Piece knight = new Knight();

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(2, 3));

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("마가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void test2() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());
        Piece knight = new Knight();

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(2, 2));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("마가 가는 방향에 기물이 있다면 이동할 수 없다.")
    @Test
    void test3() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new HanPoint(1, 2), new Knight(), Dynasty.HAN)
        ));
        Piece knight = new Knight();

        // when
        boolean isMovable = knight.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(2, 3));

        // then
        assertThat(isMovable)
                .isFalse();
    }
}