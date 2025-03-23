package janggi.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DistanceTest {

    @Test
    @DisplayName("Distance는 음수 값을 허용하지 않는다")
    void validateSign() {
        // given
        // when
        // then
        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> new Distance(-1, 0));
            assertThrows(IllegalArgumentException.class, () -> new Distance(0, -1));
            assertThrows(IllegalArgumentException.class, () -> new Distance(-3, -2));
        });
    }

    @Test
    @DisplayName("같은 축이 아닌 두 거리는 결합할 수 있다")
    void combineDistance() {
        // given
        Distance vertical = new Distance(3, 0);
        Distance horizontal = new Distance(0, 2);

        // when
        Distance combined = vertical.combine(horizontal);

        // then
        assertThat(combined).isEqualTo(new Distance(3, 2));
    }

    @Test
    @DisplayName("같은 축의 거리끼리는 결합할 수 없다")
    void combineSameAxis_shouldThrow() {
        // given
        Distance d1 = new Distance(2, 0);
        Distance d2 = new Distance(4, 0);

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> d1.combine(d2));
    }

    @Test
    @DisplayName("getTotal()은 row와 column 거리의 합을 반환한다")
    void getTotal() {
        // given
        Distance distance = new Distance(3, 4);

        // when
        int total = distance.getTotal();

        // then
        assertThat(total).isEqualTo(7);
    }

    @Test
    @DisplayName("getStraight()는 row/column 거리의 차이를 반환한다")
    void getStraight() {
        // given
        Distance distance = new Distance(5, 3);

        // when
        int straight = distance.getStraight();

        // then
        assertThat(straight).isEqualTo(2);
    }

    @Test
    @DisplayName("getDiagonal()은 row/column 거리 중 더 작은 값을 반환한다")
    void getDiagonal() {
        // given
        Distance distance = new Distance(5, 3);

        // when
        int diagonal = distance.getDiagonal();

        // then
        assertThat(diagonal).isEqualTo(3);
    }

    @Test
    @DisplayName("isVertical은 세로 거리일 경우 true를 반환한다")
    void isVertical() {
        // given
        Distance distance = new Distance(2, 0);

        // when
        // then
        assertAll(() -> {
            assertThat(distance.isVertical()).isTrue();
            assertThat(distance.isStraight()).isTrue();
        });
    }

    @Test
    @DisplayName("isHorizontal은 가로 거리일 경우 true를 반환한다")
    void isHorizontal() {
        // given
        Distance distance = new Distance(0, 3);

        // when
        // then
        assertAll(() -> {
            assertThat(distance.isHorizontal()).isTrue();
            assertThat(distance.isStraight()).isTrue();
        });
    }

    @Test
    @DisplayName("row와 column이 모두 존재하면 직선이 아니다")
    void notStraight() {
        // given
        Distance distance = new Distance(2, 2);

        // when
        // then
        assertThat(distance.isStraight()).isFalse();
    }
}
