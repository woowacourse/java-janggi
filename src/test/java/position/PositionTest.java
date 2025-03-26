package position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import position.Movement;
import position.Row;

public class PositionTest {

    @Test
    void 행과_열을_통해_위치를_생성할_수_있다() {
        // expected
        Position position = new Position(Column.A, Row.ONE);

        // then
        assertThat(position)
                .extracting("column", "row")
                .containsExactly(Column.A, Row.ONE);
    }


    @Test
    void 포지션을_움직일_수_있다() {
        // given
        Position position = new Position(Column.E, Row.FIVE);

        // when
        Position resultPosition = position.move(Movement.UP);

        // then
        assertThat(resultPosition).isEqualTo(new Position(Column.E, Row.SIX));

    }


}
