package janggi.util;

import static janggi.util.HorseElephantPositionMapper.EHEH_ORDINAL;
import static janggi.util.HorseElephantPositionMapper.EHHE_ORDINAL;
import static janggi.util.HorseElephantPositionMapper.HEEH_ORDINAL;
import static janggi.util.HorseElephantPositionMapper.HEHE_ORDINAL;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.HorseElephantPosition;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseElephantPositionMapperTest {

    @ParameterizedTest
    @MethodSource("마와_상에대한_상차림법을_매핑하는_테스트_케이스")
    public void 마와_상에대한_상차림법을_매핑하는_테스트(int ordinal, HorseElephantPosition horseElephantPosition) {
        // when & then
        assertThat(HorseElephantPositionMapper.from(ordinal)).isEqualTo(horseElephantPosition);
    }

    private static Stream<Arguments> 마와_상에대한_상차림법을_매핑하는_테스트_케이스() {
        return Stream.of(
                Arguments.of(HEHE_ORDINAL, HorseElephantPosition.HEHE),
                Arguments.of(HEEH_ORDINAL, HorseElephantPosition.HEEH),
                Arguments.of(EHEH_ORDINAL, HorseElephantPosition.EHEH),
                Arguments.of(EHHE_ORDINAL, HorseElephantPosition.EHHE)
        );
    }

}
