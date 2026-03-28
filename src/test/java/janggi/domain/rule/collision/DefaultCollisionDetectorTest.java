package janggi.domain.rule.collision;

import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultCollisionDetectorTest {

    private static final DefaultCollisionDetector DEFAULT_COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않고, 마지막 경로에 위치한 기물이 존재하지 않으면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenNoPieceOnPathAndNoPieceOnDestination() {
        // given
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), EmptyPiece.getInstance());
        Piece piece = new TestPiece(Side.CHO);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(piece, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않고, 마지막 경로에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsOtherSide() {
        // given
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), EmptyPiece.getInstance(), new TestPiece(Side.HAN));
        Piece piece = new TestPiece(Side.CHO);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(piece, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenPieceOnPathExist() {
        // given
        List<Piece> piecesOnPath = List.of(new TestPiece(Side.HAN), EmptyPiece.getInstance(), EmptyPiece.getInstance());
        Piece piece = new TestPiece(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(piece, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않고, 마지막 경로에 위치한 기물이 우리팀이면 예외를 발생시킨다.")
    void shouldThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsMySide() {
        // given
        Side side = Side.HAN;
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), EmptyPiece.getInstance(), new TestPiece(side));
        Piece piece = new TestPiece(side);

        // when & then
        Assertions.assertThatThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(piece, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
