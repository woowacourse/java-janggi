package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.piece.strategy.InnerElephantStrategy;
import domain.piece.strategy.LeftElephantStrategy;
import domain.piece.strategy.OuterElephantStrategy;
import domain.piece.strategy.RightElephantStrategy;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SetupOptionTest {

    static Stream<Arguments> HorseElephantSetupStrategyTest() {
        return Stream.of(
                Arguments.of(
                        "1",
                        new InnerElephantStrategy()
                ),
                Arguments.of(
                        "2",
                        new OuterElephantStrategy()
                ),
                Arguments.of(
                        "3",
                        new RightElephantStrategy()
                ),
                Arguments.of(
                        "4",
                        new LeftElephantStrategy()
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("옵션에 해당하는 상차림 객체를 생성한다")
    void HorseElephantSetupStrategyTest(String option, HorseElephantSetupStrategy expectedClass) {
        // when
        HorseElephantSetupStrategy actual = SetupOption.findSetupStrategy(option);

        // then
        assertThat(actual).hasSameClassAs(expectedClass);
    }
}
