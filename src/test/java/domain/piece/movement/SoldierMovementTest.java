package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("기물(졸) 행마법 테스트")
class SoldierMovementTest {

    private SoldierMovement movement;

    @BeforeEach
    void setUp() {
        movement = new SoldierMovement();
    }

    @DisplayName("앞과 양 옆으로 1칸 이동이 가능하다")
    @Nested
    class 앞과_양_옆으로_1칸_이동이_가능하다 {

        private static final Intersection CURRENT_INTERSECTION = new Intersection(5, 5);

        @DisplayName("초 진영의 경우")
        @Test
        void 초_진영의_경우() {
            List<Intersection> movableIntersections = movement.movableIntersections(CURRENT_INTERSECTION, Side.CHO);

            assertThat(movableIntersections)
                    .containsExactlyInAnyOrder(
                            new Intersection(4, 5),
                            new Intersection(5, 4),
                            new Intersection(5, 6)
                    );
        }

        @DisplayName("한 진영의 경우")
        @Test
        void 한_진영의_경우() {
            List<Intersection> movableIntersections = movement.movableIntersections(CURRENT_INTERSECTION, Side.HAN);

            assertThat(movableIntersections)
                    .containsExactlyInAnyOrder(
                            new Intersection(6, 5),
                            new Intersection(5, 4),
                            new Intersection(5, 6)
                    );
        }
    }

    @DisplayName("범위 밖으로는 이동이 불가능하다")
    @Nested
    class 범위_밖으로는_이동이_불가능하다 {

        @DisplayName("초 진영의 경우")
        @Test
        void 초_진영의_경우() {
            Intersection borderIntersection = new Intersection(1, 1);
            List<Intersection> movableIntersections = movement.movableIntersections(borderIntersection, Side.CHO);

            assertThat(movableIntersections)
                    .containsExactlyInAnyOrder(new Intersection(1, 2));
        }

        @DisplayName("한 진영의 경우")
        @Test
        void 한_진영의_경우() {
            Intersection borderIntersection = new Intersection(10, 1);
            List<Intersection> movableIntersections = movement.movableIntersections(borderIntersection, Side.HAN);

            assertThat(movableIntersections)
                    .containsExactlyInAnyOrder(new Intersection(10, 2));
        }
    }
}
