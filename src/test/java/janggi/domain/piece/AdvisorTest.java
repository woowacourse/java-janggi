package janggi.domain.piece;

import janggi.domain.mouveRule.AdvisorMoveRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdvisorTest {
    private final Piece advisor = new Advisor(Team.HAN);

    @Test
    void moveRule_연결_테스트() {
        // when, then
        assertThat(advisor.moveRule()).isInstanceOf(AdvisorMoveRule.class);
    }
}