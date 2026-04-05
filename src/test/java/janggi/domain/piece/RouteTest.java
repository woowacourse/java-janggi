package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.Route;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RouteTest {

    @Test
    void 시작과_종료_사이_칸이_없는_경우_모든_조건은_항상_참으로_리턴한다() {
        Route route = new Route(List.of(new Position(1, 1), new Position(1, 2)));
        assertThat(route.isEveryBetween(position -> false)).isTrue();
    }

    @Test
    void 중간_경로의_모든_좌표가_조건을_만족하면_isEveryBetween은_참으로_리턴한다() {
        Route route = new Route(List.of(
                new Position(1, 1),
                new Position(1, 2), // 중간 1
                new Position(1, 3), // 중간 2
                new Position(1, 4), // 중간 3
                new Position(1, 5)
        ));

        assertThat(route.isEveryBetween(position -> position.isRange(1, 1, 1, 9))).isTrue();
    }

    @Test
    void 중간_경로_중_하나라도_조건을_만족하지_않으면_isEveryBetween은_거짓으로_리턴한다() {
        Route route = new Route(List.of(
                new Position(1, 1),
                new Position(1, 2),
                new Position(2, 3), // 1행이 아님
                new Position(1, 4),
                new Position(1, 5)
        ));

        assertThat(route.isEveryBetween(position -> position.isRange(1, 1, 1, 9))).isFalse();
    }

    @Test
    void 중간_경로_중_하나라도_조건을_만족하면_isAnyBetween은_참으로_리턴한다() {
        Route route = new Route(List.of(
                new Position(1, 1),
                new Position(2, 2),
                new Position(1, 3), // 1행 좌표 존재
                new Position(4, 4),
                new Position(5, 5)
        ));

        assertThat(route.isAnyBetween(position -> position.isRange(1, 1, 1, 9))).isTrue();
    }

    @Test
    void 중간_경로_중_모든_조건을_만족하지_않으면_isAnyBetween은_거짓으로_리턴한다() {
        Route route = new Route(List.of(
                new Position(1, 1),
                new Position(2, 2),
                new Position(3, 3),
                new Position(4, 4),
                new Position(5, 5)
        ));

        assertThat(route.isAnyBetween(position -> position.isRange(10, 10, 1, 9))).isFalse();
    }

    @Test
    void 중간_경로에서_조건을_만족하는_좌표의_개수를_가져온다() {
        Route route = new Route(List.of(
                new Position(1, 1),
                new Position(2, 2), // 만족 1
                new Position(2, 3), // 만족 2
                new Position(1, 4),
                new Position(3, 5)
        ));

        assertThat(route.countBetween(position -> position.isRange(2, 2, 1, 9))).isEqualTo(2);
    }

    @Test
    void 마지막_위치에_대한_참_거짓으로_리턴한다() {
        Route route = new Route(List.of(
                new Position(1, 1),
                new Position(5, 5)
        ));

        assertThat(route.isArrivalPoint(new Position(5, 5))).isTrue();
        assertThat(route.isArrivalPoint(new Position(1, 1))).isFalse();
    }

    @Test
    void 마지막_위치가_특정_조건에_대한_참_거짓으로_리턴한다() {
        Route route = new Route(List.of(
                new Position(1, 1),
                new Position(5, 5)
        ));

        assertThat(route.isDestinationSatisfied(position -> position.isRange(5, 5, 5, 5))).isTrue();
        assertThat(route.isDestinationSatisfied(position -> position.isRange(1, 1, 1, 9))).isFalse();
    }
}