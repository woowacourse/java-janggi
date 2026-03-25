package domain;

import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    void 좌표가_범위를_벗어나는_경우에_에러가_발생한다(){
        int outOfIndexY = 10;
        int outOfIndexX = 9;

        Assertions.assertThatThrownBy(() -> {
            new Point(outOfIndexY, outOfIndexX);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 좌표가_같으면_동등한_객체로_취급한다(){
        int y = 3;
        int x = 3;

        Point actual = new Point(y, x);
        Point expected = new Point(y, x);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }
}
