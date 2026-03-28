package janggi.domain.rule.route;

import janggi.domain.Location;
import janggi.domain.piece.PieceType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RouteProviderTest {

    @Test
    @DisplayName("시작 위치에서 도착 위치까지 도달 가능하다면, 그 사이 경로 좌표를 반환한다.")
    void shouldReturnLocationsWhenValidPathIsFound() {
        // given
        Location from = new Location(1,1);
        Location to = new Location(1,5);
        List<Route> possibleRoutes = List.of(Route.of(Direction.FRONT, 4));

        // when
        List<Location> locationsOfValidPath = RouteProvider.findValidPath(PieceType.CHA, from, to, possibleRoutes);

        // then
        Assertions.assertThat(locationsOfValidPath).containsExactly(
                new Location(1,2),
                new Location(1,3),
                new Location(1,4),
                new Location(1,5)
        );
    }

    @Test
    @DisplayName("시작 위치에서 도착 위치까지 도달 불가능한 경우 예외를 발생시킨다.")
    void shouldThrowExceptionWhenValidPathIsNotFound() {
        // given
        Location from = new Location(1,1);
        Location to = new Location(5,1);
        List<Route> possibleRoutes = List.of(Route.of(Direction.FRONT, 4));

        // when & then
        Assertions.assertThatThrownBy(() -> RouteProvider.findValidPath(PieceType.CHA, from, to, possibleRoutes))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
