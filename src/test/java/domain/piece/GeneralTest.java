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

class GeneralTest {

    @Nested
    class ValidCases {

        @DisplayName("왕이 일반 영역에서 이동할 때 이동 경로를 찾는다.")
        @Test
        void findMovementRuleInNormalMovement() {
            // given
            General general = new General(Team.RED);
            BoardPosition before = new BoardPosition(3, 1);
            BoardPosition after = new BoardPosition(3, 2);

            // when
            List<Offset> route = general.findMovementRule(before, after);

            // then
            assertThat(route).containsExactly(
                    new Offset(0, 1)
            );
        }

        @DisplayName("왕이 궁성 영역에서 이동할 때 이동 경로를 찾는다.")
        @Test
        void findMovementRuleInPalaceMovement() {
            // given
            General general = new General(Team.RED);
            BoardPosition before = new BoardPosition(5, 0);
            BoardPosition after = new BoardPosition(4, 1);

            // when
            List<Offset> route = general.findMovementRule(before, after);

            // then
            assertThat(route).containsExactly(
                    new Offset(-1, 1)
            );
        }

        @DisplayName("경로에 장애물이 있는지 확인한다.")
        @Test
        void isObstacleCountAllowed() {
            // given
            General general = new General(Team.RED);

            // when & then
            assertAll(
                    () -> assertThat(general.isAllowedObstacles(List.of(new Zzu(Team.RED)))).isFalse(),
                    () -> assertThat(general.isAllowedObstacles(List.of())).isTrue()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("왕을 왕의 이동규칙에 맞지 않게 이동하면 예외가 발생한다.")
        @ParameterizedTest
        @MethodSource("provideInvalidBeforeAndAfterPosition")
        void validateOffset(BoardPosition before, BoardPosition after) {
            // given
            General general = new General(Team.RED);

            // when & then
            assertThatThrownBy(() -> general.findMovementRule(before, after))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 말은 해당 위치로 이동할 수 없습니다.");
        }

        static Stream<Arguments> provideInvalidBeforeAndAfterPosition() {
            return Stream.of(
                    Arguments.of(new BoardPosition(0, 0), new BoardPosition(2, 2)),
                    Arguments.of(new BoardPosition(5, 5), new BoardPosition(3, 5)),
                    Arguments.of(new BoardPosition(3, 3), new BoardPosition(4, 4))
            );
        }

        @DisplayName("왕이 이동하는 위치가 궁성 밖이라면 예외를 발생시킨다.")
        @Test
        void validateMoveInPalaceArea() {
            // given
            General general = new General(Team.RED);
            BoardPosition before = new BoardPosition(5, 2);
            BoardPosition after = new BoardPosition(5, 3);

            // when & then
            assertThatThrownBy(() -> general.findMovementRule(before, after))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
