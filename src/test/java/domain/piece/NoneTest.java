package domain.piece;

import static domain.piece.None.DOESNT_HAVE_TEAM;

import common.exception.JanggiException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NoneTest {

    @Test
    void getTeam함수를_호출하면_에러를_던진다() {
        None none = new None();
        Assertions.assertThatThrownBy(() -> none.getTeam())
                .isInstanceOf(JanggiException.class)
                .hasMessageContaining(DOESNT_HAVE_TEAM);
    }
}
