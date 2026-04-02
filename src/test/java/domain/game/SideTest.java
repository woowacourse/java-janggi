package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
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

        @DisplayName("방향 정보를 요청해도 NPE가 발생하지 않고 NoDirection을 반환한다")
        @Test
        void 방향_요청_시_NPE_미발생() {
            Side none = Side.NONE;

            assertThatNoException().isThrownBy(() -> {
                none.getForwardDirection();
                none.getBackwardDirection();
                none.getLeftDirection();
                none.getRightDirection();
            });
        }

        @DisplayName("방향 이동 메서드 호출 시 좌표가 변하지 않고 자기 자신을 반환한다")
        @Test
        void 이동_시_좌표_불변() {
            Side none = Side.NONE;
            Direction direction = none.getForwardDirection();
            Intersection currentIntersection = new Intersection(5, 5);
            MoveAmount amount = new MoveAmount(1);

            assertThat(direction.moveForward(currentIntersection, amount)).isEqualTo(currentIntersection);
            assertThat(direction.reverse()).isEqualTo(direction);
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
