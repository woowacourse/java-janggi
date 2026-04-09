package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceBoundsTest {

    private final PalaceBounds palace = new PalaceBounds(0, 2, 3, 5);

    @Test
    @DisplayName("궁성 범위 내의 좌표는 true를 반환한다.")
    void contains_True_Test() {
        assertThat(palace.contains(0, 3)).isTrue();
        assertThat(palace.contains(1, 4)).isTrue();
        assertThat(palace.contains(2, 5)).isTrue();
    }

    @Test
    @DisplayName("궁성 범위 밖의 좌표는 false를 반환한다.")
    void contains_False_Test() {
        assertThat(palace.contains(0, 2)).isFalse();
        assertThat(palace.contains(1, 6)).isFalse();
        assertThat(palace.contains(3, 4)).isFalse();
    }

    @Test
    @DisplayName("대칭된 궁성을 생성한다.")
    void mirror_Test() {
        PalaceBounds mirrored = palace.mirror(10);

        assertThat(mirrored.contains(7, 3)).isTrue();
        assertThat(mirrored.contains(8, 4)).isTrue();
        assertThat(mirrored.contains(9, 5)).isTrue();
        assertThat(mirrored.contains(6, 4)).isFalse();
    }
}