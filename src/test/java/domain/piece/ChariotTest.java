package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChariotTest {

    private static final Side SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Soldier SAME_SIDE_PIECE = new Soldier(SIDE);
    private static final Soldier OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_차가_적절한_초기_위치를_반환한다() {
            // given
            Chariot chariot = new Chariot(Side.HAN);
            List<Intersection> expected = List.of(
                    new Intersection(1, 1),
                    new Intersection(1, 9)
            );

            // when
            List<Intersection> actual = chariot.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_차가_적절한_초기_위치를_반환한다() {
            // given
            Chariot chariot = new Chariot(Side.CHO);
            List<Intersection> expected = List.of(
                    new Intersection(10, 1),
                    new Intersection(10, 9)
            );

            // when
            List<Intersection> actual = chariot.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class 이동할_수_있는_위치를_반환한다 {

        @Test
        void 경로에_기물이_없다면_보드_경계까지_이동할_수_있다() {
            // given
            Chariot chariot = new Chariot(SIDE);
            int currentRow = 5;
            int currentFile = 5;
            Intersection currentIntersection = new Intersection(currentRow, currentFile);

            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
            List<Intersection> expected = List.of(
                    new Intersection(1, currentFile),
                    new Intersection(2, currentFile),
                    new Intersection(3, currentFile),
                    new Intersection(4, currentFile)
            );

            // when
            List<Intersection> movableIntersections = chariot.movableIntersections(
                    currentIntersection,
                    emptyAlivePieces
            );

            // then
            assertThat(movableIntersections).containsAll(expected);
        }

        @Test
        void 경로에_아군이_있으면_그_직전_칸까지만_이동할_수_있다() {
            // given
            Chariot chariot = new Chariot(SIDE);
            int currentRow = 5;
            int currentFile = 5;
            Intersection currentIntersection = new Intersection(currentRow, currentFile);

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    new Intersection(1, currentFile), SAME_SIDE_PIECE
            ));
            List<Intersection> expected = List.of(
                    new Intersection(2, currentFile),
                    new Intersection(3, currentFile),
                    new Intersection(4, currentFile)
            );

            // when
            List<Intersection> movableIntersections = chariot.movableIntersections(
                    currentIntersection,
                    alivePieces
            );

            // then
            assertThat(movableIntersections).containsAll(expected);
        }

        @Test
        void 경로에_적군이_있으면_해당_칸까지_이동할_수_있다() {
            // given
            Chariot chariot = new Chariot(SIDE);
            int currentRow = 5;
            int currentFile = 5;
            Intersection currentIntersection = new Intersection(currentRow, currentFile);

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    new Intersection(1, currentFile), OPPOSITE_SIDE_PIECE
            ));
            List<Intersection> expected = List.of(
                    new Intersection(1, currentFile),
                    new Intersection(2, currentFile),
                    new Intersection(3, currentFile),
                    new Intersection(4, currentFile)
            );

            // when
            List<Intersection> movableIntersections = chariot.movableIntersections(
                    currentIntersection,
                    alivePieces
            );

            // then
            assertThat(movableIntersections).containsAll(expected);
        }
    }
}
