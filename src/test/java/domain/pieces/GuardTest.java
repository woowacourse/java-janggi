package domain.pieces;

import domain.Point;
import domain.Team;
import execptions.JanggiArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardTest {
    @Test
    @DisplayName("신하 대한 이동 여부를 확인 하고자 하면 예외를 던진다")
    void test_throwException() {
        // given
        Guard guard = new Guard(Team.CHO);

        // when & then
        assertThatThrownBy(() -> guard.isAbleToArrive(new Point(3, 2), new Point(2, 3)))
                .isInstanceOf(JanggiArgumentException.class);
    }
}
