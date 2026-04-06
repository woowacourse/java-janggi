package janggi.domain;

import janggi.domain.piece.Palace;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PalaceTest {

    @DisplayName("궁성에 해당하는 포지션이면 true를 반환한다")
    @Test
    void isPalace_CorrectPosition_ReturnTrue() {
        Palace palace = new Palace();

        assertThat(palace.isPalace(Position.of(1, 4))).isTrue();
    }

    @DisplayName("궁성에 해당하지 않은 포지션이면 false를 반환한다")
    @Test
    void isPalace_IncorrectPosition_ReturnFalse() {
        Palace palace = new Palace();

        assertThat(palace.isPalace(Position.of(0, 0))).isFalse();
    }

}
