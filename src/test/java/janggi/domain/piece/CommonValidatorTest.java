package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CommonValidatorTest {

    @Nested
    @DisplayName("validateStraightMovement()는")
    class ValidateStraightMovementTest {

        @Test
        @DisplayName("상하좌우 이동일 경우 예외를 던지지 않는다")
        void validStraight() {
            CommonValidator.validateStraightMovement(
                    new Position(2, 2), new Position(2, 5));
            CommonValidator.validateStraightMovement(
                    new Position(4, 3), new Position(1, 3));
        }

        @Test
        @DisplayName("대각선 이동이면 예외를 던진다")
        void invalidStraight() {
            assertThatThrownBy(() ->
                    CommonValidator.validateStraightMovement(
                            new Position(2, 2), new Position(3, 3)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("상하좌우");
        }
    }

    @Nested
    @DisplayName("validateLinearMovement()는")
    class ValidateLinearMovementTest {

        @Test
        @DisplayName("상하좌우 또는 완전한 대각선이면 예외를 던지지 않는다")
        void validLinear() {
            CommonValidator.validateLinearMovement(
                    new Position(1, 1), new Position(1, 5));
            CommonValidator.validateLinearMovement(
                    new Position(2, 2), new Position(5, 5));
        }

        @Test
        @DisplayName("직선이 아니면 예외를 던진다")
        void invalidLinear() {
            assertThatThrownBy(() ->
                    CommonValidator.validateLinearMovement(
                            new Position(2, 2), new Position(3, 5)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("직선");
        }
    }

    @Nested
    @DisplayName("validateSingleStepMovement()는")
    class ValidateSingleStepMovementTest {

        @Test
        @DisplayName("한 칸 이동이면 예외를 던지지 않는다")
        void validStep() {
            CommonValidator.validateSingleStepMovement(
                    new Position(3, 3), new Position(3, 4));
            CommonValidator.validateSingleStepMovement(
                    new Position(5, 5), new Position(6, 6));
        }

        @Test
        @DisplayName("두 칸 이상 이동이면 예외를 던진다")
        void invalidStep() {
            assertThatThrownBy(() ->
                    CommonValidator.validateSingleStepMovement(
                            new Position(2, 2), new Position(4, 2)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1칸만");
        }
    }
}