package domain.board;

import domain.Directions;
import fixture.BoardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("노드 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NodeTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 특정_위치가_현재_위치와_동일하면_true를_반환한다() {
            // given
            Board board = BoardFixture.createEmptyBoard();
            Point point = Point.of(10, 1);
            Node node = board.findNodeByPoint(point);

            // when
            final boolean actual = node.isSameNode(node);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에서_특정_방향으로_가는_경로가_있으면_true를_반환한다() {
            // given
            Board board = BoardFixture.createEmptyBoard();
            Point point = Point.of(4, 1);
            Node node = board.findNodeByPoint(point);

            // when
            final boolean actual = node.hasEdgeByDirection(Direction.UP);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에서_특정_방향으로_가는_경로가_없으면_false를_반환한다() {
            // given
            Board board = BoardFixture.createEmptyBoard();
            Point point = Point.of(4, 1);
            Node node = board.findNodeByPoint(point);

            // when
            final boolean actual = node.hasEdgeByDirection(Direction.LEFT);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에서_경로를_따라_이동한_위치가_판_내부_위치면_true를_반환한다() {
            // given
            Board board = BoardFixture.createEmptyBoard();
            Point point = Point.of(4, 5);
            Node node = board.findNodeByPoint(point);
            Directions directions = new Directions(List.of(Direction.DOWN, Direction.DOWN));

            // when
            final boolean actual = node.canMoveByPath(directions);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에서_경로를_따라_이동한_위치가_판을_벗어난_위치면_false를_반환한다() {
            // given
            Board board = BoardFixture.createEmptyBoard();
            Point point = Point.of(1, 5);
            Node node = board.findNodeByPoint(point);
            Directions directions = new Directions(List.of(Direction.LEFT, Direction.UP));

            // when
            final boolean actual = node.canMoveByPath(directions);

            // then
            assertThat(actual).isFalse();
        }
    }
}
