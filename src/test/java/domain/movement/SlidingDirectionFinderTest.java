package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import org.junit.jupiter.api.Test;

class SlidingDirectionFinderTest {

    private final SlidingDirectionFinder slidingDirectionFinder = new SlidingDirectionFinder();

    @Test
    void 같은_행에서_도착지가_왼쪽이면_LEFT를_반환한다() {
        // given
        Position departure = new Position(3, 3);
        Position destination = new Position(3, 1);

        // when
        Direction direction = slidingDirectionFinder.find(departure, destination);

        // then
        assertThat(direction).isEqualTo(Direction.LEFT);
    }

    @Test
    void 같은_행에서_도착지가_오른쪽이면_RIGHT를_반환한다() {
        // given
        Position departure = new Position(3, 3);
        Position destination = new Position(3, 5);

        // when
        Direction direction = slidingDirectionFinder.find(departure, destination);

        // then
        assertThat(direction).isEqualTo(Direction.RIGHT);
    }

    @Test
    void 같은_열에서_도착지가_아래이면_DOWN을_반환한다() {
        // given
        Position departure = new Position(3, 3);
        Position destination = new Position(1, 3);

        // when
        Direction direction = slidingDirectionFinder.find(departure, destination);

        // then
        assertThat(direction).isEqualTo(Direction.DOWN);
    }

    @Test
    void 같은_열에서_도착지가_위이면_UP을_반환한다() {
        // given
        Position departure = new Position(3, 3);
        Position destination = new Position(5, 3);

        // when
        Direction direction = slidingDirectionFinder.find(departure, destination);

        // then
        assertThat(direction).isEqualTo(Direction.UP);
    }
}
