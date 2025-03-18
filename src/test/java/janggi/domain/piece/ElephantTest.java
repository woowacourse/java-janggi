package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, -1, 4", "1, 2, 3, 0", "1, 2, -1, 0"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_3이_아니라면_움직일_수_없다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 5", "1, 2, -1, 5", "1, 2, 3, -1", "1, 2, -1, -1"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_3이라면_움직일_수_있다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 4, 3", "1, 2, -2, 3", "1, 2, 4, 1", "1, 2, -2, 1"})
    void 이동하고자_하는_x와의_차이가_3인_경우_이동하고자_하는_y와의_차이가_2가_아니라면_움직일_수_없다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 4, 4", "1, 2, -2, 4", "1, 2, 4, 0", "1, 2, -2, 0"})
    void 이동하고자_하는_x와의_차이가_3인_경우_이동하고자_하는_y와의_차이가_2라면_움직일_수_있다(
            int x,
            int y,
            int moveX,
            int moveY
    ) {

        Elephant elephant = new Elephant(Side.CHO, new Position(x, y));

        assertThat(elephant.isMoveablePosition(moveX, moveY)).isTrue();
    }
}
