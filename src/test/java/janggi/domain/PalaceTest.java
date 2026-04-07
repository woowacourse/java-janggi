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

    @DisplayName("해당 위치가 궁성의 귀에 해당하면 4개의 대각 방향을 반환한다")
    @Test
    void calculateDirections_IsDiagonalPosition_ReturnDiagonalDirections() {
        Palace palace = new Palace();

        assertThat(palace.calculateDirectionsByCamp(Position.of(0, 3))).hasSize(4);
    }

    @DisplayName("해당 위치가 궁성의 면에 해당하면 빈 리스트를 반환한다")
    @Test
    void calculateDirections_IsOrthogonalPosition_ReturnEmptyList() {
        Palace palace = new Palace();

        assertThat(palace.calculateDirectionsByCamp(Position.of(1, 3))).hasSize(0);
    }
}
