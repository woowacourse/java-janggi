package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ChaTest {
    @Test
    @DisplayName("차 기물 생성 테스트")
    public void test1() {
        String team = "red";

        Cha cha = new Cha(team);

        assertThat(cha.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("차 이동 가능 여부 판별 테스트")
    class ChaMovableTest {
        @Test
        @DisplayName("차 이동 가능 여부 판별 테스트")
        public void test2() {
            Cha cha = new Cha("red");
            assertThat(cha.isValidPoint(0, 0, 100, 0)).isTrue();
        }

        @Test
        @DisplayName("차 이동 불가능 여부 판별 테스트")
        public void test3() {
            Cha cha = new Cha("red");
            assertThat(cha.isValidPoint(0, 0, 10, 10)).isFalse();
        }
    }

    @Nested
    @DisplayName("차 이동 경로 계산 테스트")
    class ChaCalculatePathTest {
        @Test
        @DisplayName("수직 테스트")
        public void test1() {
            Cha cha = new Cha("red");
            Point point1 = new Point(0, 1);
            Point point2 = new Point(0, 2);
            Point point3 = new Point(0, 3);
            Point point4 = new Point(0, 4);
            Point point5 = new Point(0, 7);

            Path path = cha.calculatePath(0, 0, 0, 7);

            assertAll(
                    () -> assertThat(path.contains(point1)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue(),
                    () -> assertThat(path.contains(point3)).isTrue(),
                    () -> assertThat(path.contains(point4)).isTrue(),
                    () -> assertThat(path.contains(point5)).isTrue()
            );
        }

        @Test
        @DisplayName("수평 테스트")
        public void test2() {
            Cha cha = new Cha("red");
            Point point1 = new Point(1, 0);
            Point point2 = new Point(2, 0);
            Point point3 = new Point(3, 0);
            Point point4 = new Point(6, 0);
            Point point5 = new Point(7, 0);

            Path path = cha.calculatePath(0, 0, 7, 0);

            assertAll(
                    () -> assertThat(path.contains(point1)).isTrue(),
                    () -> assertThat(path.contains(point2)).isTrue(),
                    () -> assertThat(path.contains(point3)).isTrue(),
                    () -> assertThat(path.contains(point4)).isTrue(),
                    () -> assertThat(path.contains(point5)).isTrue()
            );
        }
    }
}
