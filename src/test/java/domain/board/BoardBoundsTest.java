package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardBoundsTest {

    private final BoardBounds bounds = BoardBounds.JANGGI;

    @Test
    @DisplayName("한쪽 궁성 내부 좌표는 궁성으로 판단한다.")
    void isInPalace_Han_Test() {
        assertThat(bounds.isInPalace(0, 3)).isTrue();
        assertThat(bounds.isInPalace(1, 4)).isTrue();
        assertThat(bounds.isInPalace(2, 5)).isTrue();
    }

    @Test
    @DisplayName("대칭된 궁성 내부 좌표도 궁성으로 판단한다.")
    void isInPalace_Chu_Test() {
        assertThat(bounds.isInPalace(7, 3)).isTrue();
        assertThat(bounds.isInPalace(8, 4)).isTrue();
        assertThat(bounds.isInPalace(9, 5)).isTrue();
    }

    @Test
    @DisplayName("궁성 밖 좌표는 false를 반환한다.")
    void isInPalace_Outside_Test() {
        assertThat(bounds.isInPalace(0, 0)).isFalse();
        assertThat(bounds.isInPalace(4, 4)).isFalse();
        assertThat(bounds.isInPalace(1, 2)).isFalse();
    }

    @Test
    @DisplayName("궁성 경계 바로 밖의 좌표는 false를 반환한다.")
    void isInPalace_Boundary_Test() {
        assertThat(bounds.isInPalace(0, 2)).isFalse();
        assertThat(bounds.isInPalace(1, 6)).isFalse();
        assertThat(bounds.isInPalace(3, 4)).isFalse();
        assertThat(bounds.isInPalace(6, 4)).isFalse();
    }
}
