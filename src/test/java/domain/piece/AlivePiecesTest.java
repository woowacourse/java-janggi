package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.Move;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AlivePiecesTest {

    private static final Side SIDE = Side.HAN;
    private static final Side OPPOSITE_SIDE = Side.CHO;
    private static final Piece DEFAULT_PIECE = new Soldier(Side.HAN);
    private static final Intersection DEFAULT_INTERSECTION = new Intersection(5, 5);

    private Intersection emptyIntersection;
    private Intersection notEmptyIntersection;
    private Intersection sameSideIntersection;
    private Intersection oppositeSideIntersection;
    private AlivePieces alivePieces;

    @BeforeEach
    void setUpPieces() {
        emptyIntersection = new Intersection(3, 3);
        notEmptyIntersection = new Intersection(4, 4);
        sameSideIntersection = new Intersection(5, 5);
        oppositeSideIntersection = new Intersection(6, 6);

        Map<Intersection, Piece> pieces = Map.of(
                notEmptyIntersection, DEFAULT_PIECE,
                sameSideIntersection, new Soldier(SIDE),
                oppositeSideIntersection, new Soldier(OPPOSITE_SIDE)
        );

        alivePieces = new AlivePieces(pieces);
    }

    @Nested
    class 기물을_이동시킨다 {

        @Test
        void 시작_위치에_있던_기물을_목적지로_이동시킨다() {
            // given
            Intersection startIntersection = new Intersection(3, 3);
            Intersection destination = new Intersection(5, 5);

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    startIntersection, DEFAULT_PIECE
            ));

            Move moveToDestination = new Move(startIntersection, destination);

            // when
            AlivePieces movedAlivePieces = alivePieces.replace(moveToDestination);

            // then
            Piece pieceAtDestination = movedAlivePieces.placedAt(destination);
            assertThat(pieceAtDestination).isEqualTo(DEFAULT_PIECE);
        }

        @Test
        void 기존_위치에_있던_기물_정보는_제거한다() {
            // given
            Intersection existIntersection = new Intersection(3, 3);
            Intersection destination = new Intersection(5, 5);

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    existIntersection, DEFAULT_PIECE
            ));

            Move moveFromExistIntersection = new Move(existIntersection, destination);

            // when
            AlivePieces movedAlivePieces = alivePieces.replace(moveFromExistIntersection);

            // then
            Piece pieceAtExistIntersection = movedAlivePieces.placedAt(existIntersection);
            assertThat(pieceAtExistIntersection).isNull();
        }

        @Test
        void 목적지에_존재하던_기물_정보는_제거한다() {
            // given
            Intersection startIntersection = new Intersection(3, 3);
            Intersection destination = new Intersection(5, 5);

            Piece pieceAtStart = new Soldier(Side.HAN);
            Piece pieceAtDestination = new Soldier(Side.CHO);

            AlivePieces alivePieces = new AlivePieces(Map.of(
                    startIntersection, pieceAtStart,
                    destination, pieceAtDestination
            ));

            Move moveToExistPiece = new Move(startIntersection, destination);

            // when
            AlivePieces movedAlivePieces = alivePieces.replace(moveToExistPiece);

            // then
            Piece currentPieceAtDestination = movedAlivePieces.placedAt(destination);
            assertThat(currentPieceAtDestination).isNotEqualTo(pieceAtDestination);
        }

        @Test
        void 시작_위치에_기물이_없다면_예외를_던진다() {
            // given
            Move moveFromEmpty = new Move(emptyIntersection, DEFAULT_INTERSECTION);

            // when and then
            assertThatThrownBy(() -> alivePieces.replace(moveFromEmpty))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("기물을 움직이기 위해선, 출발지에 기물이 존재해야 합니다.");
        }
    }

    @Nested
    class 좌표가_비어_있는지를_판단한다 {

        @Test
        void 비어_있는_좌표라면_true를_반환한다() {
            boolean result = alivePieces.isEmpty(emptyIntersection);

            assertThat(result).isTrue();
        }

        @Test
        void 상대_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.isEmpty(oppositeSideIntersection);

            assertThat(result).isFalse();
        }

        @Test
        void 아군_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.isEmpty(sameSideIntersection);

            assertThat(result).isFalse();
        }
    }

    @Nested
    class 좌표에_배치된_기물을_반환한다 {

        @Test
        void 비어_있는_좌표라면_null을_반환한다() {
            Piece piece = alivePieces.placedAt(emptyIntersection);

            assertThat(piece).isNull();
        }

        @Test
        void 비어_있지_않은_좌표라면_기물을_반환한다() {
            Piece piece = alivePieces.placedAt(notEmptyIntersection);

            assertThat(piece).isNotNull();
        }
    }

    @Nested
    class 같은_진영_기물이_배치되어_있는지를_판단한다 {

        @Test
        void 같은_진영_기물이_배치되어_있다면_true를_반환한다() {
            boolean result = alivePieces.placedSameSide(sameSideIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 상대_진영_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.placedSameSide(oppositeSideIntersection, SIDE);

            assertThat(result).isFalse();
        }

        @Test
        void 빈_좌표라면_false를_반환한다() {
            boolean result = alivePieces.placedSameSide(emptyIntersection, SIDE);

            assertThat(result).isFalse();
        }
    }

    @Nested
    class 같은_진영_기물이_안_배치되어_있는지를_판단한다 {

        @Test
        void 상대_진영_기물이_배치되어_있다면_true를_반환한다() {
            boolean result = alivePieces.placedNotSameSide(oppositeSideIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 빈_좌표라면_true를_반환한다() {
            boolean result = alivePieces.placedNotSameSide(emptyIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 같은_진영_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.placedNotSameSide(sameSideIntersection, SIDE);

            assertThat(result).isFalse();
        }
    }

    @Nested
    class 상대_진영_기물이_배치되어_있는지를_판단한다 {

        @Test
        void 상대_진영_기물이_배치되어_있다면_true를_반환한다() {
            boolean result = alivePieces.placedOppositeSide(oppositeSideIntersection, SIDE);

            assertThat(result).isTrue();
        }

        @Test
        void 같은_진영_기물이_배치되어_있다면_false를_반환한다() {
            boolean result = alivePieces.placedOppositeSide(sameSideIntersection, SIDE);

            assertThat(result).isFalse();
        }

        @Test
        void 빈_좌표라면_false를_반환한다() {
            boolean result = alivePieces.placedOppositeSide(emptyIntersection, SIDE);

            assertThat(result).isFalse();
        }
    }

    @Nested
    class 궁이_살아있는지_판단한다 {

        @Test
        void 궁이_존재한다면_true를_반환한다() {
            // given
            AlivePieces withGeneral = new AlivePieces(Map.of(
                    DEFAULT_INTERSECTION, new General(SIDE)
            ));

            // when
            boolean hasRoyalPiece = withGeneral.hasRoyalPiece(SIDE);

            // then
            assertThat(hasRoyalPiece).isTrue();
        }

        @Test
        void 궁이_존재하지_않는다면_false를_반환한다() {
            // given
            AlivePieces emptyAlivePieces = new AlivePieces(Map.of());

            // when
            boolean hasRoyalPiece = emptyAlivePieces.hasRoyalPiece(SIDE);

            // then
            assertThat(hasRoyalPiece).isFalse();
        }
    }

    @Test
    void 기물_점수의_총_합을_계산한다() {
        // given
        Piece piece1 = new Soldier(SIDE);
        Piece piece2 = new Chariot(SIDE);
        AlivePieces alivePieces = new AlivePieces(Map.of(
                new Intersection(3, 3), piece1,
                new Intersection(4, 4), piece2
        ));

        double expected = piece1.getScore() + piece2.getScore();

        // when
        double actual = alivePieces.getTotalScore(SIDE);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
