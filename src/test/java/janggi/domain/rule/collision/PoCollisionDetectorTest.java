package janggi.domain.rule.collision;

import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Po;
import janggi.exception.PieceOnPathException;
import janggi.support.TestPiece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PoCollisionDetectorTest {

    @Test
    @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 마지막 경로 좌표에 기물이 존재하지 않으면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenOneNonPoObstacleAndEmptyDestination() {
        // given
        CollisionDetector collisionDetector = PoCollisionDetector.getInstance();
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), new TestPiece(Side.HAN), EmptyPiece.getInstance());
        Piece piece = new Po(Side.CHO);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> collisionDetector.check(piece, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 포가 아닌 장애물이 1개 존재하고, 마지막 경로에 위치한 기물이 상대팀이면 예외를 반환하지 않는다.")
    void shouldNotThrowExceptionWhenOneNonPoObstacleAndEnemyAtDestination() {
        // given
        CollisionDetector collisionDetector = PoCollisionDetector.getInstance();
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), new TestPiece(Side.HAN), new TestPiece(Side.HAN));
        Piece piece = new Po(Side.CHO);

        // when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> collisionDetector.check(piece, piecesOnPath));
    }

    @Test
    @DisplayName("이동 경로에 장애물이 2개 이상 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenTwoOrMorePieceOnPathExist() {
        // given
        CollisionDetector collisionDetector = PoCollisionDetector.getInstance();
        List<Piece> piecesOnPath = List.of(new TestPiece(Side.HAN), new TestPiece(Side.HAN), EmptyPiece.getInstance());
        Piece piece = new Po(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> collisionDetector.check(piece, piecesOnPath))
                .isInstanceOf(PieceOnPathException.class);
    }

    @Test
    @DisplayName("이동 경로에 장애물이 존재하지 않는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenNoObstacleOnPath() {
        // given
        CollisionDetector collisionDetector = PoCollisionDetector.getInstance();
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), EmptyPiece.getInstance(), new TestPiece(Side.HAN));
        Piece piece = new Po(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> collisionDetector.check(piece, piecesOnPath))
                .isInstanceOf(PieceOnPathException.class);
    }

    @Test
    @DisplayName("이동 경로에 포가 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenPoOnPath() {
        // given
        CollisionDetector collisionDetector = PoCollisionDetector.getInstance();
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), new Po(Side.HAN), new TestPiece(Side.HAN));
        Piece piece = new Po(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> collisionDetector.check(piece, piecesOnPath))
                .isInstanceOf(PieceOnPathException.class);
    }

    @Test
    @DisplayName("도착지에 상대팀의 포가 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenPoOfOtherSideOnDestination() {
        // given
        CollisionDetector collisionDetector = PoCollisionDetector.getInstance();
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), EmptyPiece.getInstance(), new Po(Side.HAN));
        Piece piece = new Po(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> collisionDetector.check(piece, piecesOnPath))
                .isInstanceOf(PieceOnPathException.class);
    }

    @Test
    @DisplayName("도착지에 우리팀 기물이 존재하는 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenSameSidePieceOnDestination() {
        // given
        CollisionDetector collisionDetector = PoCollisionDetector.getInstance();
        List<Piece> piecesOnPath = List.of(EmptyPiece.getInstance(), EmptyPiece.getInstance(), new TestPiece(Side.CHO));
        Piece piece = new Po(Side.CHO);

        // when & then
        Assertions.assertThatThrownBy(() -> collisionDetector.check(piece, piecesOnPath))
                .isInstanceOf(PieceOnPathException.class);
    }
}
