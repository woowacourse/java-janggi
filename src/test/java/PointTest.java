
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
}
