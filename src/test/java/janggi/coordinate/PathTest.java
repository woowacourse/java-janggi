package janggi.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.coordinate.Path;
import janggi.coordinate.Position;
import janggi.coordinate.RelativePosition;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PathTest {

    @Nested
    @DisplayName("절대 경로 계산")
    class CalculateAbsolutePath {

        @DisplayName("주어진 위치에 대해서 절대 경로를 계산해서 반환한다.")
        @Test
        void calculateAbsolutePath() {
            // given
            final List<RelativePosition> relativePositions = List.of(
                    RelativePosition.BOTTOM, RelativePosition.BOTTOM_LEFT_DIAGONAL
            );
            final Path path = new Path(relativePositions);
            final Position now = new Position(3, 3);

            // when
            final List<Position> positions = path.calculateAbsolutePath(now);

            // then
            assertThat(positions)
                    .contains(new Position(4, 3))
                    .contains(new Position(5, 2));
        }

        @DisplayName("주어진 위치에 대한 절대 경로의 목적지가, 주어진 목적지라면 true, 아니라면 false를 반환한다.")
        @Test
        void equalsDestination() {
            // given
            final List<RelativePosition> relativePositions = List.of(
                    RelativePosition.BOTTOM, RelativePosition.BOTTOM_LEFT_DIAGONAL
            );
            final Path path = new Path(relativePositions);
            final Position now = new Position(3, 3);
            final Position destination = new Position(5, 2);

            // when
            final boolean actual = path.equalsDestination(now, destination);

            // then
            assertThat(actual).isTrue();
        }

    }
}
