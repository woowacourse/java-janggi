package janggi.domain.piece;


import janggi.domain.Camp;
import janggi.domain.piece.strategy.CannonStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonTest {

    @DisplayName("포가 포인지 확인하는 테스트 (항상 true)")
    @Test
    void isCannon_Always_ReturnTrue() {
        Piece piece = new Cannon(Camp.CHO, new CannonStrategy());
        assertThat(piece.isCannon()).isTrue();
    }
}