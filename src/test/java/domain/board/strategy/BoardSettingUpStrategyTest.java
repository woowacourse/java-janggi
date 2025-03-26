package domain.board.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.BoardSettingUpStrategy;
import domain.board.SettingUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class BoardSettingUpStrategyTest {

    @DisplayName("상차림 타입을 통해 상차림 전략을 가져온다.")
    @ParameterizedTest
    @EnumSource(SettingUp.class)
    void boardSettingUpStrategyTest(SettingUp settingUp) {
        BoardSettingUpStrategy strategy = settingUp.getStrategy();

        assertThat(strategy).isInstanceOf(BoardSettingUpStrategy.class);
    }


}
