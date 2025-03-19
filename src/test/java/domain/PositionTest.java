package domain;

import domain.pattern.Pattern;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {
    @Test
    void 좌표를_위로_한_칸_이동시킬_수_있다() {
        // given
        int beforeX = 0;
        int beforeY = 1;
        int afterX = 9;
        int afterY = 1;

        Position beforePosition = new Position(beforeX, beforeY);
        Position afterPosition = new Position(afterX, afterY);

        // when
        Position newPosition = beforePosition.moveOnePosition(Pattern.UP);

        // then
        Assertions.assertThat(newPosition).isEqualTo(afterPosition);
    }

    @Test
    void 좌표를_이동시킬_수_있다() {
        // given
        int beforeX = 0;
        int beforeY = 1;
        int afterX = 7;
        int afterY = 3;

        Position beforePosition = new Position(beforeX, beforeY);
        Position afterPosition = new Position(afterX, afterY);

        // when
        Position newPosition = beforePosition.move(
                List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT, Pattern.DIAGONAL_UP_RIGHT));

        // then
        Assertions.assertThat(newPosition).isEqualTo(afterPosition);
    }

    @Test
    void x좌표끼리_비교하여_더_큰_좌표를_찾을_수_있다() {
        // given
        int beforeX = 0;
        int beforeY = 1;
        int afterX = 2;
        int afterY = 1;

        Position beforePosition = new Position(beforeX, beforeY);
        Position afterPosition = new Position(afterX, afterY);

        // when
        boolean isBiggerX = afterPosition.isBiggerXThan(beforePosition);

        // then
        Assertions.assertThat(isBiggerX).isFalse();
    }

    @Test
    void y좌표끼리_비교하여_더_큰_좌표를_찾을_수_있다() {
        // given
        int beforeX = 0;
        int beforeY = 1;
        int afterX = 1;
        int afterY = 3;

        Position beforePosition = new Position(beforeX, beforeY);
        Position afterPosition = new Position(afterX, afterY);

        // when
        boolean isBiggerY = afterPosition.isBiggerYThan(beforePosition);

        // then
        Assertions.assertThat(isBiggerY).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"0, 8, 2", "3, 7, 4", "8, 0, 2"})
    void x좌표끼리의_차이를_구할_수_있다(int beforeX, int afterX, int expected) {
        // given
        int beforeY = 1;
        int afterY = 1;

        Position beforePosition = new Position(beforeX, beforeY);
        Position afterPosition = new Position(afterX, afterY);

        // when
        int gap = afterPosition.getXGap(beforePosition);

        // then
        Assertions.assertThat(gap).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"9, 8, 1", "3, 7, 4", "1, 8, 7"})
    void y좌표끼리의_차이를_구할_수_있다(int beforeY, int afterY, int expected) {
        // given
        int beforeX = 1;
        int afterX = 1;

        Position beforePosition = new Position(beforeX, beforeY);
        Position afterPosition = new Position(afterX, afterY);

        // when
        int gap = afterPosition.getYGap(beforePosition);

        // then
        Assertions.assertThat(gap).isEqualTo(expected);
    }
}
