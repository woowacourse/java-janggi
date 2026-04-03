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
}
