package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ByeongTest {
    @Test
    @DisplayName("병 기물 생성 테스트")
    public void test1() {
        Team team = Team.RED;

        Byeong byeong = new Byeong(team);

        assertThat(byeong.getTeam()).isEqualTo(team);
    }

    @Nested
    @DisplayName("병 이동 가능 여부 판별 테스트")
    class ByeongMovableTest {
        @Test
        @DisplayName("한나라 병 이동 가능 테스트")
        public void test1() {
            Byeong byeong = new Byeong(Team.RED);
            assertAll(
                    () -> assertThat(byeong.isValidPoint(0, 0, 0, 1)).isTrue(),
                    () -> assertThat(byeong.isValidPoint(0, 0, 0, -1)).isFalse()
            );
        }

        @Test
        @DisplayName("초나라 병 이동 가능 테스트")
        public void test2() {
            Byeong byeong = new Byeong(Team.BLUE);
            assertAll(
                    () -> assertThat(byeong.isValidPoint(0, 0, 0, 1)).isFalse(),
                    () -> assertThat(byeong.isValidPoint(0, 0, 0, -1)).isTrue()
            );
        }

        @Test
        @DisplayName("병 이동 불가능 테스트")
        public void test3() {
            Byeong byeong = new Byeong(Team.RED);
            assertThat(byeong.isValidPoint(0, 0, 2, 0)).isFalse();
        }
    }

    @Nested
    @DisplayName("병 이동 경로 계산 테스트")
    class ByeongCalculatePathTest {
        @Test
        @DisplayName("수직")
        public void test1() {
            Byeong byeong = new Byeong(Team.RED);
            Point point = new Point(0, 1);

            assertThat(byeong.calculatePath(0, 0, 0, 1).contains(point)).isTrue();
        }

        @Test
        @DisplayName("수평")
        public void test2() {
            Byeong byeong = new Byeong(Team.RED);
            Point point = new Point(1, 0);

            assertThat(byeong.calculatePath(0, 0, 1, 0).contains(point)).isTrue();
        }
    }
}
