package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

public class ChariotTest {

    @Nested
    class ValidCases {

        @DisplayName("차의 이동 위치를 통해 이동 경로를 찾는다.")
        @Test
        void findMovementRule() {
            // given
            Chariot chariot = new Chariot(Team.RED);
            BoardPosition before = new BoardPosition(0 ,0);
            BoardPosition after = new BoardPosition(0 ,5);

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

        @DisplayName("차가 허용가능한 경로상 장애물 갯수인지 확인한다.")
        @Test
        void isObstacleCountAllowed() {
            // given
            Chariot chariot = new Chariot(Team.RED);

            // when & then
            assertAll(
                    () -> assertThat(chariot.isObstacleCountAllowed(3)).isFalse(),
                    () -> assertThat(chariot.isObstacleCountAllowed(0)).isTrue()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("차를 수직이나 수평으로 이동하지 않으면 예외가 발생한다.")
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
                    Arguments.of( new BoardPosition(0 ,0), new BoardPosition(5 ,3)),
                    Arguments.of( new BoardPosition(5 ,5), new BoardPosition(4 ,3)),
                    Arguments.of( new BoardPosition(3 ,2), new BoardPosition(5 ,0))
            );
        }
    }
}
