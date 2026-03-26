package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SoldierTest {

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
        private static final Side SIDE = Side.HAN;
        private static final Side DIFFERENT_SIDE = Side.CHO;
        private static final Soldier SAME_SIDE_PEICE = new Soldier(SIDE);
        private static final Soldier DIFFERENT_SIDE_PEICE = new Soldier(DIFFERENT_SIDE);
        private static final Intersection CURRENT_INTERSECTION = new Intersection(DEFAULT_ROW, DEFAULT_FILE);

        @Nested
        class 아군_기물이_있는_위치로는_이동할_수_없다 {

            @Test
            void 전진할_곳에_아군_기물이_있다면_이동할_수_없다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int forwardRow = SIDE.getForwardedRow(CURRENT_INTERSECTION.row());
                Intersection forwardIntersection = new Intersection(forwardRow, CURRENT_INTERSECTION.file());
                AlivePieces aliavePieces = new AlivePieces(Map.of(
                        forwardIntersection, SAME_SIDE_PEICE
                ));

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, forwardIntersection, aliavePieces);

                // then
                assertThat(canMove).isFalse();
            }

            @Test
            void 왼쪽에_아군_기물이_있다면_이동할_수_없다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int leftFile = SIDE.getLeftFile(CURRENT_INTERSECTION.row());
                Intersection leftIntersection = new Intersection(CURRENT_INTERSECTION.row(), leftFile);
                AlivePieces aliavePieces = new AlivePieces(Map.of(
                        leftIntersection, SAME_SIDE_PEICE
                ));

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, leftIntersection, aliavePieces);

                // then
                assertThat(canMove).isFalse();
            }

            @Test
            void 오른쪽에_아군_기물이_있다면_이동할_수_없다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int rightFile = SIDE.getRightFile(CURRENT_INTERSECTION.row());
                Intersection rightIntersection = new Intersection(CURRENT_INTERSECTION.row(), rightFile);
                AlivePieces aliavePieces = new AlivePieces(Map.of(
                        rightIntersection, SAME_SIDE_PEICE
                ));

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, rightIntersection, aliavePieces);

                // then
                assertThat(canMove).isFalse();
            }
        }

        @Nested
        class 상대_기물이_있는_위치로는_이동할_수_있다 {

            @Test
            void 전진할_곳에_상대_기물이_있다면_이동할_수_있다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int forwardRow = SIDE.getForwardedRow(CURRENT_INTERSECTION.row());
                Intersection forwardIntersection = new Intersection(forwardRow, CURRENT_INTERSECTION.file());
                AlivePieces aliavePieces = new AlivePieces(Map.of(
                        forwardIntersection, DIFFERENT_SIDE_PEICE
                ));

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, forwardIntersection, aliavePieces);

                // then
                assertThat(canMove).isTrue();
            }

            @Test
            void 왼쪽에_상대_기물이_있다면_이동할_수_있다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int leftFile = SIDE.getLeftFile(CURRENT_INTERSECTION.row());
                Intersection leftIntersection = new Intersection(CURRENT_INTERSECTION.row(), leftFile);
                AlivePieces aliavePieces = new AlivePieces(Map.of(
                        leftIntersection, DIFFERENT_SIDE_PEICE
                ));

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, leftIntersection, aliavePieces);

                // then
                assertThat(canMove).isTrue();
            }

            @Test
            void 오른쪽에_상대_기물이_있다면_이동할_수_있다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int rightFile = SIDE.getRightFile(CURRENT_INTERSECTION.row());
                Intersection rightIntersection = new Intersection(CURRENT_INTERSECTION.row(), rightFile);
                AlivePieces aliavePieces = new AlivePieces(Map.of(
                        rightIntersection, DIFFERENT_SIDE_PEICE
                ));

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, rightIntersection, aliavePieces);

                // then
                assertThat(canMove).isTrue();
            }
        }

        @Nested
        class 비어_있는_위치로는_이동할_수_있다 {

            @Test
            void 전진할_곳이_비어_있다면_이동할_수_있다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int forwardRow = SIDE.getForwardedRow(CURRENT_INTERSECTION.row());
                Intersection forwardIntersection = new Intersection(forwardRow, CURRENT_INTERSECTION.file());
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, forwardIntersection, emptyAlivePieces);

                // then
                assertThat(canMove).isTrue();
            }

            @Test
            void 왼쪽이_비어_있다면_이동할_수_있다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int leftFile = SIDE.getLeftFile(CURRENT_INTERSECTION.row());
                Intersection leftIntersection = new Intersection(CURRENT_INTERSECTION.row(), leftFile);
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, leftIntersection, emptyAlivePieces);

                // then
                assertThat(canMove).isTrue();
            }

            @Test
            void 오른쪽이_비어_있다면_이동할_수_있다() {
                // given
                Soldier soldier = new Soldier(SIDE);

                int rightFile = SIDE.getRightFile(CURRENT_INTERSECTION.row());
                Intersection rightIntersection = new Intersection(CURRENT_INTERSECTION.row(), rightFile);
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                // when
                boolean canMove = soldier.canMove(CURRENT_INTERSECTION, rightIntersection, emptyAlivePieces);

                // then
                assertThat(canMove).isTrue();
            }
        }

        @Test
        void 뒤로는_이동할_수_없다() {
            // given
            Soldier soldier = new Soldier(SIDE);

            int backwardRow = SIDE.getBackwardRow(CURRENT_INTERSECTION.row());
            Intersection backwordIntersection = new Intersection(backwardRow, CURRENT_INTERSECTION.file());
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            boolean canMove = soldier.canMove(CURRENT_INTERSECTION, backwordIntersection, emptyAlivePieces);

            // then
            assertThat(canMove).isFalse();
        }
    }
}
