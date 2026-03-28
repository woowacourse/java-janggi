package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.ElephantStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantTest {

    @DisplayName("상이 포인지 확인하는 테스트 (항상 false)")
    @Test
    void isCannon_Always_ReturnFalse() {
        Piece piece = new Elephant(Camp.CHO, new ElephantStrategy());
        assertThat(piece.isCannon()).isFalse();
    }
}