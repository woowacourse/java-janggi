package janggi.domain.piece;

import janggi.domain.mouveRule.ElephantMoveRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {
    private final Piece elephant = new Elephant(Team.HAN);

    @Test
    void moveRule_연결_테스트() {
        // when, then
        assertThat(elephant.moveRule()).isInstanceOf(ElephantMoveRule.class);
    }

}