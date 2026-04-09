package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.movement.Vector;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @Nested
    class 좌표가_궁성에_해당하는지_판단한다 {

        @Test
        void 좌표가_궁성에_해당한다면_true를_반환한다() {
            // given
            Palace palace = new Palace();
            Intersection palaceIntersection = new Intersection(2, 5);

            // when
            boolean contains = palace.contains(palaceIntersection);

            // then
            assertThat(contains).isTrue();
        }

        @Test
        void 좌표가_궁성에_해당하지_않는다면_false를_반환한다() {
            // given
            Palace palace = new Palace();
            Intersection noPalaceIntersection = new Intersection(5, 5);

            // when
            boolean contains = palace.contains(noPalaceIntersection);

            // then
            assertThat(contains).isFalse();
        }
    }

    @Nested
    class 추가로_이동할_수_있는_방향을_제공한다 {

        @Test
        void 우상향_대각선으로_이동할_수_있다면_해당_벡터를_반환한다() {
            // given
            Palace palace = new Palace();
            Intersection ascendingDiagonalIntersection = new Intersection(3, 4);

            // when
            List<Vector> palaceVectors = palace.getDiagonalVectors(ascendingDiagonalIntersection);

            // then
            assertThat(palaceVectors).containsExactlyInAnyOrder(
                    Vector.leftDown(),
                    Vector.rightUp()
            );
        }

        @Test
        void 우하향_대각선으로_이동할_수_있다면_해당_벡터를_반환한다() {
            // given
            Palace palace = new Palace();
            Intersection descendingDiagonalIntersection = new Intersection(1, 4);

            // when
            List<Vector> palaceVectors = palace.getDiagonalVectors(descendingDiagonalIntersection);

            // then
            assertThat(palaceVectors).containsExactlyInAnyOrder(
                    Vector.leftUp(),
                    Vector.rightDown()
            );
        }

        @Test
        void 왼쪽_오른쪽_대각선으로_이동할_수_있다면_해당_벡터를_반환한다() {
            // given
            Palace palace = new Palace();
            Intersection bothDiagonalIntersection = new Intersection(2, 5);

            // when
            List<Vector> palaceVectors = palace.getDiagonalVectors(bothDiagonalIntersection);

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
            Palace palace = new Palace();
            Intersection noExtraVectorsIntersection = new Intersection(1, 5);

            // when
            List<Vector> palaceVectors = palace.getDiagonalVectors(noExtraVectorsIntersection);

            // then
            assertThat(palaceVectors).isEmpty();
        }
    }
}
