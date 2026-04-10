package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.Vector;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {

    private static final Side SIDE = Side.HAN;

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_병이_적절한_초기_위치를_반환한다() {
            // given
            Soldier soldier = new Soldier(Side.HAN);
            List<Intersection> expected = List.of(
                    new Intersection(4, 1),
                    new Intersection(4, 3),
                    new Intersection(4, 5),
                    new Intersection(4, 7),
                    new Intersection(4, 9)
            );

            // when
            List<Intersection> actual = soldier.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_졸이_적절한_초기_위치를_반환한다() {
            // given
            Soldier soldier = new Soldier(Side.CHO);
            List<Intersection> expected = List.of(
                    new Intersection(7, 1),
                    new Intersection(7, 3),
                    new Intersection(7, 5),
                    new Intersection(7, 7),
                    new Intersection(7, 9)
            );

            // when
            List<Intersection> actual = soldier.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class 이동_가능한_위치를_판단한다 {

        private static final int DEFAULT_ROW = 5;
        private static final int DEFAULT_FILE = 5;
        private static final Side OPPOSITE_SIDE = Side.CHO;
        private static final Soldier SAME_SIDE_PIECE = new Soldier(SIDE);
        private static final Soldier OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);
        private static final Intersection CURRENT_INTERSECTION = new Intersection(DEFAULT_ROW, DEFAULT_FILE);

        @ParameterizedTest
        @MethodSource("soliderCardinalDirections")
        void 아군_기물이_있는_위치로는_이동할_수_없다(Vector vector) {
            // given
            Soldier soldier = new Soldier(SIDE);

            Intersection forwardIntersection = vector.next(CURRENT_INTERSECTION);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    forwardIntersection, SAME_SIDE_PIECE
            ));

            // when
            List<Intersection> movableIntersections = soldier.movableIntersections(
                    CURRENT_INTERSECTION,
                    alivePieces
            );

            // then
            assertThat(movableIntersections).doesNotContain(forwardIntersection);
        }

        @ParameterizedTest
        @MethodSource("soliderCardinalDirections")
        void 상대_기물이_있는_위치로는_이동할_수_있다(Vector vector) {
            // given
            Soldier soldier = new Soldier(SIDE);

            Intersection forwardIntersection = vector.next(CURRENT_INTERSECTION);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    forwardIntersection, OPPOSITE_SIDE_PIECE
            ));

            // when
            List<Intersection> movableIntersections = soldier.movableIntersections(
                    CURRENT_INTERSECTION,
                    alivePieces
            );

            // then
            assertThat(movableIntersections).contains(forwardIntersection);
        }

        @ParameterizedTest
        @MethodSource("soliderCardinalDirections")
        void 비어_있는_위치로는_이동할_수_있다(Vector vector) {
            // given
            Soldier soldier = new Soldier(SIDE);

            Intersection forwardIntersection = vector.next(CURRENT_INTERSECTION);
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            List<Intersection> movableIntersections = soldier.movableIntersections(
                    CURRENT_INTERSECTION,
                    emptyAlivePieces
            );

            // then
            assertThat(movableIntersections).contains(forwardIntersection);
        }

        @Test
        void 뒤로는_이동할_수_없다() {
            // given
            Soldier soldier = new Soldier(SIDE);

            Vector backward = reverse(SIDE.toForward());
            Intersection backwardIntersection = backward.next(CURRENT_INTERSECTION);
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            List<Intersection> movableIntersections = soldier.movableIntersections(
                    CURRENT_INTERSECTION,
                    emptyAlivePieces
            );

            // then
            assertThat(movableIntersections).doesNotContain(backwardIntersection);
        }

        @ParameterizedTest
        @MethodSource("soliderDigonalDirections")
        void 궁성의_전진_방향_대각선으로는_이동할_수_있다(Vector digonalDirection) {
            // given
            Soldier soldier = new Soldier(SIDE);

            Intersection palaceIntersection = new Intersection(2, 5);
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            Intersection expectedPalaceDestination = digonalDirection.next(palaceIntersection);

            // when
            List<Intersection> movableIntersections = soldier.movableIntersections(palaceIntersection, emptyAlivePieces);

            // then
            assertThat(movableIntersections).contains(expectedPalaceDestination);
        }

        private static Stream<Arguments> soliderCardinalDirections() {
            return Stream.of(
                    Arguments.of(SIDE.toForward()),
                    Arguments.of(Vector.right()),
                    Arguments.of(Vector.left())
            );
        }

        private static Stream<Arguments> soliderDigonalDirections() {
            return SIDE.toForwardDiagonals()
                    .stream()
                    .map(Arguments::of);
        }
    }

    @Test
    void 본인의_점수를_반환한다() {
        // given
        Soldier soldier = new Soldier(SIDE);
        double expected = 2;

        // when
        double actual = soldier.getScore();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private Vector reverse(Vector vector) {
        int reversedRowDelta = vector.rowDelta() * -1;
        int reversedFileDelta = vector.fileDelta() * -1;

        return new Vector(reversedRowDelta, reversedFileDelta);
    }
}
