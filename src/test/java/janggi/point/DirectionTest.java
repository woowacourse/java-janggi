package janggi.point;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Nested
    @DisplayName("상하좌우 경로 테스트")
    class CardinalTest {

        @Test
        @DisplayName("북쪽으로 한 번 이동")
        void directNorth() {
            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(5, 6);

            assertThat(Direction.cardinalFrom(startPoint, targetPoint)).isEqualTo(Direction.NORTH);
        }

        @Test
        @DisplayName("남쪽으로 한 번 이동")
        void directSouth() {
            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(7, 6);

            assertThat(Direction.cardinalFrom(startPoint, targetPoint)).isEqualTo(Direction.SOUTH);
        }

        @Test
        @DisplayName("서쪽으로 한 번 이동")
        void directWest() {
            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 5);

            assertThat(Direction.cardinalFrom(startPoint, targetPoint)).isEqualTo(Direction.WEST);
        }

        @Test
        @DisplayName("동쪽으로 한 번 이동")
        void directEast() {
            Point startPoint = new Point(6, 6);
            Point targetPoint = new Point(6, 7);

            assertThat(Direction.cardinalFrom(startPoint, targetPoint)).isEqualTo(Direction.EAST);
        }
    }

    @Nested
    @DisplayName("대각선 포함 경로 테스트")
    class ComplexTest {

        @Test
        @DisplayName("상-우측대각선로 이동 경로를 생성할 수 있다.")
        void directNorth_NorthEast() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(2, 6);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.NORTH, Direction.NORTH_EAST);
        }

        @Test
        @DisplayName("우-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkRightLeftRouteMovable() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(3, 7);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.EAST, Direction.NORTH_EAST);
        }

        @Test
        @DisplayName("우-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkRightRightRouteMovable() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(7, 7);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.EAST, Direction.SOUTH_EAST);
        }

        @Test
        @DisplayName("하-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkDownLeftRouteMovable() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(8, 6);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.SOUTH, Direction.SOUTH_EAST);
        }

        @Test
        @DisplayName("하-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkDownRightRouteMovable() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(8, 2);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.SOUTH, Direction.SOUTH_WEST);
        }

        @Test
        @DisplayName("좌-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkLeftLeftRouteMovable() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(7, 1);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.WEST, Direction.SOUTH_WEST);
        }

        @Test
        @DisplayName("좌-우측대각선로 이동 경로를 생성할 수 있다.")
        void checkLeftRightRouteMovable() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(3, 1);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.WEST, Direction.NORTH_WEST);
        }

        @Test
        @DisplayName("상-좌측대각선로 이동 경로를 생성할 수 있다.")
        void checkUpLeftRouteMovable() {
            Point startPoint = new Point(5, 4);
            Point targetPoint = new Point(2, 2);

            List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2);

            assertThat(directions).containsExactly(Direction.NORTH, Direction.NORTH_WEST);
        }
    }
}
