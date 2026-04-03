package janggi.domain.path;

import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    void 경로에_좌표를_추가하면_저장된다() {
        Path path = new Path();
        Position position = Position.of(2, 3);

        path.add(position);

        assertThat(path).containsExactly(position);
    }

    @Test
    void 빈_경로를_순회하면_비어있다() {
        Path path = new Path();

        assertThat(path).isEmpty();
    }
}