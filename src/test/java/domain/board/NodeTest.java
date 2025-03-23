package domain.board;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;
import static domain.board.Direction.UP_RIGHT;

import domain.piece.Byeong;
import domain.piece.Piece;
import domain.piece.Team;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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
            final Node leftNode = new Node();
            final Node rightNode = new Node();
            final Node downNode = new Node();
            final Edge upEdge = new Edge(upNode, UP);
            final Edge leftEdge = new Edge(leftNode, LEFT);
            final Edge rightEdge = new Edge(rightNode, RIGHT);
            final Edge downEdge = new Edge(downNode, DOWN);
            final List<Edge> edges = List.of(upEdge, leftEdge, rightEdge, downEdge);

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
            final Node leftNode = new Node();
            final Node rightNode = new Node();

            final Edge upEdge = new Edge(upNode, UP);
            final Edge leftEdge = new Edge(leftNode, LEFT);
            final Edge rightEdge = new Edge(rightNode, RIGHT);
            final List<Edge> edges = List.of(upEdge, leftEdge, rightEdge);

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
            final Point point = Point.of(2, 3);
            final Node node = pointNodeMapper.getNodeByPoint(point);
            final Path path = Path.RIGHT_RIGHT_UP_PATH;

            // when
            final boolean actual = node.canMoveByPath(path);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에서_경로를_따라_이동한_위치가_판을_벗어난_위치면_false를_반환한다() {
            // given
            final PointNodeMapper pointNodeMapper = BoardFixture.createDefaultPointNodeMapper();
            final Point point = Point.of(2, 3);
            final Node node = pointNodeMapper.getNodeByPoint(point);
            final Path path = Path.RIGHT_RIGHT_RIGHT_UP_UP_PATH;

            // when
            final boolean actual = node.canMoveByPath(path);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 간선이_있는_방향은_true_없는_방향은_false를_반환한다() {
            // given
            final Node upNode = new Node();
            final Node upRightNode = new Node();
            final Node centerNode = new Node();
            final Node rightNode = new Node();

            final Edge rightEdgeForUpNode = new Edge(upRightNode, RIGHT);
            final Edge downEdgeForUpNode = new Edge(centerNode, DOWN);
            upNode.addAllEdges(List.of(rightEdgeForUpNode, downEdgeForUpNode));

            final Edge leftEdgeForUpRightNode = new Edge(upNode, LEFT);
            final Edge downEdgeForUpRightNode = new Edge(rightNode, DOWN);
            upRightNode.addAllEdges(List.of(leftEdgeForUpRightNode, downEdgeForUpRightNode));

            final Edge upEdgeForCenterNode = new Edge(centerNode, UP);
            final Edge rightEdgeForCenterNode = new Edge(rightNode, RIGHT);
            centerNode.addAllEdges(List.of(upEdgeForCenterNode, rightEdgeForCenterNode));

            final Edge upEdgeForRightNode = new Edge(upRightNode, UP);
            final Edge leftEdgeForRightNode = new Edge(centerNode, LEFT);
            rightNode.addAllEdges(List.of(upEdgeForRightNode, leftEdgeForRightNode));

            // when & then
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(upNode.hasNextNode(UP)).isFalse();
                softly.assertThat(upNode.hasNextNode(RIGHT)).isTrue();
                softly.assertThat(upNode.hasNextNode(DOWN)).isTrue();
                softly.assertThat(upNode.hasNextNode(LEFT)).isFalse();

                softly.assertThat(upRightNode.hasNextNode(UP)).isFalse();
                softly.assertThat(upRightNode.hasNextNode(RIGHT)).isFalse();
                softly.assertThat(upRightNode.hasNextNode(DOWN)).isTrue();
                softly.assertThat(upRightNode.hasNextNode(LEFT)).isTrue();

                softly.assertThat(centerNode.hasNextNode(UP)).isTrue();
                softly.assertThat(centerNode.hasNextNode(RIGHT)).isTrue();
                softly.assertThat(centerNode.hasNextNode(DOWN)).isFalse();
                softly.assertThat(centerNode.hasNextNode(LEFT)).isFalse();

                softly.assertThat(rightNode.hasNextNode(UP)).isTrue();
                softly.assertThat(rightNode.hasNextNode(RIGHT)).isFalse();
                softly.assertThat(rightNode.hasNextNode(DOWN)).isFalse();
                softly.assertThat(rightNode.hasNextNode(LEFT)).isTrue();

                softly.assertThat(centerNode.hasNextNode(UP_RIGHT)).isFalse();
            });
        }
    }
}
