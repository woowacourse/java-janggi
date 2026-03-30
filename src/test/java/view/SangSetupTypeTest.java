package view;

import static org.assertj.core.api.Assertions.assertThat;

import board.InnerSangSetup;
import board.LeftSangSetup;
import board.OuterSangSetup;
import board.RightSangSetup;
import board.SangSetup;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SangSetupTypeTest {

    private static final Map<Integer, SangSetup> SANG_SETUP_MAPPER = Map.of(
        1, new LeftSangSetup(),
        2, new RightSangSetup(),
        3, new InnerSangSetup(),
        4, new OuterSangSetup()
    );

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void 인풋_번호에_매칭되는_상차림_객체를_반환한다(int inputNumber) {
        // when
        SangSetup sangSetup = SangSetupType.from(inputNumber);
        // then
        assertThat(sangSetup).isInstanceOf(SANG_SETUP_MAPPER.get(inputNumber).getClass());
    }
}