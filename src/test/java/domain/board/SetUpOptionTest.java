package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import application.SetUpOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SetUpOptionTest {
    @Test
    @DisplayName("상차림 번호 1을 왼상차림으로 변환한다.")
    void findOne() {
        SetUp setUp = SetUpOption.from(1);

        assertThat(setUp).isEqualTo(SetUp.LEFT_ELEPHANT);
    }

    @Test
    @DisplayName("상차림 번호 2를 오른상차림으로 변환한다.")
    void findTwo() {
        SetUp setUp = SetUpOption.from(2);

        assertThat(setUp).isEqualTo(SetUp.RIGHT_ELEPHANT);
    }

    @Test
    @DisplayName("상차림 번호 3을 안상차림으로 변환한다.")
    void findThree() {
        SetUp setUp = SetUpOption.from(3);

        assertThat(setUp).isEqualTo(SetUp.INNER_ELEPHANT);
    }

    @Test
    @DisplayName("상차림 번호 4를 바깥상차림으로 변환한다.")
    void findFour() {
        SetUp setUp = SetUpOption.from(4);

        assertThat(setUp).isEqualTo(SetUp.OUTER_ELEPHANT);
    }

    @Test
    @DisplayName("상차림 번호가 1에서 4 사이가 아니면 예외가 발생한다.")
    void invalidSetUpNumber() {
        assertThatThrownBy(() -> SetUpOption.from(5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상차림 번호는 1, 2, 3, 4 중 하나여야 합니다.");
    }
}
