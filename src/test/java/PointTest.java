
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PointTest {

    @ValueSource(strings = {"0,-1", "-1,0", "9,0", "0,10"})
    @ParameterizedTest
    @DisplayName("좌표 범위를 넘으면 예외를 발생시킨다")
    void test1(String str) {
        // given
        String[] split = str.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);

        // when & then
        Assertions.assertThatThrownBy(() -> new Point(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("");
    }

    @ValueSource(strings = {"0,2", "2,0", "2,9", "8,2"})
    @ParameterizedTest
    @DisplayName("수평 혹은 수직 직선에 점이 위치해 있으면 참을 반환한다")
    void test2(String str) {
        // given
        String[] split = str.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        Point point = new Point(x, y);

        Point opposite = new Point(2, 2);

        // when
        boolean b = point.isHorizontal(opposite);

        // then
        Assertions.assertThat(b).isTrue();
    }

    @ValueSource(strings = {"3,3", "1,1", "3,1", "1,3"})
    @ParameterizedTest
    @DisplayName("수평 혹은 수직 직선에 점이 위치해있지 않으면 거짓을 반환한다")
    void test3(String str) {
        // given
        String[] split = str.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        Point point = new Point(x, y);

        Point opposite = new Point(2, 2);

        // when
        boolean b = point.isHorizontal(opposite);

        // then
        Assertions.assertThat(b).isFalse();
    }
}
