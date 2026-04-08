package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class IntersectionTest {

    private static final int DEFAULT_ROW = 5;
    private static final int DEFAULT_FILE = 5;

    @DisplayName("문자열로부터 생성한다")
    @Nested
    class 문자열로부터_생성 {

        @DisplayName("파싱하여 정상 생성")
        @Test
        void 파싱이_가능한_문자열() {
            String parseableString = "5,5";

            Intersection intersection = Intersection.parse(parseableString);

            assertThat(intersection).isEqualTo(new Intersection(5, 5));
        }

        @DisplayName("구분자가 없으면 예외를 던진다")
        @Test
        void 쉼표가_없으면_예외() {
            assertThatThrownBy(() -> Intersection.parse("55"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("구분자");
        }

        @DisplayName("숫자와 구분자 외에 다른 문자가 존재하면 예외를 던진다")
        @Test
        void 숫자_구분자말고_있으면_예외() {
            assertThatThrownBy(() -> Intersection.parse("5,a"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("숫자");
        }

        @DisplayName("구분자가 있지만 숫자가 하나만 입력되면 예외를 던진다")
        @Test
        void 숫자_하나와_구분자만_있으면_예외() {
            assertThatThrownBy(() -> Intersection.parse("1,"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("구분자");
        }
    }


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
            boolean outOfBounds = intersection.isOutOfBounds();

            // then
            assertThat(outOfBounds).isTrue();
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
            boolean outOfRow = intersection.isOutOfBounds();

            // then
            assertThat(outOfRow).isTrue();
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
            boolean outOfFile = intersection.isOutOfBounds();

            // then
            assertThat(outOfFile).isTrue();
        }

        @ParameterizedTest
        @CsvSource({
                "1,1", "1,9", "10,1", "10,9"
        })
        void 행과_열이_모두_범위_안이라면_벗어나지_않았다고_판단한다(int inBoardRow, int inBoardFile) {
            // given
            Intersection intersection = new Intersection(inBoardRow, inBoardFile);

            // when
            boolean outOfBounds = intersection.isOutOfBounds();

            // then
            assertThat(outOfBounds).isFalse();
        }
    }
}
