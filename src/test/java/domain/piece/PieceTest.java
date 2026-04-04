package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("기물 통합 테스트")
class PieceTest {

    private static final int DEFAULT_ROW = 5;
    private static final int DEFAULT_FILE = 5;
    private static final Intersection CURRENT_INTERSECTION = new Intersection(DEFAULT_ROW, DEFAULT_FILE);
    private static final Side MY_SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;

    private static final Piece SAME_SIDE_PIECE = new Piece(PieceType.SOLDIER, MY_SIDE);
    private static final Piece OPPOSITE_SIDE_PIECE = new Piece(PieceType.SOLDIER, OPPOSITE_SIDE);

    @DisplayName("행마법 테스트")
    @Nested
    class 행마법_테스트 {

        @DisplayName("졸/병")
        @Nested
        class 졸_병 {

            private static final Piece SOLDIER = new Piece(PieceType.SOLDIER, MY_SIDE);

            @DisplayName("전진 및 좌우로 1칸 이동은 가능하나 뒤로는 이동할 수 없다")
            @Test
            void 전진_및_좌우로_1칸_이동은_가능하나_뒤로는_이동할_수_없다() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
                List<Intersection> movableDestinations =
                        SOLDIER.movableDestinations(CURRENT_INTERSECTION, emptyAlivePieces);

                assertThat(movableDestinations).containsExactlyInAnyOrder(
                        new Intersection(DEFAULT_ROW + 1, DEFAULT_FILE),
                        new Intersection(DEFAULT_ROW, DEFAULT_FILE - 1),
                        new Intersection(DEFAULT_ROW, DEFAULT_FILE + 1)
                );
            }

            @DisplayName("아군이 있는 곳으로는 이동할 수 없고, 적군이 있는 곳으로는 이동할 수 있다")
            @Test
            void 아군이_있는_곳으로는_이동할_수_없고_적군이_있는_곳으로는_이동할_수_있다() {
                Intersection placedOppositeSidePieceIntersection = new Intersection(DEFAULT_ROW + 1, DEFAULT_FILE);
                Intersection placedSameSidePieceIntersection = new Intersection(DEFAULT_ROW, DEFAULT_FILE - 1);

                AlivePieces alivePieces = new AlivePieces(Map.of(
                        placedOppositeSidePieceIntersection, SAME_SIDE_PIECE,
                        placedSameSidePieceIntersection, OPPOSITE_SIDE_PIECE
                ));

                List<Intersection> movableDestinations = SOLDIER.movableDestinations(CURRENT_INTERSECTION, alivePieces);

                assertThat(movableDestinations)
                        .as("아군이 있는 곳으로는 이동할 수 없다")
                        .doesNotContain(placedOppositeSidePieceIntersection)
                        .as("적군이 있는 곳으로는 이동할 수 있다")
                        .contains(placedSameSidePieceIntersection);
            }
        }

        @DisplayName("차")
        @Nested
        class 차 {

            private static final Piece CHARIOT = new Piece(PieceType.CHARIOT, MY_SIDE);

            @DisplayName("경로에 기물이 없다면 직선으로 끝까지 이동할 수 있다")
            @Test
            void 경로에_기물이_없다면_직선으로_끝까지_이동할_수_있다() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
                List<Intersection> movableDestinations =
                        CHARIOT.movableDestinations(CURRENT_INTERSECTION, emptyAlivePieces);

                List<Intersection> expected =
                        createAllStraightIntersectionExcludeTargetPosition(DEFAULT_ROW, DEFAULT_FILE);

                assertThat(movableDestinations).containsExactlyInAnyOrderElementsOf(expected);
            }

            @DisplayName("경로에 기물이 있으면 통과할 수 없으며, 적군일 경우 해당 위치까지만 이동 가능하다")
            @Test
            void 경로에_기물이_있으면_통과할_수_없으며_적군일_경우_해당_위치까지만_이동_가능하다() {
                Intersection placedSameSidePieceIntersection = new Intersection(DEFAULT_ROW + 2, DEFAULT_FILE);
                Intersection placedOppositePieceIntersection = new Intersection(DEFAULT_ROW, DEFAULT_FILE + 2);

                AlivePieces alivePieces = new AlivePieces(Map.of(
                        placedSameSidePieceIntersection, SAME_SIDE_PIECE,
                        placedOppositePieceIntersection, OPPOSITE_SIDE_PIECE
                ));

                List<Intersection> movableDestinations = CHARIOT.movableDestinations(CURRENT_INTERSECTION, alivePieces);

                assertThat(movableDestinations)
                        .as("아군 기물이 있으면 그 직전까지 이동 가능하다")
                        .contains(new Intersection(DEFAULT_ROW + 1, DEFAULT_FILE))
                        .doesNotContain(placedSameSidePieceIntersection)
                        .as("적군 기물이 있으면 해당 위치까지 이동 가능하다")
                        .contains(new Intersection(DEFAULT_ROW, DEFAULT_FILE + 1), placedOppositePieceIntersection)
                        .doesNotContain(new Intersection(DEFAULT_ROW, DEFAULT_FILE + 3));
            }

            private static List<Intersection> createAllStraightIntersectionExcludeTargetPosition(
                    int targetRow,
                    int targetFile
            ) {
                List<Intersection> intersections = new ArrayList<>();

                for (int row = 1; row <= 10; row++) {
                    if (row == targetRow) {
                        continue;
                    }
                    intersections.add(new Intersection(row, targetFile));
                }

                for (int file = 1; file <= 9; file++) {
                    if (file == targetFile) {
                        continue;
                    }
                    intersections.add(new Intersection(targetRow, file));
                }

                return intersections;
            }
        }

        @DisplayName("마")
        @Nested
        class 마 {

            private static final Piece HORSE = new Piece(PieceType.HORSE, MY_SIDE);

            @DisplayName("직선으로 1칸 이동 후 대각선으로 1칸 이동할 수 있다(날일자)")
            @Test
            void 직선으로_1칸_이동_후_대각선으로_1칸_이동할_수_있다() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                List<Intersection> movableDestinations =
                        HORSE.movableDestinations(CURRENT_INTERSECTION, emptyAlivePieces);

                assertThat(movableDestinations).containsExactlyInAnyOrder(
                        new Intersection(3, 4), new Intersection(3, 6),
                        new Intersection(7, 4), new Intersection(7, 6),
                        new Intersection(4, 3), new Intersection(6, 3),
                        new Intersection(4, 7), new Intersection(6, 7)
                );
            }

            @DisplayName("직선 1칸 앞(첫 경로)에 기물이 있으면 이동할 수 없다")
            @Test
            void 직선_1칸_앞에_기물이_있으면_이동할_수_없다() {
                Intersection blockPoint = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE);
                AlivePieces alivePieces = new AlivePieces(Map.of(blockPoint, SAME_SIDE_PIECE));

                List<Intersection> movableDestinations = HORSE.movableDestinations(CURRENT_INTERSECTION, alivePieces);

                assertThat(movableDestinations).doesNotContain(
                        new Intersection(3, 4),
                        new Intersection(3, 6)
                );
            }
        }

        @DisplayName("상")
        @Nested
        class 상 {

            private static final Piece ELEPHANT = new Piece(PieceType.ELEPHANT, MY_SIDE);

            @DisplayName("직선 1칸 앞(첫 경로) 또는 첫 대각선(두 번째 경로)에 기물이 있으면 이동할 수 없다")
            @Test
            void 직선_1칸_앞_또는_첫_대각선에_기물이_있으면_이동할_수_없다() {
                Intersection firstBlockedIntersection = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE);
                Intersection secondBlockedIntersection = new Intersection(DEFAULT_ROW - 2, DEFAULT_FILE - 1);

                AlivePieces blockedAtFirstPassingIntersection = new AlivePieces(Map.of(
                        firstBlockedIntersection, OPPOSITE_SIDE_PIECE)
                );
                AlivePieces blockedAtSecondPassingIntersection = new AlivePieces(Map.of(
                        secondBlockedIntersection, OPPOSITE_SIDE_PIECE)
                );

                assertThat(ELEPHANT.movableDestinations(CURRENT_INTERSECTION, blockedAtFirstPassingIntersection))
                        .doesNotContain(new Intersection(2, 3), new Intersection(2, 7));

                assertThat(ELEPHANT.movableDestinations(CURRENT_INTERSECTION, blockedAtSecondPassingIntersection))
                        .doesNotContain(new Intersection(2, 3));
            }
        }

        @DisplayName("포")
        @Nested
        class 포 {

            private static final Piece CANNON = new Piece(PieceType.CANNON, MY_SIDE);

            @DisplayName("반드시 다른 기물(스크린)을 하나 뛰어넘어야 이동할 수 있다")
            @Nested
            class 반드시_다른_기물을_하나_뛰어넘어야_이동할_수_있다 {

                @DisplayName("뛰어넘을 기물이 없는 경우")
                @Test
                void 뛰어넘을_기물이_없는_경우() {
                    AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                    assertThat(CANNON.movableDestinations(CURRENT_INTERSECTION, emptyAlivePieces)).isEmpty();
                }

                @DisplayName("뛰어넘을 기물이 있는 경우")
                @Test
                void 뛰어넘을_기물이_있는_경우() {
                    Intersection screen = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE);
                    AlivePieces alivePiecesWithScreen = new AlivePieces(
                            Map.of(screen, SAME_SIDE_PIECE));

                    List<Intersection> movableDestinations =
                            CANNON.movableDestinations(CURRENT_INTERSECTION, alivePiecesWithScreen);

                    assertThat(movableDestinations).contains(
                            new Intersection(DEFAULT_ROW - 2, DEFAULT_FILE),
                            new Intersection(DEFAULT_ROW - 3, DEFAULT_FILE)
                    );
                }
            }

            @DisplayName("포를 뛰어넘을 수 없고, 적군의 포를 잡을 수도 없다")
            @Nested
            class 포를_뛰어넘을_수_없고_적군의_포를_잡을_수도_없다 {

                @DisplayName("포를 뛰어넘을 수 없다")
                @Test
                void 포를_뛰어넘을_수_없다() {
                    Intersection placedCannonIntersection = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE); // 스크린이 포
                    AlivePieces blockedByCannon = new AlivePieces(Map.of(
                            placedCannonIntersection, new Piece(PieceType.CANNON, OPPOSITE_SIDE)
                    ));

                    assertThat(CANNON.movableDestinations(CURRENT_INTERSECTION, blockedByCannon)).isEmpty();
                }

                @DisplayName("적군의 포를 잡을 수 없다")
                @Test
                void 적군의_포를_잡을_수_없다() {
                    Intersection placedScreenIntersection = new Intersection(DEFAULT_ROW, DEFAULT_FILE + 1);
                    Intersection placedCannonDestination = new Intersection(DEFAULT_ROW, DEFAULT_FILE + 3);
                    AlivePieces targetIsCannon = new AlivePieces(Map.of(
                            placedScreenIntersection, SAME_SIDE_PIECE,
                            placedCannonDestination, new Piece(PieceType.CANNON, OPPOSITE_SIDE)
                    ));

                    List<Intersection> movableDestinations =
                            CANNON.movableDestinations(CURRENT_INTERSECTION, targetIsCannon);

                    assertThat(movableDestinations).doesNotContain(placedCannonDestination);
                }
            }
        }

        @DisplayName("궁/사")
        @Nested
        class 궁_사 {

            @DisplayName("한 칸씩 모든 방향으로 이동할 수 있다")
            @Test
            void 한_칸씩_모든_방향으로_이동할_수_있다() {
                Piece general = new Piece(PieceType.GENERAL, MY_SIDE);
                Piece guard = new Piece(PieceType.GUARD, MY_SIDE);
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
                List<Intersection> expected = createOneStepIntersectionsToAllDirection(CURRENT_INTERSECTION);

                List<Intersection> generalPaths = general.movableDestinations(CURRENT_INTERSECTION,
                        emptyAlivePieces);
                List<Intersection> guardPaths = guard.movableDestinations(CURRENT_INTERSECTION, emptyAlivePieces);

                assertThat(generalPaths).containsAll(expected);
                assertThat(guardPaths).containsAll(expected);
            }

            private static List<Intersection> createOneStepIntersectionsToAllDirection(
                    Intersection startIntersection
            ) {
                int row = startIntersection.row();
                int file = startIntersection.file();

                return List.of(
                        new Intersection(row + 1, file),
                        new Intersection(row - 1, file),
                        new Intersection(row, file + 1),
                        new Intersection(row, file - 1)
                );
            }
        }
    }
}
