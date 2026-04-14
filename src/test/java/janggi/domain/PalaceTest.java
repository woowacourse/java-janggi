package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    private final Palace palace = new Palace();

    @Test
    @DisplayName("궁성 내부 좌표를 판별할 수 있다.")
    void isInside() {
        assertAll(
            () -> assertThat(palace.isInside(new Position(5, 2), TeamType.CHU)).isTrue(),
            () -> assertThat(palace.isInside(new Position(5, 9), TeamType.HAN)).isTrue(),
            () -> assertThat(palace.isInside(new Position(5, 5))).isFalse()
        );
    }

    @Test
    @DisplayName("궁성 한 칸 이동 경로를 찾을 수 있다.")
    void findOneStepMovePath() {
        assertAll(
            () -> assertThat(palace.findOneStepMovePath(new Position(5, 2), new Position(5, 3))).isPresent(),
            () -> assertThat(palace.findOneStepMovePath(new Position(5, 2), new Position(4, 1))).isPresent(),
            () -> assertThat(palace.findOneStepMovePath(new Position(4, 2), new Position(5, 1))).isEmpty()
        );
    }

    @Test
    @DisplayName("궁성 대각선 경로를 찾을 수 있다.")
    void findDiagonalMovePath() {
        assertAll(
            () -> assertThat(palace.findDiagonalMovePath(new Position(4, 1), new Position(5, 2))).isPresent(),
            () -> assertThat(palace.findDiagonalMovePath(new Position(4, 1), new Position(6, 3))).isPresent(),
            () -> assertThat(palace.findDiagonalMovePath(new Position(4, 2), new Position(5, 1))).isEmpty(),
            () -> assertThat(palace.findDiagonalMovePath(new Position(4, 1), new Position(6, 10))).isEmpty()
        );
    }

    @Test
    @DisplayName("졸의 궁성 전진 대각선 경로를 찾을 수 있다.")
    void findForwardDiagonalStepPath() {
        assertAll(
            () -> assertThat(
                palace.findForwardDiagonalStepPath(new Position(4, 1), new Position(5, 2), TeamType.CHU)
            ).isPresent(),
            () -> assertThat(
                palace.findForwardDiagonalStepPath(new Position(5, 2), new Position(4, 1), TeamType.CHU)
            ).isEmpty(),
            () -> assertThat(
                palace.findForwardDiagonalStepPath(new Position(6, 10), new Position(5, 9), TeamType.HAN)
            ).isPresent()
        );
    }
}
