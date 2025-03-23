package domain.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("엣지 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EdgeTest {

    @Test
    void 위치가_같으면_true를_반환한다() {
        // given
        Node node = new Node(Point.of(1, 1));
        Direction direction = Direction.UP;
        Edge edge = new Edge(node, direction);

        // when & then
        assertThat(edge.isSameDirection(direction)).isTrue();
    }

    @Test
    void 위치가_다르면_false를_반환한다() {
        // given
        Node node = new Node(Point.of(1, 1));
        Direction direction = Direction.UP;
        Edge edge = new Edge(node, direction);

        // when
        Direction differentDirection = Direction.DOWN;

        // then
        assertThat(edge.isSameDirection(differentDirection)).isFalse();
    }
}
