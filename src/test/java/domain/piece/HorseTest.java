package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;

class HorseTest {

    @Nested
    class HorseMoveTest {

        private Horse horse;

        @BeforeEach
        void setUp() {
            horse = PieceFactory.createRedTeam(Horse::new);
        }

        @DisplayName("마는 1칸 위로, 1칸 오른쪽 대각선 위로 이동할 수 있다.")
        @Test
        void move_2_2_to_3_4() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(3, 4))).isTrue();
        }

        @DisplayName("마는 1칸 위로, 1칸 왼쪽 대각선 위로 이동할 수 있다.")
        @Test
        void move_2_2_to_1_4() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(1, 4))).isTrue();
        }

        @DisplayName("마는 1칸 아래로, 1칸 오른쪽 대각선 아래로 이동할 수 있다.")
        @Test
        void move_2_2_to_3_0() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(3, 0))).isTrue();
        }

        @DisplayName("마는 1칸 아래로, 1칸 왼쪽 대각선 아래로 이동할 수 있다.")
        @Test
        void move_2_2_to_1_0() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(1, 0))).isTrue();
        }

        @DisplayName("마는 1칸 오른쪽으로, 1칸 오른쪽 위 대각선으로 이동할 수 있다.")
        @Test
        void move_2_2_to_4_3() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(4, 3))).isTrue();
        }

        @DisplayName("마는 1칸 왼쪽으로, 1칸 왼쪽 위 대각선으로 이동할 수 있다.")
        @Test
        void move_2_2_to_0_3() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(0, 3))).isTrue();
        }

        @DisplayName("마는 1칸 오른쪽으로, 1칸 오른쪽 아래 대각선으로 이동할 수 있다.")
        @Test
        void move_2_2_to_4_1() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(4, 1))).isTrue();
        }

        @DisplayName("마는 1칸 왼쪽으로, 1칸 왼쪽 아래 대각선으로 이동할 수 있다.")
        @Test
        void move_2_2_to_0_1() {
            assertThat(horse.isMovable(Point.newInstance(2, 2), Point.newInstance(0, 1))).isTrue();
        }

        @DisplayName("마는 1칸 아래로 이동할 수 없다.")
        @Test
        void cannot_move_1_1_to_1_0() {
            assertThat(horse.isMovable(Point.newInstance(1, 1), Point.newInstance(1, 0))).isFalse();
        }

        @DisplayName("마는 시작 위치와 같은 위치로는 이동할 수 없다.")
        @Test
        void cannot_move_1_1_to_1_1() {
            assertThat(horse.isMovable(Point.newInstance(1, 1), Point.newInstance(1, 1))).isFalse();
        }

        @DisplayName("마는 대각선으로 여러 칸 이동할 수 없다.")
        @Test
        void cannot_move_1_1_to_8_8() {
            assertThat(horse.isMovable(Point.newInstance(1, 1), Point.newInstance(8, 8))).isFalse();
        }
    }


    @Test
    void 말의_이동_가능_경로_모두_반환() {

        // given
        final Horse horse = PieceFactory.createGreenTeam(Horse::new);

        // when
        final List<Point> possiblePoint = horse.calculatePossiblePoint(Point.newInstance(2, 0),
                Point.newInstance(3, 2));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(1);
            softly.assertThat(possiblePoint.getFirst()).isEqualTo(Point.newInstance(2, 1));
        });
    }
}
