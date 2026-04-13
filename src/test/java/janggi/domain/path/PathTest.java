package janggi.domain.path;

import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {

    @DisplayName("경로에 좌표를 추가하면 저장된다.")
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

    @DisplayName("빈 경로를 순회하면 비어있다.")
    @Test
    void 빈_경로를_순회하면_비어있다() {
        // given
        Path path = new Path();

        // when & then
        assertThat(path).isEmpty();
    }
}
