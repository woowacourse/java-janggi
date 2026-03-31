package janggi.domain.piece;

import janggi.domain.mouveRule.HorseMoveRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {
    private final Piece horse = new Horse(Team.HAN);

    @Test
    void moveRule_연결_테스트() {
        // when, then
        assertThat(horse.moveRule()).isInstanceOf(HorseMoveRule.class);
    }

}