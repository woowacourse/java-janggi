package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class SlidingPathTest {

    @Test
    void 위_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(3, 3);
        Position destination = departure.moveUp().moveUp().moveUp();
        SlidingPath slidingPath = new SlidingPath(Direction.UP);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveUp(),
                departure.moveUp().moveUp()
        );
    }

    @Test
    void 아래_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(6, 3);
        Position destination = departure.moveDown().moveDown().moveDown();
        SlidingPath slidingPath = new SlidingPath(Direction.DOWN);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveDown(),
                departure.moveDown().moveDown()
        );
    }

    @Test
    void 왼쪽_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(3, 3);
        Position destination = departure.moveLeft().moveLeft().moveLeft();
        SlidingPath slidingPath = new SlidingPath(Direction.LEFT);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveLeft(),
                departure.moveLeft().moveLeft()
        );
    }

    @Test
    void 오른쪽_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(3, 2);
        Position destination = departure.moveRight().moveRight().moveRight();
        SlidingPath slidingPath = new SlidingPath(Direction.RIGHT);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveRight(),
                departure.moveRight().moveRight()
        );
    }

    @Test
    void 우상향_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(3, 2);
        Position destination = departure.moveRightUp().moveRightUp();
        SlidingPath slidingPath = new SlidingPath(Direction.RIGHT_UP);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveRightUp()
        );
    }

    @Test
    void 좌상향_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(3, 4);
        Position destination = departure.moveLeftUp().moveLeftUp();
        SlidingPath slidingPath = new SlidingPath(Direction.LEFT_UP);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveLeftUp()
        );
    }

    @Test
    void 우하향_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(5, 2);
        Position destination = departure.moveRightDown().moveRightDown();
        SlidingPath slidingPath = new SlidingPath(Direction.RIGHT_DOWN);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveRightDown()
        );
    }

    @Test
    void 좌하향_방향으로_destination_직전까지_경로를_반환한다() {
        // given
        Position departure = new Position(5, 4);
        Position destination = departure.moveLeftDown().moveLeftDown();
        SlidingPath slidingPath = new SlidingPath(Direction.LEFT_DOWN);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).containsExactly(
                departure.moveLeftDown()
        );
    }

    @Test
    void 도착지가_한_칸_차이면_빈_경로를_반환한다() {
        // given
        Position departure = new Position(3, 3);
        Position destination = departure.moveUp();
        SlidingPath slidingPath = new SlidingPath(Direction.UP);

        // when
        List<Position> pathPositions = slidingPath.pathPositions(departure, destination);

        // then
        assertThat(pathPositions).isEmpty();
    }
}
