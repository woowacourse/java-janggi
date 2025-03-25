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

class PalaceMovementTest {

    @Nested
    class ValidCases {

        @DisplayName("궁성 영역에서 보드 라인을 따라 이동한 이동인지 확인한다.")
        @ParameterizedTest
        @MethodSource("providePalaceMovementsWithMoveOnLineFlag")
        void isMoveOnLine(PalaceMovement palaceMovement, boolean moveOnLineFlag) {
            // when & then
            assertThat(palaceMovement.isMoveOnLine()).isEqualTo(moveOnLineFlag);
        }

        static Stream<Arguments> providePalaceMovementsWithMoveOnLineFlag() {
            return Stream.of(
                    Arguments.of(new PalaceMovement(new BoardPosition(3, 0), new BoardPosition(3, 2)), true),
                    Arguments.of(new PalaceMovement(new BoardPosition(3, 0), new BoardPosition(5, 2)), true),
                    Arguments.of(new PalaceMovement(new BoardPosition(3, 0), new BoardPosition(5, 9)), false),
                    Arguments.of(new PalaceMovement(new BoardPosition(4, 1), new BoardPosition(3, 7)), false)
            );
        }

        @DisplayName("궁성 영역에서 보드 라인을 따라 이동한 하나의 이동인지 확인한다.")
        @ParameterizedTest
        @MethodSource("providePalaceMovementsWithOneLineMovementFlag")
        void isOneLineMovement(PalaceMovement palaceMovement, boolean oneLineMovementFlag) {
            // when & then
            assertThat(palaceMovement.isOneLineMovement()).isEqualTo(oneLineMovementFlag);
        }

        static Stream<Arguments> providePalaceMovementsWithOneLineMovementFlag() {
            return Stream.of(
                    Arguments.of(new PalaceMovement(new BoardPosition(3, 9), new BoardPosition(4, 8)), true),
                    Arguments.of(new PalaceMovement(new BoardPosition(4, 1), new BoardPosition(3, 2)), true),
                    Arguments.of(new PalaceMovement(new BoardPosition(3, 0), new BoardPosition(5, 2)), false)
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("궁성 영역이 아닌 움직임이라면 예외가 발생힌다.")
        @Test
        void validateIsPlaceMovement() {
            assertThatThrownBy(() -> new PalaceMovement(
                    new BoardPosition(3, 0), new BoardPosition(3, 1)))
                    .isInstanceOf(IllegalCallerException.class)
                    .hasMessage("해당 이동은 궁성 이동이 아닙니다.");
        }
    }
}
