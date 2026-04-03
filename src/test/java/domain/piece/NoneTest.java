package domain.piece;

import static domain.piece.None.CAN_NOT_MOVE;
import static domain.piece.None.DOES_NOT_HAVE_TEAM;

import common.exception.JanggiException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NoneTest {

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
