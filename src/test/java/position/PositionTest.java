package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static testutil.TestConstant.A1;
import static testutil.TestConstant.A5;
import static testutil.TestConstant.A6;
import static testutil.TestConstant.B5;
import static testutil.TestConstant.C5;
import static testutil.TestConstant.D5;
import static testutil.TestConstant.E1;
import static testutil.TestConstant.E2;
import static testutil.TestConstant.E3;
import static testutil.TestConstant.E4;
import static testutil.TestConstant.E5;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    void 행이나_열이_같으면_직선상으로_판단한다() {
        // given & then
        assertThat(A1.isStraight(A6)).isTrue();
        assertThat(E5.isStraight(A5)).isTrue();
    }

    @Test
    void 직선상의_두_포지션_간의_포지션들을_Path_로_구한다() {
        // given & when
        List<Position> straightPositions = E5.findStraightPath(A5).positions();
        List<Position> straightPositions2 = E5.findStraightPath(E1).positions();

        // then
        assertAll(
                () -> Assertions.assertThat(straightPositions).containsExactly(B5, C5, D5),
                () -> Assertions.assertThat(straightPositions2).containsExactly(E2, E3, E4)
        );
    }
}
