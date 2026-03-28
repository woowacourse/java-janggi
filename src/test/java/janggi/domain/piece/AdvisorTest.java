package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.AdvisorStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdvisorTest {

    @DisplayName("사가 포인지 확인하는 테스트 (항상 false)")
    @Test
    void isCannon_Always_ReturnFalse() {
        Piece piece = new Advisor(Camp.CHO, new AdvisorStrategy());
        assertThat(piece.isCannon()).isFalse();
    }
}