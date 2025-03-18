package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.TeamColor;
import janggi.point.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ChaTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Cha cha = new Cha(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 4);

            assertThat(cha.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Cha cha = new Cha(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(6, 8);

            assertThat(cha.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Cha cha = new Cha(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(4, 6);

            assertThat(cha.isMovable(targetPoint)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Cha cha = new Cha(TeamColor.BLUE, new Point(6, 6));

            Point targetPoint = new Point(8, 6);

            assertThat(cha.isMovable(targetPoint)).isTrue();
        }
    }
}
