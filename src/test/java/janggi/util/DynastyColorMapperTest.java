package janggi.util;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.util.DynastyColorMapper.BLUE_COLOR;
import static janggi.util.DynastyColorMapper.RED_COLOR;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.dynasty.Dynasty;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DynastyColorMapperTest {

    @ParameterizedTest
    @MethodSource("나라별로_색상을_매핑하는_테스트_케이스")
    public void 나라별로_색상을_매핑하는_테스트(Dynasty dynasty, String color) {
        // when & then
        assertThat(DynastyColorMapper.from(dynasty)).isEqualTo(color);
    }

    private static Stream<Arguments> 나라별로_색상을_매핑하는_테스트_케이스() {
        return Stream.of(
                Arguments.of(HAN, RED_COLOR),
                Arguments.of(CHO, BLUE_COLOR)
        );
    }

}
