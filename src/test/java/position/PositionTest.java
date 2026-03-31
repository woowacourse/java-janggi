package position;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 특정_좌표의_포지션을_생성한다() {
        // given
        Position position = new Position(1, 1);
        // when & then
        assertThat(position.row()).isEqualTo(1);
        assertThat(position.column()).isEqualTo(1);
    }
}
