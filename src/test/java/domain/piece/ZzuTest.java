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

class ZzuTest {

    @Nested
    class ValidCases {

        @DisplayName("쭈의 이동 위치를 통해 이동 경로를 찾는다.")
        @Test
        void findMovementRule() {
            // given
            Zzu zzu = new Zzu(Team.GREEN);
            BoardPosition before = new BoardPosition(0, 0);
            BoardPosition after = new BoardPosition(1, 0);

            // when
            List<Offset> route = zzu.findMovementRule(before, after);

            // then
            assertThat(route).containsExactly(
                    new Offset(1, 0)
            );
        }

        @DisplayName("경로에 장애물이 있는지 확인한다.")
        @Test
        void isObstacleCountAllowed() {
            // given
            Zzu zzu = new Zzu(Team.GREEN);

            // when & then
            assertAll(
                    () -> assertThat(zzu.isAllowedObstacles(List.of(new Zzu(Team.RED)))).isFalse(),
                    () -> assertThat(zzu.isAllowedObstacles(List.of())).isTrue()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("쭈를 한 칸 이상 움직이면 예외가 발생한다.")
        @ParameterizedTest
        @MethodSource("provideOverOneMovementPosition")
        void hasOneMovement(BoardPosition before, BoardPosition after) {
            // given
            Zzu zzu = new Zzu(Team.GREEN);

            // when & then
            assertThatThrownBy(() -> zzu.findMovementRule(before, after))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 말은 해당 위치로 이동할 수 없습니다.");
        }

        static Stream<Arguments> provideOverOneMovementPosition() {
            return Stream.of(
                    Arguments.of(new BoardPosition(0, 0), new BoardPosition(2, 0)),
                    Arguments.of(new BoardPosition(5, 5), new BoardPosition(3, 5)),
                    Arguments.of(new BoardPosition(3, 3), new BoardPosition(3, 6))
            );
        }

        @DisplayName("쭈를 팀의 진영 기준으로 뒤로 움직이면 예외가 발생한다.")
        @ParameterizedTest
        @MethodSource("provideBackwardMovementPosition")
        void hasOneMovement(
                Team team,
                BoardPosition before,
                BoardPosition after
        ) {
            // given
            Zzu zzu = new Zzu(team);

            // when & then
            assertThatThrownBy(() -> zzu.findMovementRule(before, after))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 말은 해당 위치로 이동할 수 없습니다.");
        }

        static Stream<Arguments> provideBackwardMovementPosition() {
            return Stream.of(
                    Arguments.of(Team.RED, new BoardPosition(0, 0), new BoardPosition(0, 1)),
                    Arguments.of(Team.GREEN, new BoardPosition(5, 5), new BoardPosition(5, 4))
            );
        }
    }
}
