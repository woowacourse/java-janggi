package domain;

import domain.unit.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {

    @Test
    @DisplayName("졸의 이동경로를 구한다.")
    void test1() {
        // given
        Janggi janggi = new Janggi();

        // when
        List<Route> routes = janggi.searchAvailableRoutes(new Point(0, 3));

        // then
        Assertions.assertThat(routes).hasSize(3);
    }
}
