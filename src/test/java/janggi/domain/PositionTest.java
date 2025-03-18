package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
public class PositionTest {

    private static Stream<Arguments> 좌표가_같은지_확인한다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        new Position(1, 2),
                        1,
                        2,
                        true
                ),
                Arguments.of(
                        new Position(1, 2),
                        2,
                        2,
                        false
                ),
                Arguments.of(
                        new Position(1, 3),
                        1,
                        2,
                        false
                )
        );
    }

    @Test
    void 가로_세로_2차원_좌표를_가진다() {
        Position position = new Position(1, 2);
        assertThat(position.getX()).isEqualTo(1);
        assertThat(position.getY()).isEqualTo(2);
    }

    @Test
    void 좌표를_변경할_수_있다() {
        Position position = new Position(1, 2);

        Position moved = position.moveTo(2, 4);
        assertThat(moved.getX()).isEqualTo(2);
        assertThat(moved.getY()).isEqualTo(4);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1, 1", "1,-1", "9,1", "1,10"})
    void 초기화_시_좌표를_검증한다(int x, int y) {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Position(x, y));
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,false", "2,2,true"})
    void X_좌표가_같은지_확인한다(int x, int comparer, boolean expected) {
        Position position = new Position(x, 2);

        assertThat(position.hasSameX(comparer)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,false", "2,2,true"})
    void Y_좌표가_같은지_확인한다(int y, int comparer, boolean expected) {
        Position position = new Position(1, y);

        assertThat(position.hasSameY(comparer)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("좌표가_같은지_확인한다_테스트_케이스")
    void 좌표가_같은지_확인한다(Position position, int comparerX, int comparerY, boolean expected) {

        assertThat(position.isSameCoordinate(comparerX, comparerY)).isEqualTo(expected);
    }
}
