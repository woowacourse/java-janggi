package domain;

import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    @DisplayName("좌표가 범위를 벗어나는 경우에 에러가 발생한다")
    void shouldThrowExceptionWhenCoordinateIsOutOfBounds() {
        int outOfIndexY = 10;
        int outOfIndexX = 9;

        Assertions.assertThatThrownBy(() -> {
            new Point(outOfIndexY, outOfIndexX);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("좌표가 같으면 동등한 객체로 취급한다")
    void shouldBeEqualWhenPointsAreSame() {
        int y = 3;
        int x = 3;

        Point actual = new Point(y, x);
        Point expected = new Point(y, x);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("(0, 0)과 (0, 7)은 Y축이 같아야 한다")
    void returnTrueWhenFilesMatch(){
        int y = 0;
        int seven = 7;
        int zero = 0;

        Assertions.assertThat(new Point(y, seven).isSameFile(new Point(y, zero)))
                .isTrue();
    }

    @Test
    @DisplayName("(0, 0)과 (7, 0)은 X축이 같아야 한다")
    void returnTrueWhenRowsMatch(){
        int x = 0;
        int seven = 7;
        int zero = 0;

        Assertions.assertThat(new Point(seven, x).isSameRow(new Point(zero, x)))
                .isTrue();
    }

}
