package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class CannonTest {

    private static final Side SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Soldier SAME_SIDE_PIECE = new Soldier(SIDE);
    private static final Soldier OPPOSITE_SIDE_PIECE = new Soldier(OPPOSITE_SIDE);
    private static final Soldier OTHER_PIECE = new Soldier(SIDE);

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_포가_적절한_초기_위치를_반환한다() {
            // given
            Cannon cannon = new Cannon(Side.HAN);
            List<Intersection> expected = List.of(
                    new Intersection(3, 2),
                    new Intersection(3, 8)
            );

            // when
            List<Intersection> actual = cannon.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_포가_적절한_초기_위치를_반환한다() {
            // given
            Cannon cannon = new Cannon(Side.CHO);
            List<Intersection> expected = List.of(
                    new Intersection(8, 2),
                    new Intersection(8, 8)
            );

            // when
            List<Intersection> actual = cannon.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class 이동할_수_있는_위치를_반환한다 {

        @Nested
        class 스크린이_없다면_이동할_수_없다 {

            @Test
            void 경로에_기물이_없다면_이동할_수_없다() {
                // given
                Cannon cannon = new Cannon(SIDE);
                int currentRow = 5;
                int currentFile = 5;
                Intersection currentIntersection = new Intersection(currentRow, currentFile);

                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                // when
                List<Intersection> movableIntersections = cannon.movableIntersections(
                        currentIntersection,
                        emptyAlivePieces
                );

                // then
                assertThat(movableIntersections).isEmpty();
            }

            @ParameterizedTest
            @EnumSource(Side.class)
            void 가장_가까운_기물이_포라면_이동할_수_없다(Side closestCannonSide) {
                // given
                Cannon cannon = new Cannon(SIDE);
                int currentRow = 5;
                int currentFile = 5;
                Intersection currentIntersection = new Intersection(currentRow, currentFile);

                AlivePieces emptyAlivePieces = new AlivePieces(Map.of(
                        new Intersection(3, currentFile), new Cannon(closestCannonSide)
                ));

                // when
                List<Intersection> movableIntersections = cannon.movableIntersections(
                        currentIntersection,
                        emptyAlivePieces
                );

                // then
                assertThat(movableIntersections).isEmpty();
            }
        }

        @Nested
        class 스크린이_있다면_그_너머로_이동할_수_있다 {

            @Test
            void 너머_경로에_기물이_없다면_보드_경계까지_이동할_수_있다() {
                // given
                Cannon cannon = new Cannon(SIDE);
                int currentRow = 5;
                int currentFile = 5;
                Intersection currentIntersection = new Intersection(currentRow, currentFile);
                Intersection screenIntersection = new Intersection(3, currentFile);
                AlivePieces alivePieces = new AlivePieces(Map.of(
                        screenIntersection, OTHER_PIECE
                ));

                List<Intersection> expected = List.of(
                        new Intersection(1, currentFile),
                        new Intersection(2, currentFile)
                );

                // when
                List<Intersection> actual = cannon.movableIntersections(
                        currentIntersection,
                        alivePieces
                );

                // then
                assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
            }

            @Test
            void 너머_경로에_아군_기물이_있다면_그_직전_칸까지만_이동할_수_있다() {
                // given
                Cannon cannon = new Cannon(SIDE);
                int currentRow = 5;
                int currentFile = 5;
                Intersection currentIntersection = new Intersection(currentRow, currentFile);

                Intersection screenIntersection = new Intersection(4, currentFile);
                Intersection sameSidePieceIntersection = new Intersection(2, currentFile);
                AlivePieces alivePieces = new AlivePieces(Map.of(
                        screenIntersection, OTHER_PIECE,
                        sameSidePieceIntersection, SAME_SIDE_PIECE
                ));

                List<Intersection> expected = List.of(
                        new Intersection(3, currentFile)
                );

                // when
                List<Intersection> actual = cannon.movableIntersections(
                        currentIntersection,
                        alivePieces
                );

                // then
                assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
            }

            @Test
            void 너머_경로에_적군이_있으면_해당_칸까지_이동할_수_있다() {
                // given
                Cannon cannon = new Cannon(SIDE);
                int currentRow = 5;
                int currentFile = 5;
                Intersection currentIntersection = new Intersection(currentRow, currentFile);

                Intersection screenIntersection = new Intersection(4, currentFile);
                Intersection oppositeSidePieceIntersection = new Intersection(2, currentFile);
                AlivePieces alivePieces = new AlivePieces(Map.of(
                        screenIntersection, OTHER_PIECE,
                        oppositeSidePieceIntersection, OPPOSITE_SIDE_PIECE
                ));

                List<Intersection> expected = List.of(
                        oppositeSidePieceIntersection,
                        new Intersection(3, currentFile)
                );

                // when
                List<Intersection> actual = cannon.movableIntersections(
                        currentIntersection,
                        alivePieces
                );

                // then
                assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
            }

            @Test
            void 너머_경로에_적군_포가_있다면_그_직전_칸까지만_이동할_수_있다() {
                // given
                Cannon cannon = new Cannon(SIDE);
                int currentRow = 5;
                int currentFile = 5;
                Intersection currentIntersection = new Intersection(currentRow, currentFile);

                Intersection screenIntersection = new Intersection(4, currentFile);
                Intersection oppositeCannonIntersection = new Intersection(2, currentFile);
                AlivePieces alivePieces = new AlivePieces(Map.of(
                        screenIntersection, OTHER_PIECE,
                        oppositeCannonIntersection, new Cannon(OPPOSITE_SIDE)
                ));

                List<Intersection> expected = List.of(
                        new Intersection(3, currentFile)
                );

                // when
                List<Intersection> actual = cannon.movableIntersections(
                        currentIntersection,
                        alivePieces
                );

                // then
                assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
            }

            @Test
            void 일직선상이_아닌_칸으로는_이동할_수_없다() {
                // given
                Cannon cannon = new Cannon(SIDE);

                Intersection currentIntersection = new Intersection(5, 5);
                Intersection diagonalScreenIntersection = new Intersection(4, 4);
                AlivePieces alivePieces = new AlivePieces(Map.of(
                        diagonalScreenIntersection, OTHER_PIECE
                ));

                // when
                List<Intersection> movableIntersections = cannon.movableIntersections(
                        currentIntersection,
                        alivePieces
                );

                // then
                assertThat(movableIntersections).isEmpty();
            }

            @Test
            void 궁성의_대각선으로는_이동할_수_있다() {
                // given
                Cannon cannon = new Cannon(SIDE);

                Intersection from = new Intersection(1, 4);
                Intersection diagonalScreen = new Intersection(2, 5);
                Intersection diagonalDestination = new Intersection(3, 6);
                AlivePieces alivePieces = new AlivePieces(Map.of(
                        from, cannon,
                        diagonalScreen, OTHER_PIECE
                ));

                // when
                List<Intersection> movableIntersections = cannon.movableIntersections(from, alivePieces);

                // then
                assertThat(movableIntersections).containsExactly(diagonalDestination);
            }
        }
    }

    @Test
    void 본인의_점수를_반환한다() {
        // given
        Cannon cannon = new Cannon(SIDE);
        double expected = 7;

        // when
        double actual = cannon.getScore();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
