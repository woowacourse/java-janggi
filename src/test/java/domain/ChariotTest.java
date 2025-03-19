package domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotTest {

    @DisplayName("차(車)는 현재 위치에서 한 방향으로 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test() {
        // given
        Piece chariot = new Chariot();
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation target = new BoardLocation(1, 2);

        // when
        boolean isMovable = chariot.isMovable(current, target);

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("차(車)는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        Piece chariot = new Chariot();
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation target = new BoardLocation(2, 2);

        // when
        boolean isMovable = chariot.isMovable(current, target);

        // then
        assertThat(isMovable).isFalse();
    }
}
