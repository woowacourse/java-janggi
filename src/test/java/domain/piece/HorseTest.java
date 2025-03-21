package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.Team;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {

    @Nested
    class ValidCases {

        @DisplayName("마의 이동 위치를 통해 이동 경로를 찾는다.")
        @Test
        void findMovementRule() {
            // given
            Horse horse = new Horse(Team.RED);
            BoardPosition before = new BoardPosition(0 ,0);
            BoardPosition after = new BoardPosition(1 ,2);

            // when
            List<Offset> route = horse.findMovementRule(before, after);

            // then
            assertThat(route).containsExactly(
                    new Offset(0, 1),
                    new Offset(1, 1)
            );
        }

        @DisplayName("경로에 장애물이 있는지 확인한다..")
        @Test
        void isObstacleCountAllowed() {
            // given
            Horse horse = new Horse(Team.RED);

            // when & then
            assertAll(
                    () -> assertThat(horse.isAllowedObstacles(List.of(new Zzu(Team.RED)))).isFalse(),
                    () -> assertThat(horse.isAllowedObstacles(List.of())).isTrue()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("마를 마의 이동규칙에 맞지 않게 이동하면 예외가 발생한다.")
        @ParameterizedTest
        @MethodSource("provideInvalidBeforeAndAfterPosition")
        void validateOffset(BoardPosition before, BoardPosition after) {
            // given
            Horse horse = new Horse(Team.RED);

            // when & then
            assertThatThrownBy(() -> horse.findMovementRule(before, after))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 말은 해당 위치로 이동할 수 없습니다.");
        }

        static Stream<Arguments> provideInvalidBeforeAndAfterPosition() {
            return Stream.of(
                    Arguments.of( new BoardPosition(0 ,0), new BoardPosition(2 ,2)),
                    Arguments.of( new BoardPosition(5 ,5), new BoardPosition(4 ,5)),
                    Arguments.of( new BoardPosition(3 ,3), new BoardPosition(5 ,0))
            );
        }
    }
}
