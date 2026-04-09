package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class IntersectionTest {

    private static final int DEFAULT_ROW = 5;
    private static final int DEFAULT_FILE = 5;

    @Nested
    class 좌표가_보드_범위를_벗어났는지_판단한다 {

        @ParameterizedTest
        @CsvSource({
                "0,0", "0,10", "11,0", "11,10"
        })
        void 행과_열이_모두_범위_밖이라면_벗어났다고_판단한다(int outOfBoardRow, int outOfBoardFile) {
            // given
            Intersection intersection = new Intersection(outOfBoardRow, outOfBoardFile);

            // when
            boolean outOfBoard = intersection.isOutOfBoard();

            // then
            assertThat(outOfBoard).isTrue();
        }

        @ParameterizedTest
        @ValueSource(ints = {
                0, 11, // 엣지 케이스
                Integer.MIN_VALUE, -10, 20, Integer.MAX_VALUE
        })
        void 행이_범위_밖이라면_벗어났다고_판단한다(int outOfBoardRow) {
            // given
            Intersection intersection = new Intersection(outOfBoardRow, DEFAULT_FILE);

            // when
            boolean outOfBoard = intersection.isOutOfBoard();

            // then
            assertThat(outOfBoard).isTrue();
        }

        @ParameterizedTest
        @ValueSource(ints = {
                0, 10, // 엣지 케이스
                Integer.MIN_VALUE, -10, 20, Integer.MAX_VALUE
        })
        void 열이_범위_밖이라면_벗어났다고_판단한다(int outOfBoardFile) {
            // given
            Intersection intersection = new Intersection(DEFAULT_ROW, outOfBoardFile);

            // when
            boolean outOfBoard = intersection.isOutOfBoard();

            // then
            assertThat(outOfBoard).isTrue();
        }

        @ParameterizedTest
        @CsvSource({
                "1,1", "1,9", "10,1", "10,9"
        })
        void 행과_열이_모두_범위_안이라면_벗어나지_않았다고_판단한다(int inBoardRow, int inBoardFile) {
            // given
            Intersection intersection = new Intersection(inBoardRow, inBoardFile);

            // when
            boolean inBoard = intersection.isOutOfBoard();

            // then
            assertThat(inBoard).isFalse();
        }
    }
}
