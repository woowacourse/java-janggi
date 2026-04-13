package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AlivePiecesTest {

    private static final Intersection NON_EMPTY_INTERSECTION = new Intersection(5, 5);
    private static final Intersection EMPTY_INTERSECTION = new Intersection(3, 3);
    private static final Piece DEFAULT_PIECE = Piece.of(PieceType.SOLDIER, Side.CHO);
    private static final Piece CHO_PIECE = Piece.of(PieceType.SOLDIER, Side.CHO);

    @DisplayName("기물이 없는 위치를 조회하면 EMPTY 타입과 NONE 진영을 가진 기물을 리턴한다")
    @Test
    void 기물이_없는_위치를_조회하면_EMPTY_타입과_NONE_진영을_가진_기물을_리턴한다() {
        AlivePieces alivePieces = new AlivePieces(Map.of());
        Piece piece = alivePieces.placedAt(new Intersection(1, 1));

        assertThat(piece.isSameType(PieceType.EMPTY)).isTrue();
        assertThat(piece.isSameSide(Side.NONE)).isTrue();
    }

    @DisplayName("기물의 존재 여부 검증")
    @Nested
    class 기물의_존재_여부_검증 {

        private static final AlivePieces alivePieces = new AlivePieces(Map.of(
                NON_EMPTY_INTERSECTION, DEFAULT_PIECE
        ));

        @DisplayName("비어 있는 곳 검증")
        @Test
        void 비어_있는_곳_검증() {
            assertThat(alivePieces.isEmpty(EMPTY_INTERSECTION)).isTrue();
            assertThat(alivePieces.isNotEmpty(EMPTY_INTERSECTION)).isFalse();
        }

        @DisplayName("비어 있지 않은 곳 검증")
        @Test
        void 비어_있지_않은_곳_검증() {
            assertThat(alivePieces.isNotEmpty(NON_EMPTY_INTERSECTION)).isTrue();
            assertThat(alivePieces.isEmpty(NON_EMPTY_INTERSECTION)).isFalse();
        }

        @DisplayName("존재하는 기물 검증")
        @Test
        void 존재하는_기물_검증() {
            Piece placed = alivePieces.placedAt(NON_EMPTY_INTERSECTION);

            assertThat(placed).isSameAs(DEFAULT_PIECE);
        }
    }

    @DisplayName("특정 위치의 진영 검증")
    @Nested
    class 특정_위치의_진영_검증 {

        private final AlivePieces alivePieces = new AlivePieces(Map.of(
                NON_EMPTY_INTERSECTION, CHO_PIECE
        ));

        @DisplayName("같은 진영의 기물인지")
        @Test
        void 같은_진영의_기물인지() {
            assertThat(alivePieces.placedSameSide(NON_EMPTY_INTERSECTION, Side.CHO)).isTrue();
            assertThat(alivePieces.placedNotSameSide(NON_EMPTY_INTERSECTION, Side.CHO)).isFalse();
        }

        @DisplayName("상대 진영의 기물인지")
        @Test
        void 상대_진영의_기물인지() {
            assertThat(alivePieces.placedSameSide(NON_EMPTY_INTERSECTION, Side.HAN)).isFalse();
            assertThat(alivePieces.placedNotSameSide(NON_EMPTY_INTERSECTION, Side.HAN)).isTrue();
        }

        @DisplayName("빈 공간이면 같은 진영이 아니라고 판단")
        @Test
        void 빈_공간이면_같은_진영이_아니라고_판단() {
            assertThat(alivePieces.placedSameSide(EMPTY_INTERSECTION, Side.CHO)).isFalse();
            assertThat(alivePieces.placedNotSameSide(EMPTY_INTERSECTION, Side.CHO)).isTrue();
        }
    }

    @DisplayName("놓여 있는 기물의 존재 검증")
    @Nested
    class 놓여_있는_기물의_존재_검증 {

        @DisplayName("같은 진영 검증")
        @Test
        void 같은_진영_검증() {
            Piece choPiece = Piece.of(PieceType.SOLDIER, Side.CHO);
            Intersection targetIntersection = new Intersection(5, 5);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    targetIntersection, choPiece
            ));

            boolean placedSameSide = alivePieces.placedSameSide(targetIntersection, Side.CHO);
            boolean placedNotSameSide = alivePieces.placedNotSameSide(targetIntersection, Side.CHO);

            assertThat(placedSameSide).isTrue();
            assertThat(placedNotSameSide).isFalse();
        }

        @DisplayName("상대 진영 검증")
        @Test
        void 상대_진영_검증() {
            Piece choPiece = Piece.of(PieceType.SOLDIER, Side.CHO);
            Intersection targetIntersection = new Intersection(5, 5);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    targetIntersection, choPiece
            ));

            boolean placedSameSide = alivePieces.placedSameSide(targetIntersection, Side.HAN);
            boolean placedNotSameSide = alivePieces.placedNotSameSide(targetIntersection, Side.HAN);

            assertThat(placedSameSide).isFalse();
            assertThat(placedNotSameSide).isTrue();
        }
    }

    @DisplayName("기물의 위치 이동 검증")
    @Test
    void 기물의_위치_이동_검증() {
        Piece targetPiece = Piece.of(PieceType.SOLDIER, Side.CHO);
        Intersection startIntersection = NON_EMPTY_INTERSECTION;
        Intersection destination = EMPTY_INTERSECTION;
        AlivePieces alivePieces = new AlivePieces(Map.of(
                startIntersection, targetPiece
        ));

        alivePieces.replace(startIntersection, destination);
        Piece placed = alivePieces.placedAt(destination);

        assertThat(placed).isSameAs(targetPiece);
    }

    @DisplayName("통과할 수 있는 경로인지 검증")
    @Nested
    class 통과할_수_있는_경로인지_검증 {

        @DisplayName("경로에 다른 기물이 있으면 통과할 수 없다")
        @Test
        void 경로에_다른_기물이_있으면_통과할_수_없다() {
            Intersection passingIntersection = new Intersection(5, 5);
            Intersection destination = new Intersection(6, 5);
            AlivePieces alivePieces = new AlivePieces(Map.of(
                    passingIntersection, Piece.of(PieceType.SOLDIER, Side.CHO)
            ));
            Path path = new Path(destination, List.of(passingIntersection));

            assertThat(alivePieces.isPassable(path)).isFalse();
        }

        @DisplayName("경로에 다른 기물이 없으면 통과할 수 있다")
        @Test
        void 경로에_다른_기물이_없으면_통과할_수_있다() {
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());
            List<Intersection> passingIntersections = List.of(
                    new Intersection(5, 5),
                    new Intersection(6, 5)
            );
            Intersection destination = new Intersection(7, 5);

            Path path = new Path(destination, passingIntersections);

            assertThat(emptyAlivePieces.isPassable(path)).isTrue();
        }
    }

    @DisplayName("진영별로 살아있는 기물들의 점수를 계산한다")
    @ParameterizedTest(name = "초(CHO): {1}점, 한(HAN): {2}점")
    @MethodSource("piecesAndPoint")
    void 진영별로_기물_점수의_합을_계산한다(Map<Intersection, Piece> pieces, int expectedPointCho, int expectedPointHan) {
        // given
        AlivePieces alivePieces = new AlivePieces(Map.copyOf(pieces));

        // when
        int totalPointOfCho = alivePieces.calculatePiecePointOf(Side.CHO);
        int totalPointOfHan = alivePieces.calculatePiecePointOf(Side.HAN);

        // then
        assertThat(totalPointOfCho).isEqualTo(expectedPointCho);
        assertThat(totalPointOfHan).isEqualTo(expectedPointHan);
    }

    private static Stream<Arguments> piecesAndPoint() {
        return Stream.of(
                Arguments.of(
                        Map.of(),
                        0, 0),
                Arguments.of(
                        Map.of(
                                pos(1, 1), Piece.of(PieceType.SOLDIER, Side.CHO)
                        ),
                        2, 0
                ),
                Arguments.of(
                        Map.of(
                                pos(1, 1), Piece.of(PieceType.SOLDIER, Side.HAN)
                        ),
                        0, 2
                ), Arguments.of(
                        Map.of(
                                pos(1, 1), Piece.of(PieceType.CHARIOT, Side.CHO),
                                pos(1, 2), Piece.of(PieceType.CANNON, Side.CHO),
                                pos(1, 3), Piece.of(PieceType.HORSE, Side.HAN),
                                pos(1, 4), Piece.of(PieceType.ELEPHANT, Side.HAN),
                                pos(1, 5), Piece.of(PieceType.GUARD, Side.HAN)
                        ),
                        20, 11
                )
        );
    }

    private static Intersection pos(int row, int file) {
        return new Intersection(row, file);
    }
}
