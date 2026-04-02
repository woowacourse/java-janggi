package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Side 테스트")
class SideTest {

    @DisplayName("NONE 진영(널 객체) 테스트")
    @Nested
    class NoneSideTest {

        @DisplayName("다음 턴을 요청해도 여전히 NONE이다")
        @Test
        void 다음_턴도_NONE이다() {
            Side none = Side.NONE;
            assertThat(none.nextTurn()).isEqualTo(Side.NONE);
        }

        @DisplayName("이동을 요청하면 이동하지 않는다(제자리 이동)")
        @Test
        void 이동_요청_시_제자리() {
            Side none = Side.NONE;
            Intersection startIntersection = new Intersection(5, 5);
            MoveAmount moveAmount = new MoveAmount(1);

            List<Intersection> movedIntersections = List.of(
                    none.moveForward(startIntersection, moveAmount),
                    none.moveLeft(startIntersection, moveAmount),
                    none.moveRight(startIntersection, moveAmount),
                    none.moveBackward(startIntersection, moveAmount),
                    none.moveForwardLeft(startIntersection, moveAmount),
                    none.moveForwardRight(startIntersection, moveAmount)
            );

            for (Intersection movedIntersection : movedIntersections) {
                assertThat(movedIntersection).isEqualTo(startIntersection);
            }
        }

        @DisplayName("기준 행/열 계산 시 NPE가 발생하지 않는다")
        @Test
        void 기준_좌표_계산_NPE_미발생() {
            Side none = Side.NONE;
            MoveAmount amount = new MoveAmount(0);

            assertThatNoException().isThrownBy(() -> {
                none.getRowAt(amount);
                none.getFileAt(amount);
            });
        }
    }
}
