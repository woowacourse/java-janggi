package domain.board.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.board.BoardPosition;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MovementTest {

    @Nested
    class ValidCases {

        @DisplayName("궁성이동 적용 영역 밖에서의 움직임이라면 일반 움직임 객체를 생성한다.")
        @ParameterizedTest
        @MethodSource("provideNormalMovementPositions")
        void createNormalMovement(
                BoardPosition before,
                BoardPosition after
        ) {
            // when
            Movement movement = Movement.of(before, after);

            // then
            assertThat(movement).isExactlyInstanceOf(NormalMovement.class);
        }

        static Stream<Arguments> provideNormalMovementPositions() {
            return Stream.of(
                    Arguments.of(new BoardPosition(5, 5), new BoardPosition(5, 6)),
                    Arguments.of(new BoardPosition(4, 8), new BoardPosition(5, 8)),
                    Arguments.of(new BoardPosition(3, 0), new BoardPosition(3, 1))
            );
        }

        @DisplayName("궁성이동 적용 영역 내부에서의 움직임이라면 궁성 움직임 객체를 생성한다.")
        @ParameterizedTest
        @MethodSource("providePalaceMovementPositions")
        void createPalaceMovement(
                final BoardPosition before,
                final BoardPosition after
        ) {
            // when
            Movement movement = Movement.of(before, after);

            // then
            assertThat(movement).isExactlyInstanceOf(PalaceMovement.class);
        }

        static Stream<Arguments> providePalaceMovementPositions() {
            return Stream.of(
                    Arguments.of(new BoardPosition(3, 0), new BoardPosition(4, 1)),
                    Arguments.of(new BoardPosition(4, 1), new BoardPosition(5, 0)),
                    Arguments.of(new BoardPosition(3, 9), new BoardPosition(4, 8))
            );
        }

        @DisplayName("궁성 내부 이동인지 확인한다.")
        @Test
        void isMoveInPalaceArea() {
            // given
            Movement inPalaceMovement = Movement.of(new BoardPosition(3, 1), new BoardPosition(3, 2));
            Movement notInPalaceMovement = Movement.of(new BoardPosition(3, 2), new BoardPosition(3, 3));

            // when & then
            assertAll(
                    () -> assertThat(inPalaceMovement.isMoveInPalaceArea()).isTrue(),
                    () -> assertThat(notInPalaceMovement.isMoveInPalaceArea()).isFalse()
            );
        }
    }
}
