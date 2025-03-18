package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.TeamColor;
import janggi.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SaTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Sa sa = new Sa(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 5);

            assertThat(sa.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Sa sa = new Sa(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 7);

            assertThat(sa.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Sa sa = new Sa(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(5, 6);

            assertThat(sa.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Sa sa = new Sa(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(7, 6);

            assertThat(sa.isMovable(targetPoint)).isTrue();
        }
    }
}
