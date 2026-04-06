package janggi.domain.rule.collision;

import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PoCollisionDetectorTest {

    private static final PoCollisionDetector PO_COLLISION_DETECTOR = PoCollisionDetector.getInstance();
    private static final Piece EMPTY = EmptyPiece.getInstance();

    @Test
    @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 마지막 경로 좌표에 기물이 존재하지 않으면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenOneNonPoObstacleAndEmptyDestination() {
        // given
        List<Piece> piecesOnPath = List.of(EMPTY, new TestPiece(PieceType.CHA, Side.HAN), EMPTY);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> PO_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 마지막 경로에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenOneNonPoObstacleAndEnemyAtDestination() {
        // given
        List<Piece> piecesOnPath = List.of(EMPTY, new TestPiece(PieceType.CHA, Side.HAN), new TestPiece(PieceType.CHA, Side.HAN));

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> PO_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 도착지에 상대팀의 포가 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenOneNonPoObstacleAndPoOfOtherSideOnDestination() {
        // given
        List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, Side.CHO), EMPTY, new TestPiece(PieceType.PO, Side.HAN));

        // when & then
        Assertions.assertThatThrownBy(() -> PO_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 도착지에 우리팀 기물이 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenOneNonPoObstacleAndSameSidePieceOnDestination() {
        // given
        List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.MA, Side.CHO), EMPTY, new TestPiece(PieceType.CHA, Side.CHO));

        // when & then
        Assertions.assertThatThrownBy(() -> PO_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 장애물이 2개 이상 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenTwoOrMorePieceOnPathExist() {
        // given
        List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, Side.HAN), new TestPiece(PieceType.CHA, Side.HAN), EMPTY);

        // when & then
        Assertions.assertThatThrownBy(() -> PO_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenNoObstacleOnPath() {
        // given
        List<Piece> piecesOnPath = List.of(EMPTY, EMPTY, new TestPiece(PieceType.CHA, Side.HAN));

        // when & then
        Assertions.assertThatThrownBy(() -> PO_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 포가 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenPoOnPath() {
        // given
        List<Piece> piecesOnPath = List.of(EMPTY, new TestPiece(PieceType.PO, Side.HAN), new TestPiece(PieceType.CHA, Side.HAN));

        // when & then
        Assertions.assertThatThrownBy(() -> PO_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
