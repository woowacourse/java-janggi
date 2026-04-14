package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.piece.strategy.ChariotStrategy;
import janggi.domain.piece.strategy.ElephantStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ChariotTest {

    @DisplayName("차를 넘어갈 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeJumpedOver_Always_ReturnTrue() {
        Piece piece = new Chariot(Camp.CHO, new ChariotStrategy());
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @DisplayName("포가 차를 잡을 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeCapturedByCannon_Always_ReturnTrue() {
        Piece piece = new Chariot(Camp.CHO, new ChariotStrategy());
        assertThat(piece.canBeCapturedByCannon()).isTrue();
    }

    @DisplayName("경로에 기물이 있으면 False를 반환한다")
    @Test
    void canPassRoute_PiecesInPathIsNotEmpty_ReturnFalse() {
        Piece piece = new Chariot(Camp.CHO, new ChariotStrategy());
        Map<JanggiPosition, Piece> piecesInPath = new HashMap<>();
        piecesInPath.put(JanggiPosition.of(3, 3),
                new Elephant(Camp.HAN, new ElephantStrategy()));
        assertThat(piece.canPassRoute(piecesInPath)).isFalse();
    }

    @DisplayName("경로에 기물이 없으면 True를 반환한다")
    @Test
    void canPassRoute_PiecesInPathIsEmpty_ReturnTrue() {
        Piece piece = new Chariot(Camp.CHO, new ChariotStrategy());
        Map<JanggiPosition, Piece> piecesInPath = new HashMap<>();
        assertThat(piece.canPassRoute(piecesInPath)).isTrue();
    }

    @DisplayName("도착 지점에 있는 기물이 같은 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsSameCamp_ReturnFalse() {
        Piece piece = new Chariot(Camp.CHO, new ChariotStrategy());
        Piece destinationPiece = new Elephant(Camp.CHO, new ElephantStrategy());
        assertThat(piece.canCatch(destinationPiece)).isFalse();
    }

    @DisplayName("도착 지점에 있는 기물이 다른 진영이면 True를 반환한다")
    @Test
    void canCatch_DestinationPieceIsNotSameCamp_ReturnTrue() {
        Piece piece = new Chariot(Camp.CHO, new ChariotStrategy());
        Piece destinationPiece = new Elephant(Camp.HAN, new ElephantStrategy());
        assertThat(piece.canCatch(destinationPiece)).isTrue();
    }
}
