package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("기물(마) 행마법 테스트")
class HorseMovementTest {

    private PieceMovement movement;

    @BeforeEach
    void setUp() {
        movement = new HorseMovement();
    }

    @DisplayName("앞, 뒤, 양 옆 방향마다 두 갈래의 날일 방향으로 이동이 가능하다")
    @Nested
    class 모든_방향마다_두_갈래의_날일_방향으로_이동이_가능하다 {

        @DisplayName("진영에 상관없이 동일하게 적용된다")
        @ParameterizedTest
        @EnumSource(Side.class)
        void 진영에_상관없이_동일하게_적용된다(Side side) {
            Intersection currentIntersection = new Intersection(5, 5);

            List<Path> movablePaths = movement.movablePaths(currentIntersection, side);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrder(
                            new Intersection(3, 4),
                            new Intersection(3, 6),
                            new Intersection(7, 4),
                            new Intersection(7, 6),
                            new Intersection(4, 3),
                            new Intersection(6, 3),
                            new Intersection(4, 7),
                            new Intersection(6, 7)
                    );
        }
    }

    @DisplayName("범위 밖으로는 이동이 불가능하다")
    @Nested
    class 범위_밖으로는_이동이_불가능하다 {

        @DisplayName("진영에 상관없이 동일하게 적용된다")
        @ParameterizedTest
        @EnumSource(Side.class)
        void 진영에_상관없이_동일하게_적용된다(Side side) {
            Intersection borderIntersection = new Intersection(1, 1);

            List<Path> movablePaths = movement.movablePaths(borderIntersection, side);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrder(
                            new Intersection(2, 3),
                            new Intersection(3, 2)
                    );
        }
    }
}
