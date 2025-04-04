package janggi.position;

import janggi.board.Pieces;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RouteTest {
    @Test
    @DisplayName("빈 경로의 루트는 생성할 수 없다")
    void test1() {
        // given
        Assertions.assertThatThrownBy(() -> Route.of(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 경로는 존재할 수 없습니다.");
    }

    @Test
    @DisplayName("경로에 포 기물이 있다면 포가 지나갈 수 없다.")
    void test2() {
        // given
        Route route = Route.of(List.of(new Position(1, 2)));
        Pieces pieces = new Pieces();

        // when
        boolean canCannonJump = route.canCannonJump(pieces);

        // then
        Assertions.assertThat(canCannonJump).isFalse();
    }

    @Test
    @DisplayName("도착지를 제외한 경로에 포 외의 다른 기물이 하나 있어야 포가 지나갈 수 있다.")
    void test3() {
        // given
        Route route = Route.of(List.of(new Position(0, 4), new Position(0, 3), new Position(0, 2)));
        Pieces pieces = new Pieces();

        // when
        boolean canCannonJump = route.canCannonJump(pieces);

        // then
        Assertions.assertThat(canCannonJump).isTrue();
    }

    @Test
    @DisplayName("거리를 이용해서 도착지점을 계산한다")
    void test4() {
        // given
        Position start = new Position(0, 4);
        Position end = new Position(0, 2);
        Route route = Route.of(List.of(start, new Position(0, 3), end));

        // when
        Position position = route.searchEndPoint(start);

        // then
        Assertions.assertThat(position).isEqualTo(end);
    }
}
