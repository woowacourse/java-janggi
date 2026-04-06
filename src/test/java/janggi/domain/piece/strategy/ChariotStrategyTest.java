package janggi.domain.piece.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotStrategyTest implements LinearMoveAssertion {

    @Test
    @DisplayName("차는 현재 위치에서 가로와 세로 직선상의 모든 좌표를 후보로 반환한다")
    void findMovablePaths_ReturnAllLinearCandidates() {
        assertLinearStrategy(new ChariotStrategy());
    }
}
