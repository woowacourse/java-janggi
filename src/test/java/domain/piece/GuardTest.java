package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;

class GuardTest {

    @DisplayName("한나라 팀의 사는 특정 규칙에 따라 움직일 수 있다.")
    @Nested
    class HanGuardTest {

        private Guard guard;

        @BeforeEach
        void setUp() {
            guard = PieceFactory.createRedTeam(Guard::new);
        }

        @DisplayName("사는 궁성 내에서 대각선으로 한 칸 이동할 수 있다.")
        @Test
        void move_4_8_to_3_9() {
            assertThat(guard.isMovable(Point.newInstance(4, 8), Point.newInstance(3, 9))).isTrue();
        }

        @DisplayName("사는 궁성 내에서 아래로 한 칸 이동할 수 있다.")
        @Test
        void move_4_8_to_4_7() {
            assertThat(guard.isMovable(Point.newInstance(4, 8), Point.newInstance(4, 7))).isTrue();
        }

        @DisplayName("사는 궁성 내에서 왼쪽으로 한 칸 이동할 수 있다.")
        @Test
        void move_5_8_to_4_8() {
            assertThat(guard.isMovable(Point.newInstance(5, 8), Point.newInstance(4, 8))).isTrue();
        }

        @DisplayName("사는 궁성 내에서 오른쪽으로 한 칸 이동할 수 있다.")
        @Test
        void move_5_9_to_5_8() {
            assertThat(guard.isMovable(Point.newInstance(4, 9), Point.newInstance(5, 9))).isTrue();
        }

        @DisplayName("사는 2칸 이상 이동할 수 없다.")
        @Test
        void cannot_move_4_8_to_4_6() {
            assertThat(guard.isMovable(Point.newInstance(4, 8), Point.newInstance(4, 6))).isFalse();
        }

        @DisplayName("사는 궁성 밖으로 이동할 수 없다.")
        @Test
        void cannot_move_4_8_to_2_6() {
            assertThat(guard.isMovable(Point.newInstance(4, 8), Point.newInstance(2, 6))).isFalse();
        }

        @DisplayName("사는 궁성 밖으로 이동할 수 없다.")
        @Test
        void cannot_move_5_7_to_5_6() {
            assertThat(guard.isMovable(Point.newInstance(5, 7), Point.newInstance(5, 6))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선으로 이동할 수 없다.")
        @Test
        void cannot_move_4_7_to_3_8() {
            assertThat(guard.isMovable(Point.newInstance(4, 7), Point.newInstance(3, 8))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선으로 이동할 수 없다.")
        @Test
        void cannot_move_3_8_to_4_9() {
            assertThat(guard.isMovable(Point.newInstance(3, 8), Point.newInstance(4, 9))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선 이동을 할 수 없다.")
        @Test
        void cannot_move_4_9_to_5_8() {
            assertThat(guard.isMovable(Point.newInstance(4, 9), Point.newInstance(5, 8))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선 이동을 할 수 없다.")
        @Test
        void cannot_move_5_8_to_4_7() {
            assertThat(guard.isMovable(Point.newInstance(5, 8), Point.newInstance(4, 7))).isFalse();
        }
    }

    @DisplayName("초나라 팀의 사는 특정 규칙에 따라 움직일 수 있다.")
    @Nested
    class ChoGuardTest {

        private Guard guard;

        @BeforeEach
        void setUp() {
            guard = PieceFactory.createGreenTeam(Guard::new);
        }

        @DisplayName("사는 궁성 내에서 대각선으로 이동할 수 있다.")
        @Test
        void move_4_1_to_3_0() {
            assertThat(guard.isMovable(Point.newInstance(4, 1), Point.newInstance(3, 0))).isTrue();
        }

        @DisplayName("경비병은 궁성 내에서 오른쪽으로 1칸 이동할 수 있다.")
        @Test
        void move_4_1_to_4_2() {
            assertThat(guard.isMovable(Point.newInstance(4, 1), Point.newInstance(4, 2))).isTrue();
        }

        @DisplayName("사는 궁성 밖으로 이동할 수 없다.")
        @Test
        void cannot_move_4_2_to_4_3() {
            assertThat(guard.isMovable(Point.newInstance(4, 2), Point.newInstance(4, 3))).isFalse();
        }

        @DisplayName("초나라 사는 한나라 궁성 영역으로 이동할 수 없다.")
        @Test
        void cannot_move_5_2_to_6_3() {
            assertThat(guard.isMovable(Point.newInstance(5, 2), Point.newInstance(6, 3))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선 이동을 할 수 없다.")
        @Test
        void cannot_move_4_0_to_3_1() {
            assertThat(guard.isMovable(Point.newInstance(4, 0), Point.newInstance(3, 1))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선 이동을 할 수 없다.")
        @Test
        void cannot_move_3_1_to_4_2() {
            assertThat(guard.isMovable(Point.newInstance(3, 1), Point.newInstance(4, 2))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선 이동을 할 수 없다.")
        @Test
        void cannot_move_4_2_to_5_1() {
            assertThat(guard.isMovable(Point.newInstance(4, 2), Point.newInstance(5, 1))).isFalse();
        }

        @DisplayName("사는 궁성 내에서 길이 없는 대각선 이동을 할 수 없다.")
        @Test
        void cannot_move_5_1_to_4_0() {
            assertThat(guard.isMovable(Point.newInstance(5, 1), Point.newInstance(4, 0))).isFalse();
        }
    }
}
