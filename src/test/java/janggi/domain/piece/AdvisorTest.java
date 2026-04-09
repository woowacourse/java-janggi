package janggi.domain.piece;

import janggi.domain.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class AdvisorTest {
    @DisplayName("사가 포에 의해 잡힐 수 있다.")
    @Test
    void canBeCaughtByCannon() {
        Piece piece = new Advisor(Camp.CHO);
        assertThat(piece.canBeCaughtByCannon()).isTrue();
    }

    @DisplayName("사는 넘을 수 있다.")
    @Test
    void canBeJumpedOver() {
        Piece piece = new Advisor(Camp.CHO);
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @DisplayName("사의 경로 확인은 항상 True를 반환한다")
    @Test
    void canPassRoute_Always_ReturnTrue() {
        Piece piece = new Advisor(Camp.CHO);
        assertThat(piece.canPassRoute(new HashMap<>())).isTrue();
    }

    @DisplayName("도착 지점에 있는 기물이 같은 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsSameCamp_ReturnFalse() {
        Piece piece = new Advisor(Camp.CHO);
        Piece desinationPiece = new Chariot(Camp.CHO);
        assertThat(piece.canCatch(desinationPiece)).isFalse();
    }

    @DisplayName("도착 지점에 있는 기물이 다른 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsNotSameCamp_ReturnTrue() {
        Piece piece = new Advisor(Camp.CHO);
        Piece desinationPiece = new Chariot(Camp.HAN);
        assertThat(piece.canCatch(desinationPiece)).isTrue();
    }

    @Test
    void 필수적인_기물이_아니면_false를_출력한다() {
        Piece piece = new Advisor(Camp.CHO);
        boolean essential = piece.isEssential();

        assertThat(essential).isFalse();
    }
}
