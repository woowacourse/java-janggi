package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Test
    @DisplayName("plus()는 두 Movement를 더한 결과를 반환한다")
    void plus() {
        Movement result = new Movement(1, 2).plus(new Movement(3, -1));
        assertThat(result).isEqualTo(new Movement(4, 1));
    }

    @Nested
    @DisplayName("findUnitMovement()는")
    class FindUnitMovementTest {

        @Test
        @DisplayName("대각선 방향을 올바르게 반환한다")
        void diagonalDirection() {
            assertThat(Movement.findUnitMovement(3, 3)).isEqualTo(Movement.DOWN_RIGHT);
            assertThat(Movement.findUnitMovement(-2, 2)).isEqualTo(Movement.UP_RIGHT);
            assertThat(Movement.findUnitMovement(4, -5)).isEqualTo(Movement.DOWN_LEFT);
            assertThat(Movement.findUnitMovement(-3, -3)).isEqualTo(Movement.UP_LEFT);
        }

        @Test
        @DisplayName("직선 방향을 올바르게 반환한다")
        void straightDirection() {
            assertThat(Movement.findUnitMovement(0, 4)).isEqualTo(Movement.RIGHT);
            assertThat(Movement.findUnitMovement(0, -1)).isEqualTo(Movement.LEFT);
            assertThat(Movement.findUnitMovement(-3, 0)).isEqualTo(Movement.UP);
            assertThat(Movement.findUnitMovement(2, 0)).isEqualTo(Movement.DOWN);
        }

        @Test
        @DisplayName("잘못된 방향일 경우 예외를 던진다")
        void invalidDirection() {
            assertThatThrownBy(() -> Movement.findUnitMovement(0, 0))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("isUnitMovement()는")
    class IsUnitMovementTest {

        @Test
        @DisplayName("정의된 단위 이동이면 true를 반환한다")
        void isValidUnit() {
            assertThat(Movement.isUnitMovement(Movement.UP)).isTrue();
            assertThat(Movement.isUnitMovement(Movement.DOWN_RIGHT)).isTrue();
        }

        @Test
        @DisplayName("정의되지 않은 이동이면 false를 반환한다")
        void isNotValidUnit() {
            assertThat(Movement.isUnitMovement(new Movement(2, 0))).isFalse();
            assertThat(Movement.isUnitMovement(new Movement(1, 2))).isFalse();
        }
    }
}