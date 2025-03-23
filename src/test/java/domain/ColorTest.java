package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ColorTest {

    @EnumSource(Color.class)
    @ParameterizedTest
    void 팀은_홍팀과_청팀이_있다(Color color) {
        assertThat(color).isEqualByComparingTo(color);
    }

}
