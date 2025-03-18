package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.TeamColor;
import janggi.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SangTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("상-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpRightMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(2, 6);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우-좌측대각선로 이동할 수 있다면 true를 반환한다.")
        void checkRightLeftMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(3, 7);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkRightRightMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(7, 7);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownLeftMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(8, 6);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkDownRightMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(8, 2);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("좌-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftLeftMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(7, 1);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("좌-우측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkLeftRightMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(3, 1);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상-좌측대각선으로 이동할 수 있다면 true를 반환한다.")
        void checkUpLeftMovable() {
            Sang sang = new Sang(TeamColor.BLUE, new Point(5,4));

            Point targetPoint = new Point(2, 2);

            assertThat(sang.isMovable(targetPoint)).isTrue();
        }
    }
}
