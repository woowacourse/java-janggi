package janggi.domain.piece;


import janggi.domain.Camp;
import janggi.domain.position.Position;
import janggi.domain.piece.strategy.CannonStrategy;
import janggi.domain.piece.strategy.ElephantStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonTest {

    @DisplayName("포가 포인지 확인하는 테스트 (항상 true)")
    @Test
    void isCannon_Always_ReturnTrue() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        assertThat(piece.isCannon()).isTrue();
    }

    @DisplayName("이동 경로에 기물이 없으면 False를 반환한다")
    @Test
    void canPassRoute_PieceInPathSizeIsZero_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        Map<Position, Piece> pieceInPath = new HashMap<>();
        assertThat(piece.canPassRoute(pieceInPath)).isFalse();
    }

    @DisplayName("이동 경로에 기물이 2개 이상이면 False를 반환한다")
    @Test
    void canPassRoute_PieceInPathSizeOverTwo_Return_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        Map<Position, Piece> pieceInPath = new HashMap<>();
        pieceInPath.put(Position.of(3, 3), new Elephant(Camp.CHO, new ElephantStrategy()));
        pieceInPath.put(Position.of(3, 4), new Elephant(Camp.CHO, new ElephantStrategy()));
        assertThat(piece.canPassRoute(pieceInPath)).isFalse();
    }

    @DisplayName("이동 경로에 포가 있으면 False를 반환한다")
    @Test
    void canPassRoute_PieceInPathIsCannon_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        Map<Position, Piece> pieceInPath = new HashMap<>();
        pieceInPath.put(Position.of(3, 3), new Cannon(Camp.CHO, new CannonStrategy()));
        assertThat(piece.canPassRoute(pieceInPath)).isFalse();
    }

    @DisplayName("이동 경로에 기물이 1개이고, 그 기물이 포가 아니면 True를 반환한다")
    @Test
    void canPassRoute_PieceInPathNotCannon_ReturnTrue() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        Map<Position, Piece> pieceInPath = new HashMap<>();
        pieceInPath.put(Position.of(3, 3), new Elephant(Camp.CHO, new ElephantStrategy()));
        assertThat(piece.canPassRoute(pieceInPath)).isTrue();
    }

    @DisplayName("도착 지점의 기물이 포이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsCannon_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        Piece destinationPiece = new Cannon(Camp.HAN, new CannonStrategy());
        assertThat(piece.canCatch(destinationPiece)).isFalse();
    }

    @DisplayName("도착 지점의 기물이 같은 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsSameCamp_ReturnFalse() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        Piece destinationPiece = new Elephant(Camp.CHO, new ElephantStrategy());
        assertThat(piece.canCatch(destinationPiece)).isFalse();
    }

    @DisplayName("도착 지점의 기물이 포가 아니고 다른 진영이면 True를 반환한다")
    @Test
    void canCatch_DestinationPieceIsNotCannonAndIsNotSameCamp_ReturnTrue() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        Piece destinationPiece = new Elephant(Camp.HAN, new ElephantStrategy());
        assertThat(piece.canCatch(destinationPiece)).isTrue();
    }
}
