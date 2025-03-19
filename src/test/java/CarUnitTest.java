import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CarUnitTest {

    @Test
    @DisplayName("한 방향으로 이동할 수 있다.")
    void test1() {
        // given
        Point point = new Point(0, 0);
        CarUnit carUnit = new CarUnit(point, "한나라");

        Point movedPoint = new Point(0, 5);

        // when
        CarUnit movedCarUnit = carUnit.move(movedPoint);

        // then
        Assertions.assertThat(movedCarUnit).isEqualTo(new CarUnit(new Point(0, 5), "한나라"));
    }

    @Test
    @DisplayName("대각선으로 이동하려고 할 시 예외를 발생시킨다.")
    void test2() {
        // given
        Point point = new Point(0, 0);
        CarUnit carUnit = new CarUnit(point, "한나라");

        Point movedPoint = new Point(1, 5);

        // when & then
        Assertions.assertThatThrownBy(() -> carUnit.move(movedPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("");
    }
}
