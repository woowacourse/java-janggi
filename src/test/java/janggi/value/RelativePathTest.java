package janggi.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RelativePathTest {

    @DisplayName("출발지를 통해 경로를 계산할 수 있다.")
    @Test
    void calculatePath() {
        RelativePath relativePath = new RelativePath(List.of(Direction.ORIGIN, Direction.DOWN_LEFT, Direction.UP_LEFT));

        Path path = relativePath.calculatePath(new Position(0, 0));

        assertAll(
                () -> assertThat(path.getStart()).isEqualTo(new Position(0, 0)),
                () -> assertThat(path.getMiddle()).contains(new Position(-1, 1)),
                () -> assertThat(path.getEnd()).isEqualTo(new Position(-2, 0))
        );
    }

    @DisplayName("출발지를 통해 목적지를 계산할 수 있다.")
    @Test
    void calculateDestination() {
        RelativePath relativePath = new RelativePath(List.of(Direction.DOWN_LEFT, Direction.UP_LEFT));
        Position destination = relativePath.getDestination(new Position(0, 0));
        assertThat(destination).isEqualTo(new Position(-2, 0));
    }
}