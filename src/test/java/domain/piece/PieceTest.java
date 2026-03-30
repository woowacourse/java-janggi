package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
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
            private final Piece soldier = new Piece(PieceType.SOLDIER, MY_SIDE);

            @Test
            @DisplayName("전진 및 좌우 이동은 가능하나 뒤로는 이동할 수 없다")
            void moveForwardAndSides() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
                List<Intersection> movablePaths = soldier.movablePaths(CURRENT_INTERSECTION,
                        emptyAlivePieces);

                assertThat(movablePaths).containsExactlyInAnyOrder(
                        new Intersection(DEFAULT_ROW + 1, DEFAULT_FILE), // 전진 (HAN 기준)
                        new Intersection(DEFAULT_ROW, DEFAULT_FILE - 1), // 좌
                        new Intersection(DEFAULT_ROW, DEFAULT_FILE + 1)  // 우
                );
            }

            @Test
            @DisplayName("이동하려는 위치에 아군이 있으면 이동할 수 없고, 적군이 있으면 이동할 수 있다")
            void blockBySameSide() {
                Intersection forward = new Intersection(DEFAULT_ROW + 1, DEFAULT_FILE);
                Intersection left = new Intersection(DEFAULT_ROW, DEFAULT_FILE - 1);

                AlivePieces alivePieces = new AlivePieces(Map.of(
                        forward, SAME_SIDE_PIECE,      // 전진 방향에 아군
                        left, OPPOSITE_SIDE_PIECE      // 왼쪽 방향에 적군
                ));

                List<Intersection> movablePaths = soldier.movablePaths(CURRENT_INTERSECTION, alivePieces);

                assertThat(movablePaths).doesNotContain(forward); // 아군 위치 불가
                assertThat(movablePaths).contains(left);          // 적군 위치 가능 (포획)
            }
        }

        @DisplayName("차")
        @Nested
        class 차 {
            private final Piece chariot = new Piece(PieceType.CHARIOT, MY_SIDE);

            @Test
            @DisplayName("경로에 기물이 없다면 직선으로 끝까지 이동할 수 있다")
            void moveStraightUntilEnd() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
                List<Intersection> movablePaths = chariot.movablePaths(CURRENT_INTERSECTION,
                        emptyAlivePieces);

                assertThat(movablePaths).contains(
                        new Intersection(1, DEFAULT_FILE),
                        new Intersection(10, DEFAULT_FILE),
                        new Intersection(DEFAULT_ROW, 1),
                        new Intersection(DEFAULT_ROW, 9)
                );
            }

            @Test
            @DisplayName("경로에 기물이 있으면 통과할 수 없으며, 적군일 경우 해당 위치까지만 이동 가능하다")
            void blockByPieces() {
                Intersection sameSideBlock = new Intersection(DEFAULT_ROW + 2, DEFAULT_FILE); // 아래쪽 아군
                Intersection oppositeSideBlock = new Intersection(DEFAULT_ROW, DEFAULT_FILE + 2); // 오른쪽 적군

                AlivePieces alivePieces = new AlivePieces(Map.of(
                        sameSideBlock, SAME_SIDE_PIECE,
                        oppositeSideBlock, OPPOSITE_SIDE_PIECE
                ));

                List<Intersection> movablePaths = chariot.movablePaths(CURRENT_INTERSECTION, alivePieces);

                // 아군 앞까지만 이동 가능 (해당 칸 포함 X)
                assertThat(movablePaths).contains(new Intersection(DEFAULT_ROW + 1, DEFAULT_FILE));
                assertThat(movablePaths).doesNotContain(sameSideBlock);

                // 적군 칸까지 이동 가능 (포획)
                assertThat(movablePaths).contains(new Intersection(DEFAULT_ROW, DEFAULT_FILE + 1), oppositeSideBlock);
                assertThat(movablePaths).doesNotContain(new Intersection(DEFAULT_ROW, DEFAULT_FILE + 3));
            }
        }

        @DisplayName("마")
        @Nested
        class 마 {
            private final Piece horse = new Piece(PieceType.HORSE, MY_SIDE);

            @Test
            @DisplayName("직선 1칸, 대각선 1칸(날일자)으로 이동할 수 있다")
            void moveOrthogonalThenDiagonal() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
                List<Intersection> movablePaths = horse.movablePaths(CURRENT_INTERSECTION, emptyAlivePieces);

                assertThat(movablePaths).containsExactlyInAnyOrder(
                        new Intersection(3, 4), new Intersection(3, 6),
                        new Intersection(7, 4), new Intersection(7, 6),
                        new Intersection(4, 3), new Intersection(6, 3),
                        new Intersection(4, 7), new Intersection(6, 7)
                );
            }

            @Test
            @DisplayName("직선 1칸 째(첫 경로)에 기물이 있으면 해당 방향으로는 이동할 수 없다 (멱이 막힘)")
            void blockedPath() {
                Intersection blockPoint = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE); // 위쪽으로 1칸 지점
                AlivePieces alivePieces = new AlivePieces(Map.of(blockPoint, SAME_SIDE_PIECE));

                List<Intersection> movablePaths = horse.movablePaths(CURRENT_INTERSECTION, alivePieces);

                // 위쪽을 거쳐가는 목적지는 이동 불가
                assertThat(movablePaths).doesNotContain(
                        new Intersection(3, 4), new Intersection(3, 6)
                );
            }
        }

        @DisplayName("상")
        @Nested
        class 상 {
            private final Piece elephant = new Piece(PieceType.ELEPHANT, MY_SIDE);

            @Test
            @DisplayName("첫 직선 경로 혹은 첫 대각선 경로에 기물이 있으면 이동할 수 없다 (멱이 막힘)")
            void blockedPath() {
                // 상이 (5,5)에서 (2,3)으로 가려면 (4,5)와 (3,4)를 거쳐야 함
                Intersection firstBlock = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE); // (4,5)
                Intersection secondBlock = new Intersection(DEFAULT_ROW - 2, DEFAULT_FILE - 1); // (3,4)

                AlivePieces blockedAtFirst = new AlivePieces(
                        Map.of(firstBlock, OPPOSITE_SIDE_PIECE));
                AlivePieces blockedAtSecond = new AlivePieces(
                        Map.of(secondBlock, OPPOSITE_SIDE_PIECE));

                assertThat(elephant.movablePaths(CURRENT_INTERSECTION, blockedAtFirst))
                        .doesNotContain(new Intersection(2, 3), new Intersection(2, 7)); // 해당 방향 전체 차단

                assertThat(elephant.movablePaths(CURRENT_INTERSECTION, blockedAtSecond))
                        .doesNotContain(new Intersection(2, 3)); // 왼쪽 대각선 방향 차단
            }
        }

        @DisplayName("포")
        @Nested
        class 포 {
            private final Piece cannon = new Piece(PieceType.CANNON, MY_SIDE);

            @Test
            @DisplayName("반드시 다른 기물(스크린)을 하나 뛰어넘어야 이동할 수 있다")
            void needScreenToMove() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of()); // 스크린 없음
                assertThat(cannon.movablePaths(CURRENT_INTERSECTION, emptyAlivePieces)).isEmpty();

                Intersection screen = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE); // 바로 앞에 스크린
                AlivePieces alivePiecesWithScreen = new AlivePieces(
                        Map.of(screen, SAME_SIDE_PIECE));

                List<Intersection> movablePaths = cannon.movablePaths(CURRENT_INTERSECTION, alivePiecesWithScreen);
                assertThat(movablePaths).contains(
                        new Intersection(DEFAULT_ROW - 2, DEFAULT_FILE),
                        new Intersection(DEFAULT_ROW - 3, DEFAULT_FILE)
                );
            }

            @Test
            @DisplayName("포를 뛰어넘을 수 없고, 적군의 포를 잡을 수도 없다")
            void cannotJumpOrCaptureCannon() {
                Intersection screenCannon = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE); // 스크린이 포
                AlivePieces blockedByCannonScreen = new AlivePieces(Map.of(
                        screenCannon, new Piece(PieceType.CANNON, OPPOSITE_SIDE)
                ));

                // 포를 넘을 수 없으므로 이동 불가
                assertThat(cannon.movablePaths(CURRENT_INTERSECTION, blockedByCannonScreen)).isEmpty();

                Intersection screenNormal = new Intersection(DEFAULT_ROW, DEFAULT_FILE + 1); // 스크린은 일반 기물
                Intersection targetCannon = new Intersection(DEFAULT_ROW, DEFAULT_FILE + 3); // 잡으려는 목적지에 적 포
                AlivePieces targetIsCannon = new AlivePieces(Map.of(
                        screenNormal, SAME_SIDE_PIECE,
                        targetCannon, new Piece(PieceType.CANNON, OPPOSITE_SIDE)
                ));

                List<Intersection> movablePaths = cannon.movablePaths(CURRENT_INTERSECTION, targetIsCannon);
                // 적 포 앞까지만 갈 수 있고, 포를 포획할 수 없음
                assertThat(movablePaths).contains(new Intersection(DEFAULT_ROW, DEFAULT_FILE + 2));
                assertThat(movablePaths).doesNotContain(targetCannon);
            }
        }

        @DisplayName("궁/사")
        @Nested
        class 궁_사 {
            private final Piece general = new Piece(PieceType.GENERAL, MY_SIDE);
            private final Piece guard = new Piece(PieceType.GUARD, MY_SIDE);

            @Test
            @DisplayName("한 칸씩 이동할 수 있다")
            void moveSingleStep() {
                AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                List<Intersection> generalPaths = general.movablePaths(CURRENT_INTERSECTION,
                        emptyAlivePieces);
                List<Intersection> guardPaths = guard.movablePaths(CURRENT_INTERSECTION, emptyAlivePieces);

                List<Intersection> expected = List.of(
                        new Intersection(DEFAULT_ROW + 1, DEFAULT_FILE),
                        new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE),
                        new Intersection(DEFAULT_ROW, DEFAULT_FILE + 1),
                        new Intersection(DEFAULT_ROW, DEFAULT_FILE - 1)
                );

                assertThat(generalPaths).containsAll(expected);
                assertThat(guardPaths).containsAll(expected);
            }
        }
    }
}
