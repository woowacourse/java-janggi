package domain.board;

import static domain.movements.Direction.NORTH;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class BoardPointTest {

    @Nested
    @DisplayName("Point가 방향을 받았을 때")
    class TestMoveBoardPoint {
        @Test
        @DisplayName("정해진 방향으로 이동한다.")
        void test_move() {
            //given
            final BoardPoint boardPoint = new BoardPoint(0, 0);
            final BoardPoint expect = new BoardPoint(1, 0);
            //when
            final BoardPoint actual = boardPoint.move(NORTH);

            //then
            Assertions.assertThat(actual).isEqualTo(expect);
        }
    }

    private static Stream<Arguments> testCasesForIsPointInPalaceReturnTrue() {
        return Stream.of(
                Arguments.of(new BoardPoint(2, 5)),
                Arguments.of(new BoardPoint(0, 3)),
                Arguments.of(new BoardPoint(0, 4)),
                Arguments.of(new BoardPoint(1, 3)),
                Arguments.of(new BoardPoint(2, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForIsPointInPalaceReturnTrue")
    @DisplayName("포인트가 궁 내부에 존재하는지 확인한다")
    void test_isInPalaceReturnTrue(BoardPoint boardPoint) {
        assertTrue(boardPoint.isInPalace());
    }

    private static Stream<Arguments> testCasesForIsPointInPalaceReturnFalse() {
        return Stream.of(
                Arguments.of(new BoardPoint(3, 3)),
                Arguments.of(new BoardPoint(2, 2)),
                Arguments.of(new BoardPoint(0, 2)),
                Arguments.of(new BoardPoint(1, 2)),
                Arguments.of(new BoardPoint(0, 6)),
                Arguments.of(new BoardPoint(1, 6)),
                Arguments.of(new BoardPoint(2, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForIsPointInPalaceReturnFalse")
    @DisplayName("포인트가 궁 내부에 존재하지 않는지 확인한다")
    void test_isInPalaceReturnFalse(BoardPoint boardPoint) {
        assertFalse(boardPoint.isInPalace());
    }
}
