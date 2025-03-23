package domain.board;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;

import fixture.BoardFixture;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NodeTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 특정_위치에서_특정_방향으로_가는_경로가_있으면_true를_반환한다() {
            // given
            final Node centerNode = new Node();
            final Node upNode = new Node();
            Node leftNode = new Node();
            Node rightNode = new Node();
            Node downNode = new Node();
            Edge upEdge = new Edge(upNode, UP);
            Edge leftEdge = new Edge(leftNode, LEFT);
            Edge rightEdge = new Edge(rightNode, RIGHT);
            Edge downEdge = new Edge(downNode, DOWN);
            List<Edge> edges = List.of(upEdge, leftEdge, rightEdge, downEdge);

            centerNode.addAllEdges(edges);

            // when
            final boolean actual = centerNode.hasNextNode(DOWN);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에서_특정_방향으로_가는_경로가_없으면_false를_반환한다() {
            // given
            final Node centerNode = new Node();
            final Node upNode = new Node();
            Node leftNode = new Node();
            Node rightNode = new Node();

            Edge upEdge = new Edge(upNode, UP);
            Edge leftEdge = new Edge(leftNode, LEFT);
            Edge rightEdge = new Edge(rightNode, RIGHT);
            List<Edge> edges = List.of(upEdge, leftEdge, rightEdge);

            centerNode.addAllEdges(edges);

            // when
            final boolean actual = centerNode.hasNextNode(DOWN);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에서_경로를_따라_이동한_위치가_판_내부_위치면_true를_반환한다() {
            // given
            final PointNodeMapper pointNodeMapper = BoardFixture.createDefaultPointNodeMapper();
            Point point = Point.of(2, 3);
            Node node = pointNodeMapper.getNodeByPoint(point);
            Path path = Path.RIGHT_RIGHT_UP_PATH;

            // when
            final boolean actual = node.canMoveByPath(path);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에서_경로를_따라_이동한_위치가_판을_벗어난_위치면_false를_반환한다() {
            // given
            final PointNodeMapper pointNodeMapper = BoardFixture.createDefaultPointNodeMapper();
            Point point = Point.of(2, 3);
            Node node = pointNodeMapper.getNodeByPoint(point);
            Path path = Path.RIGHT_RIGHT_RIGHT_UP_UP_PATH;

            // when
            final boolean actual = node.canMoveByPath(path);

            // then
            Assertions.assertThat(actual).isFalse();
        }
    }
}
