package janggi.domain;

import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PathsTest {
    @Test
    @DisplayName("도착지로 경로를 찾을 수 있다")
    void findPathByDestination() {
        Paths paths = new Paths(List.of(
                Path.of(Position.of(1, 1)),
                Path.of(Position.of(2, 2)),
                Path.of(Position.of(3, 3))
        ));

        assertThat(paths.findPathByDestination(Position.of(2, 2))
                .isDestination(Position.of(2, 2))).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 도착지를 찾으면 예외가 발생한다")
    void findPathByDestination_NotFound() {
        Paths paths = new Paths(List.of(
                Path.of(Position.of(1, 1))
        ));

        assertThatThrownBy(() -> paths.findPathByDestination(Position.of(9, 8)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 좌표입니다.");
    }

    @Test
    @DisplayName("빈 경로 목록에서 찾으면 예외가 발생한다")
    void findPathByDestination_EmptyPaths() {
        Paths paths = new Paths(List.of());

        assertThatThrownBy(() -> paths.findPathByDestination(Position.of(4, 4)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}