package janggi.point;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PointDistanceTest {

    @Nested
    @DisplayName("두 점 사이 거리 테스트")
    class DistanceTest {

        @Test
        @DisplayName("두 점 사이가 정수일 때 계산할 수 있다.")
        void calculateWithInteger() {
            Point startPoint = new Point(5, 5);
            Point targetPoint = new Point(4, 5);

            PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

            assertThat(distance.getDistance()).isEqualTo(1);
        }

        @Test
        @DisplayName("두 점 사이가 소수일 때 계산할 수 있다.")
        void calculateWithDouble() {
            Point startPoint = new Point(5, 5);
            Point targetPoint = new Point(4, 4);

            PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

            assertThat(distance.getDistance()).isEqualTo(Math.sqrt(2));
        }
    }

    @Nested
    @DisplayName("두 거리 비교 테스트")
    class CompareTest {

        @Test
        @DisplayName("거리가 같으면 true를 반환한다.")
        void compareSameDistance() {
            PointDistance distance = new PointDistance(1);

            assertThat(distance.isSameWith(1)).isTrue();
        }

        @Test
        @DisplayName("거리가 같으면 false를 반환한다.")
        void compareDifferentDistance() {
            PointDistance distance = new PointDistance(1);

            assertThat(distance.isSameWith(2)).isFalse();
        }
    }
}
