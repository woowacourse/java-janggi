package janggi.value;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RelativePathTest {

    @DisplayName("출발지를 통해 목적지를 계산할 수 있다.")
    @Test
    void calculateDestination() {
        RelativePath relativePath = new RelativePath(List.of(Direction.DOWN_LEFT, Direction.UP_LEFT));
        Position destination = relativePath.getDestination(new Position(0, 0));
        Assertions.assertThat(destination).isEqualTo(new Position(-2, 0));
    }

}