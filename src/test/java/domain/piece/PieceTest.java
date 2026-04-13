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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("기물 통합 테스트")
class PieceTest {

    private static final int DEFAULT_ROW = 5;
    private static final int DEFAULT_FILE = 5;
    private static final Intersection CURRENT_INTERSECTION = new Intersection(DEFAULT_ROW, DEFAULT_FILE);
    private static final Side MY_SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;

    private static final Piece SAME_SIDE_PIECE = Piece.of(PieceType.SOLDIER, MY_SIDE);
    private static final Piece OPPOSITE_SIDE_PIECE = Piece.of(PieceType.SOLDIER, OPPOSITE_SIDE);

    @DisplayName("빈 기물은 항상 같은 인스턴스이다.")
    @Nested
    class 빈_기물은_항상_같은_인스턴스만_리턴 {

        @DisplayName("빈 기물을 새로 생성해도, 기존의 다른 빈 기물과 동일한 인스턴스이다")
        @Test
        void 빈_기물을_새로_생성해도_기존_빈_기물과_동일() {
            Piece emptyPiece = Piece.EMPTY;
            Piece otherEmptyPiece = Piece.of(PieceType.EMPTY, Side.NONE);

            assertThat(otherEmptyPiece).isSameAs(emptyPiece);
        }
    }

    @DisplayName("행마법 테스트")
    @Nested
    class 행마법_테스트 {

        @DisplayName("졸/병")
        @Nested
        class 졸_병 {

            private static final Piece SOLDIER = Piece.of(PieceType.SOLDIER, MY_SIDE);

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

            @DisplayName("궁성에 있는 경우 대각선으로 이동할 수 있다")
            @Nested
            class 궁성에서_대각선_이동_가능 {

                private final AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                @DisplayName("초(CHO)")
                @Test
                void 초_진영() {
                    Piece solider = Piece.of(PieceType.SOLDIER, Side.CHO);

                    List<Intersection> movableDestinations
                            = solider.movableDestinations(position(2, 5), emptyAlivePieces);

                    assertThat(movableDestinations)
                            .containsExactlyInAnyOrder(
                                    position(1, 4),
                                    position(1, 5),
                                    position(1, 6),
                                    position(2, 4),
                                    position(2, 6)
                            );
                }

                @DisplayName("초(HAN)")
                @Test
                void 한_진영() {
                    Piece solider = Piece.of(PieceType.SOLDIER, Side.HAN);

                    List<Intersection> movableDestinations
                            = solider.movableDestinations(position(9, 5), emptyAlivePieces);

                    assertThat(movableDestinations)
                            .containsExactlyInAnyOrder(
                                    position(10, 4),
                                    position(10, 5),
                                    position(10, 6),
                                    position(9, 4),
                                    position(9, 6)
                            );
                }
            }
        }

        @DisplayName("차")
        @Nested
        class 차 {

            private static final Piece CHARIOT = Piece.of(PieceType.CHARIOT, MY_SIDE);

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

            @DisplayName("궁성에 있는 경우 대각선으로 이동할 수 있다")
            @Test
            void 궁성에서_대각선_이동_가능() {
                // given
                Intersection placedSameSidePiece = position(1, 6);
                AlivePieces alivePieces = new AlivePieces(Map.of(placedSameSidePiece, SAME_SIDE_PIECE));
                Intersection corner = new Intersection(2, 5);
                List<Intersection> orthogonalDestinations =
                        createAllStraightIntersectionExcludeTargetPosition(2, 5);
                List<Intersection> diagonalDestinations = List.of(
                        position(1, 4), position(3, 4), position(3, 6)
                );

                // when
                List<Intersection> movableDestinations = CHARIOT.movableDestinations(corner, alivePieces);

                // then
                assertThat(movableDestinations)
                        .hasSize(orthogonalDestinations.size() + diagonalDestinations.size())
                        .as("직선 방향 포함")
                        .containsAll(orthogonalDestinations)
                        .as("궁성 대각선 포함")
                        .containsAll(diagonalDestinations)
                        .as("같은 편 기물이 있는 곳으로는 이동 불가")
                        .doesNotContain(placedSameSidePiece);
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

            private static final Piece HORSE = Piece.of(PieceType.HORSE, MY_SIDE);

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
                Intersection blockPosition = new Intersection(DEFAULT_ROW - 1, DEFAULT_FILE);
                AlivePieces alivePieces = new AlivePieces(Map.of(blockPosition, SAME_SIDE_PIECE));

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

            private static final Piece ELEPHANT = Piece.of(PieceType.ELEPHANT, MY_SIDE);

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

            private static final Piece CANNON = Piece.of(PieceType.CANNON, MY_SIDE);

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
                            placedCannonIntersection, Piece.of(PieceType.CANNON, OPPOSITE_SIDE)
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
                            placedCannonDestination, Piece.of(PieceType.CANNON, OPPOSITE_SIDE)
                    ));

                    List<Intersection> movableDestinations =
                            CANNON.movableDestinations(CURRENT_INTERSECTION, targetIsCannon);

                    assertThat(movableDestinations).doesNotContain(placedCannonDestination);
                }
            }

            @DisplayName("포는 궁성에 있는 경우 중앙의 기물을 넘어 대각선으로 이동할 수 있다")
            @Nested
            class 궁성에서_대각선_이동 {

                @DisplayName("중앙에 뛰어넘을 기물이 있다면, 그 너머 대각선으로 이동 가능")
                @Test
                void 뛰어넘을_기물_있으면_가능() {
                    // given
                    Intersection startIntersection = position(1, 4);
                    Intersection screenIntersection = position(2, 5); // 궁성 대각선의 다리

                    AlivePieces alivePieces = new AlivePieces(Map.of(
                            screenIntersection, SAME_SIDE_PIECE
                    ));

                    // when
                    List<Intersection> movableDestinations = CANNON.movableDestinations(startIntersection, alivePieces);

                    // then
                    assertThat(movableDestinations).containsExactlyInAnyOrder(position(3, 6));
                }

                @DisplayName("중앙에 뛰어넘을 기물이 없다면, 행마법에 따라 대각선 이동이 불가능하다")
                @Test
                void 뛰어넘을_기물_없으면_불가능() {
                    // given
                    Intersection startIntersection = position(1, 4);

                    AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                    // when
                    List<Intersection> movableDestinations =
                            CANNON.movableDestinations(startIntersection, emptyAlivePieces);

                    // then
                    assertThat(movableDestinations).isEmpty();
                }
            }
        }

        @DisplayName("궁/사")
        @Nested
        class 궁_사 {

            @DisplayName("궁성 내에서 1칸씩 모든 방향으로 이동할 수 있다")
            @Nested
            class 궁성_내에서만_1칸_이동 {

                @DisplayName("초(CHO) 진영")
                @ParameterizedTest(name = "기물 {0}은 궁성 내에서만 모든 방향으로 이동할 수 있다")
                @EnumSource(value = PieceType.class, names = {"GENERAL", "GUARD"}, mode = EnumSource.Mode.INCLUDE)
                void 초_진영(PieceType palacePieceType) {
                    // given
                    Piece choPalacePiece = Piece.of(palacePieceType, Side.CHO);
                    AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                    // when
                    List<Intersection> movableDestinations = choPalacePiece.movableDestinations(
                            position(8, 4),
                            emptyAlivePieces
                    );

                    // then
                    assertThat(movableDestinations).containsExactlyInAnyOrder(
                            position(8, 5),
                            position(9, 4),
                            position(9, 5)
                    );
                }

                @DisplayName("한(HAN) 진영")
                @ParameterizedTest(name = "기물 {0}은 궁성 내에서만 모든 방향으로 이동할 수 있다")
                @EnumSource(value = PieceType.class, names = {"GENERAL", "GUARD"}, mode = EnumSource.Mode.INCLUDE)
                void 한_진영(PieceType palacePieceType) {
                    // given
                    Piece hanPalacePiece = Piece.of(palacePieceType, Side.HAN);
                    AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

                    // when
                    List<Intersection> movableDestinations = hanPalacePiece.movableDestinations(
                            position(3, 4),
                            emptyAlivePieces
                    );

                    // then
                    assertThat(movableDestinations).containsExactlyInAnyOrder(
                            position(2, 4),
                            position(3, 5),
                            position(2, 5)
                    );
                }
            }
        }
    }

    private static Intersection position(int row, int file) {
        return new Intersection(row, file);
    }
}
