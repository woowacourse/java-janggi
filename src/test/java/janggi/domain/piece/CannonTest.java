package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonTest {
    @DisplayName("포는 포에 의해 잡힐 수 없다.")
    @Test
    void canNotBeCaughtByCannon() {
        Piece piece = new Cannon(Camp.CHO);
        assertThat(piece.canBeCaughtByCannon()).isFalse();
    }

    @DisplayName("포는 넘을 수 없다.")
    @Test
    void canNotBeJumpedOver() {
        Piece piece = new Cannon(Camp.CHO);
        assertThat(piece.canBeJumpedOver()).isFalse();
    }

    @DisplayName("이동 경로에 기물이 없으면 False를 반환한다")
    @Test
    void canPassRoute_PieceInPathSizeIsZero_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO);
        Map<Position, Piece> pieceInPath = new HashMap<>();
        assertThat(piece.canPassRoute(pieceInPath)).isFalse();
    }

    @DisplayName("이동 경로에 기물이 2개 이상이면 False를 반환한다")
    @Test
    void canPassRoute_PieceInPathSizeOverTwo_Return_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO);
        Map<Position, Piece> pieceInPath = new HashMap<>();
        pieceInPath.put(Position.of(3, 3), new Elephant(Camp.CHO));
        pieceInPath.put(Position.of(3, 4), new Elephant(Camp.CHO));
        assertThat(piece.canPassRoute(pieceInPath)).isFalse();
    }

    @DisplayName("이동 경로에 포가 있으면 False를 반환한다")
    @Test
    void canPassRoute_PieceInPathIsCannon_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO);
        Map<Position, Piece> pieceInPath = new HashMap<>();
        pieceInPath.put(Position.of(3, 3), new Cannon(Camp.CHO));
        assertThat(piece.canPassRoute(pieceInPath)).isFalse();
    }

    @DisplayName("이동 경로에 기물이 1개이고, 그 기물이 포가 아니면 True를 반환한다")
    @Test
    void canPassRoute_PieceInPathNotCannon_ReturnTrue() {
        Piece piece = new Cannon(Camp.CHO);
        Map<Position, Piece> pieceInPath = new HashMap<>();
        pieceInPath.put(Position.of(3, 3), new Elephant(Camp.CHO));
        assertThat(piece.canPassRoute(pieceInPath)).isTrue();
    }

    @DisplayName("도착 지점의 기물이 포이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsCannon_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO);
        Piece destinationPiece = new Cannon(Camp.HAN);
        assertThat(piece.canCatch(destinationPiece)).isFalse();
    }

    @DisplayName("도착 지점의 기물이 같은 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsSameCamp_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO);
        Piece destinationPiece = new Elephant(Camp.CHO);
        assertThat(piece.canCatch(destinationPiece)).isFalse();
    }

    @DisplayName("도착 지점의 기물이 포가 아니고 다른 진영이면 True를 반환한다")
    @Test
    void canCatch_DestinationPieceIsNotCannonAndIsNotSameCamp_ReturnTrue() {
        Piece piece = new Cannon(Camp.CHO);
        Piece destinationPiece = new Elephant(Camp.HAN);
        assertThat(piece.canCatch(destinationPiece)).isTrue();
    }

    @Test
    void 필수적인_기물이_아니면_false를_출력한다() {
        Piece piece = new Cannon(Camp.CHO);
        boolean essential = piece.isEssential();

        assertThat(essential).isFalse();
    }
}
