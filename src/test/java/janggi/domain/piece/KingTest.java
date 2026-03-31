package janggi.domain.piece;

import janggi.domain.mouveRule.KingMoveRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    private final Piece king = new King(Team.HAN);

    @Test
    void moveRule_연결_테스트() {
        // when, then
        assertThat(king.moveRule()).isInstanceOf(KingMoveRule.class);
    }
}
