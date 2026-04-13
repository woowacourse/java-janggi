package domain.piece;

import common.JanggiException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NoneTest {
    private static final String DOES_NOT_HAVE_TEAM = "빈 칸에는 팀이 없습니다.";
    private static final String CAN_NOT_MOVE = "빈 칸에는 기물이 없어 이동할 수 없습니다.";

    @Test
    void getTeam함수를_호출하면_에러를_던진다() {
        None none = new None();
        Assertions.assertThatThrownBy(() -> none.getTeam())
                .isInstanceOf(JanggiException.class)
                .hasMessageContaining(DOES_NOT_HAVE_TEAM);
    }

    @Test
    void getMovementStrategy함수를_호출하면_에러를_던진다() {
        None none = new None();
        Assertions.assertThatThrownBy(() -> none.getMovementStrategy())
                .isInstanceOf(JanggiException.class)
                .hasMessageContaining(CAN_NOT_MOVE);
    }

    @Test
    void getPathGenerator함수를_호출하면_에러를_던진다() {
        None none = new None();
        Assertions.assertThatThrownBy(() -> none.getPathGenerator())
                .isInstanceOf(JanggiException.class)
                .hasMessageContaining(CAN_NOT_MOVE);
    }
}
