package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PalaceTest {

    private final Palace palace = Palace.getInstance();

    @Nested
    @DisplayName("궁성 내부 확인")
    class IsInPalace {

        @Test
        @DisplayName("초 궁성 중앙은 궁성 내부이다")
        void 초_궁성_중앙은_궁성_내부이다() {
            assertThat(palace.isInPalace(Position.of(5, 9))).isTrue();
        }

        @Test
        @DisplayName("한 궁성 중앙은 궁성 내부이다")
        void 한_궁성_중앙은_궁성_내부이다() {
            assertThat(palace.isInPalace(Position.of(5, 2))).isTrue();
        }

        @Test
        @DisplayName("초 궁성 모서리는 궁성 내부이다")
        void 초_궁성_모서리는_궁성_내부이다() {
            assertThat(palace.isInPalace(Position.of(4, 8))).isTrue();
            assertThat(palace.isInPalace(Position.of(6, 8))).isTrue();
            assertThat(palace.isInPalace(Position.of(4, 10))).isTrue();
            assertThat(palace.isInPalace(Position.of(6, 10))).isTrue();
        }

        @Test
        @DisplayName("한 궁성 모서리는 궁성 내부이다")
        void 한_궁성_모서리는_궁성_내부이다() {
            assertThat(palace.isInPalace(Position.of(4, 1))).isTrue();
            assertThat(palace.isInPalace(Position.of(6, 1))).isTrue();
            assertThat(palace.isInPalace(Position.of(4, 3))).isTrue();
            assertThat(palace.isInPalace(Position.of(6, 3))).isTrue();
        }

        @Test
        @DisplayName("궁성 외부는 궁성이 아니다")
        void 궁성_외부는_궁성이_아니다() {
            assertThat(palace.isInPalace(Position.of(1, 1))).isFalse();
            assertThat(palace.isInPalace(Position.of(5, 5))).isFalse();
            assertThat(palace.isInPalace(Position.of(3, 9))).isFalse();
        }
    }

    @Nested
    @DisplayName("방향 조회")
    class GetDirections {

        @Test
        @DisplayName("궁성 중앙에서는 8방향 모두 가능하다")
        void 궁성_중앙에서는_8방향_모두_가능하다() {
            Set<Direction> directions = palace.getDirections(Position.of(5, 2));

            assertThat(directions).containsExactlyInAnyOrder(
                    Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                    Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT
            );
        }

        @Test
        @DisplayName("궁성 좌상단 모서리에서는 우, 하, 우하 방향이 가능하다")
        void 궁성_좌상단_모서리에서는_우_하_우하_방향이_가능하다() {
            Set<Direction> directions = palace.getDirections(Position.of(4, 1));

            assertThat(directions).containsExactlyInAnyOrder(
                    Direction.RIGHT, Direction.DOWN, Direction.DOWN_RIGHT
            );
        }

        @Test
        @DisplayName("궁성 우하단 모서리에서는 상, 좌, 좌상 방향이 가능하다")
        void 궁성_우하단_모서리에서는_상_좌_좌상_방향이_가능하다() {
            Set<Direction> directions = palace.getDirections(Position.of(6, 3));

            assertThat(directions).containsExactlyInAnyOrder(
                    Direction.UP, Direction.LEFT, Direction.UP_LEFT
            );
        }

        @Test
        @DisplayName("궁성 외부에서는 빈 집합을 반환한다")
        void 궁성_외부에서는_빈_집합을_반환한다() {
            Set<Direction> directions = palace.getDirections(Position.of(1, 1));

            assertThat(directions).isEmpty();
        }
    }

    @Nested
    @DisplayName("대각선 경로 탐색")
    class FindDiagonalRoutes {

        @Test
        @DisplayName("궁성 중앙에서 대각선 경로를 찾는다")
        void 궁성_중앙에서_대각선_경로를_찾는다() {
            List<Position> routes = palace.findDiagonalRoutes(Position.of(5, 2));

            assertThat(routes).contains(
                    Position.of(4, 1),
                    Position.of(6, 1),
                    Position.of(4, 3),
                    Position.of(6, 3)
            );
        }

        @Test
        @DisplayName("궁성 모서리에서 대각선 경로를 찾는다")
        void 궁성_모서리에서_대각선_경로를_찾는다() {
            List<Position> routes = palace.findDiagonalRoutes(Position.of(4, 1));

            assertThat(routes).contains(Position.of(5, 2));
        }

        @Test
        @DisplayName("궁성 외부에서는 빈 경로를 반환한다")
        void 궁성_외부에서는_빈_경로를_반환한다() {
            List<Position> routes = palace.findDiagonalRoutes(Position.of(1, 1));

            assertThat(routes).isEmpty();
        }
    }

    @Nested
    @DisplayName("두 위치 사이 대각선 경로")
    class FindDiagonalPathTo {

        @Test
        @DisplayName("모서리에서 중앙까지의 경로는 빈 리스트이다")
        void 모서리에서_중앙까지의_경로는_빈_리스트이다() {
            List<Position> path = palace.findDiagonalPathTo(Position.of(4, 1), Position.of(5, 2));

            assertThat(path).isEmpty();
        }

        @Test
        @DisplayName("모서리에서 반대 모서리까지의 경로에는 중앙이 포함된다")
        void 모서리에서_반대_모서리까지의_경로에는_중앙이_포함된다() {
            List<Position> path = palace.findDiagonalPathTo(Position.of(4, 1), Position.of(6, 3));

            assertThat(path).containsExactly(Position.of(5, 2));
        }
    }
}
