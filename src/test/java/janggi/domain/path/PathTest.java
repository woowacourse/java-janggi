package janggi.domain.path;

import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @Test
    void 경로에_좌표를_추가하면_저장된다() {
        // given
        Path path = new Path();
        Position position = Position.of(2, 3);

        // when
        path.add(position);

        // then
        assertThat(path).containsExactly(position);
    }

    @Test
    void 빈_경로를_순회하면_비어있다() {
        // given
        Path path = new Path();

        // when & then
        assertThat(path).isEmpty();
    }
}
