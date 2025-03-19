package domain.piece;

import domain.Point;
import domain.Team;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {
    @Test
    @DisplayName("도착할 수 있는지 확인한다.")
    void test_IsAbleToArrive() {
        // given
        Chariot chariot = new Chariot(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(0, 9);

        // when
        boolean actual = chariot.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("도착할 수 없는지 확인한다.")
    void test_IsNotAbleToArrive() {
        // given
        Chariot chariot = new Chariot(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(3, 3);

        // when
        boolean actual = chariot.isAbleToArrive(startPoint, arrivalPoint);

        // then
        assertThat(actual).isFalse();
    }


    @Test
    @DisplayName("도착 위치까지의 경로를 모두 반환한다.")
    void test_getRoutePoints() {
        // given
        Chariot chariot = new Chariot(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(0, 3);

        // when
        List<Point> routePoints = chariot.getRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );
    }
}
