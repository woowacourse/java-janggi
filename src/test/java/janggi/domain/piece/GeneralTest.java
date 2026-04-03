package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.ChariotStrategy;
import janggi.domain.piece.strategy.GeneralStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneralTest {

    @DisplayName("장을 넘어갈 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeJumpedOver_Always_ReturnTrue() {
        Piece piece = new General(Camp.CHO, new GeneralStrategy());
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @DisplayName("포가 장을 잡을 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeCapturedByCannon_Always_ReturnTrue() {
        Piece piece = new General(Camp.CHO, new GeneralStrategy());
        assertThat(piece.canBeCapturedByCannon()).isTrue();
    }

    @DisplayName("장의 경로 확인은 항상 True를 반환한다")
    @Test
    void canPassRoute_Always_ReturnTrue() {
        Piece piece = new General(Camp.CHO, new GeneralStrategy());
        assertThat(piece.canPassRoute(new HashMap<>())).isTrue();
    }

    @DisplayName("도착 지점에 있는 기물이 같은 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsSameCamp_ReturnFalse() {
        Piece piece = new General(Camp.CHO, new GeneralStrategy());
        Piece desinationPiece = new Chariot(Camp.CHO, new ChariotStrategy());
        assertThat(piece.canCatch(desinationPiece)).isFalse();
    }

    @DisplayName("도착 지점에 있는 기물이 다른 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsNotSameCamp_ReturnTrue() {
        Piece piece = new General(Camp.CHO, new GeneralStrategy());
        Piece desinationPiece = new Chariot(Camp.HAN, new ChariotStrategy());
        assertThat(piece.canCatch(desinationPiece)).isTrue();
    }
}
