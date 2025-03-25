package domain.board.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.BoardPosition;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NormalMovementTest {

    @Nested
    class ValidCases {

        @DisplayName("일반 영역에서 보드 라인을 따라 이동한 이동인지 확인한다.")
        @ParameterizedTest
        @MethodSource("provideNormalMovementsWithMoveOnLineFlag")
        void isMoveOnLine(NormalMovement normalMovement, boolean moveOnLineFlag) {
            // when & then
            assertThat(normalMovement.isMoveOnLine()).isEqualTo(moveOnLineFlag);
        }

        static Stream<Arguments> provideNormalMovementsWithMoveOnLineFlag() {
            return Stream.of(
                    Arguments.of(new NormalMovement(new BoardPosition(5, 5), new BoardPosition(5, 6)), true),
                    Arguments.of(new NormalMovement(new BoardPosition(5, 5), new BoardPosition(6, 6)), false),
                    Arguments.of(new NormalMovement(new BoardPosition(5, 5), new BoardPosition(7, 6)), false)
            );
        }

        @DisplayName("일반 영역에서 보드 라인을 따라 이동한 하나의 이동인지 확인한다.")
        @ParameterizedTest
        @MethodSource("provideNormalMovementsWithOneLineMovementFlag")
        void isOneLineMovement(NormalMovement normalMovement, boolean oneLineMovementFlag) {
            // when & then
            assertThat(normalMovement.isOneLineMovement()).isEqualTo(oneLineMovementFlag);
        }

        static Stream<Arguments> provideNormalMovementsWithOneLineMovementFlag() {
            return Stream.of(
                    Arguments.of(new NormalMovement(new BoardPosition(5, 5), new BoardPosition(5, 6)), true),
                    Arguments.of(new NormalMovement(new BoardPosition(5, 5), new BoardPosition(5, 7)), false),
                    Arguments.of(new NormalMovement(new BoardPosition(5, 5), new BoardPosition(4, 5)), true)
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("일반 영역이 아닌 움직임이라면 예외가 발생힌다.")
        @Test
        void validateIsNotPlaceMovement() {
            assertThatThrownBy(() -> new NormalMovement(
                    new BoardPosition(3, 0), new BoardPosition(4, 1)))
                    .isInstanceOf(IllegalCallerException.class)
                    .hasMessage("해당 이동은 일반 이동이 아닙니다.");
        }
    }
}
