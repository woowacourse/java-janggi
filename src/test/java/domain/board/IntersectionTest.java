package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.movement.Vector;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

    @Nested
    class 좌표가_궁성에_해당하는지_판단한다 {

        @Test
        void 좌표가_궁성에_해당한다면_true를_반환한다() {
            // given
            Intersection palaceIntersection = new Intersection(2, 5);

            // when
            boolean palace = palaceIntersection.isPalace();

            // then
            assertThat(palace).isTrue();
        }

        @Test
        void 좌표가_궁성에_해당하지_않는다면_false를_반환한다() {
            // given
            Intersection noPalaceIntersection = new Intersection(5, 5);

            // when
            boolean palace = noPalaceIntersection.isPalace();

            // then
            assertThat(palace).isFalse();
        }
    }

    @Nested
    class 추가로_이동할_수_있는_방향을_제공한다 {

        @Test
        void 우상향_대각선으로_이동할_수_있다면_해당_벡터를_반환한다() {
            // given
            Intersection ascendingDiagonalIntersection = new Intersection(3, 4);

            // when
            List<Vector> palaceVectors = ascendingDiagonalIntersection.getPalaceDiagonalVectors();

            // then
            assertThat(palaceVectors).containsExactlyInAnyOrder(
                    Vector.leftDown(),
                    Vector.rightUp()
            );
        }

        @Test
        void 우하향_대각선으로_이동할_수_있다면_해당_벡터를_반환한다() {
            // given
            Intersection descendingDiagonalIntersection = new Intersection(1, 4);

            // when
            List<Vector> palaceVectors = descendingDiagonalIntersection.getPalaceDiagonalVectors();

            // then
            assertThat(palaceVectors).containsExactlyInAnyOrder(
                    Vector.leftUp(),
                    Vector.rightDown()
            );
        }

        @Test
        void 왼쪽_오른쪽_대각선으로_이동할_수_있다면_해당_벡터를_반환한다() {
            // given
            Intersection bothDiagonalIntersection = new Intersection(2, 5);

            // when
            List<Vector> palaceVectors = bothDiagonalIntersection.getPalaceDiagonalVectors();

            // then
            assertThat(palaceVectors).containsExactlyInAnyOrder(
                    Vector.leftUp(),
                    Vector.leftDown(),
                    Vector.rightDown(),
                    Vector.rightUp()
            );
        }

        @Test
        void 추가로_이동할_수_있는_방향이_없다면_빈_컬렉션을_반환한다() {
            // given
            Intersection noExtraVectorsIntersection = new Intersection(1, 5);

            // when
            List<Vector> palaceVectors = noExtraVectorsIntersection.getPalaceDiagonalVectors();

            // then
            assertThat(palaceVectors).isEmpty();
        }
    }
}
