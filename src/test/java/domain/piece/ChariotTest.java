package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.janggi.Team;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ChariotTest {

    @Nested
    class ValidCases {

        @DisplayName("차가 일반 영역에서 이동할 때 이동 경로를 찾는다.")
        @Test
        void findMovementRuleInNormalMovement() {
            // given
            Chariot chariot = new Chariot(Team.RED);
            BoardPosition before = new BoardPosition(0, 0);
            BoardPosition after = new BoardPosition(0, 5);

            // when
            List<Offset> route = chariot.findMovementRule(before, after);

            // then
            assertThat(route).containsExactlyInAnyOrder(
                    new Offset(0, 1),
                    new Offset(0, 1),
                    new Offset(0, 1),
                    new Offset(0, 1),
                    new Offset(0, 1)
            );
        }

        @DisplayName("차가 궁성 영역에서 이동할 때 이동 경로를 찾는다.")
        @Test
        void findMovementRuleInPalaceMovement() {
            // given
            Chariot chariot = new Chariot(Team.RED);
            BoardPosition before = new BoardPosition(5, 0);
            BoardPosition after = new BoardPosition(3, 2);

            // when
            List<Offset> route = chariot.findMovementRule(before, after);

            // then
            assertThat(route).containsExactlyInAnyOrder(
                    new Offset(-1, 1),
                    new Offset(-1, 1)
            );
        }

        @DisplayName("경로에 장애물이 있는지 확인한다.")
        @Test
        void isObstacleCountAllowed() {
            // given
            Chariot chariot = new Chariot(Team.RED);

            // when & then
            assertAll(
                    () -> assertThat(chariot.isAllowedObstacles(List.of(new Zzu(Team.RED)))).isFalse(),
                    () -> assertThat(chariot.isAllowedObstacles(List.of())).isTrue()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("차를 보드판의 선을 따라 이동한 것이 아니라면 예외를 발생시킨다.")
        @ParameterizedTest
        @MethodSource("provideInvalidBeforeAndAfterPosition")
        void validateOffset(BoardPosition before, BoardPosition after) {
            // given
            Chariot chariot = new Chariot(Team.RED);

            // when & then
            assertThatThrownBy(() -> chariot.findMovementRule(before, after))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 말은 해당 위치로 이동할 수 없습니다.");
        }

        static Stream<Arguments> provideInvalidBeforeAndAfterPosition() {
            return Stream.of(
                    Arguments.of(new BoardPosition(0, 0), new BoardPosition(5, 3)),
                    Arguments.of(new BoardPosition(5, 5), new BoardPosition(4, 3)),
                    Arguments.of(new BoardPosition(3, 2), new BoardPosition(5, 1))
            );
        }
    }
}
