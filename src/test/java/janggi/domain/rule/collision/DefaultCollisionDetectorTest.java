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

class DefaultCollisionDetectorTest {

    private static final DefaultCollisionDetector DEFAULT_COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();
    private static final Piece EMPTY = EmptyPiece.getInstance();

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않고, 마지막 경로에 위치한 기물이 존재하지 않으면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenNoPieceOnPathAndNoPieceOnDestination() {
        // given
        List<Piece> piecesOnPath = List.of(EMPTY, EMPTY);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않고, 마지막 경로에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsOtherSide() {
        // given
        List<Piece> piecesOnPath = List.of(EMPTY, EMPTY, new TestPiece(PieceType.CHA, Side.HAN));

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenPieceOnPathExist() {
        // given
        List<Piece> piecesOnPath = List.of(new TestPiece(PieceType.CHA, Side.HAN), EMPTY, EMPTY);

        // when & then
        Assertions.assertThatThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(Side.CHO, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않고, 마지막 경로에 위치한 기물이 우리팀이면 예외를 발생시킨다.")
    void shouldThrowExceptionWhenNoPieceOnPathAndPieceOnDestinationIsMySide() {
        // given
        Side side = Side.HAN;
        List<Piece> piecesOnPath = List.of(EMPTY, EMPTY, new TestPiece(PieceType.CHA, side));

        // when & then
        Assertions.assertThatThrownBy(() -> DEFAULT_COLLISION_DETECTOR.check(side, piecesOnPath))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
