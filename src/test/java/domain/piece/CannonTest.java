package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import domain.BoardPosition;
import domain.Offset;
import domain.Team;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {

    @Nested
    class ValidCases {

        @DisplayName("포의 이동 위치를 통해 이동 경로를 찾는다.")
        @Test
        void findMovementRule() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            BoardPosition before = new BoardPosition(0 ,0);
            BoardPosition after = new BoardPosition(0 ,5);

            // when
            List<Offset> route = cannon.findMovementRule(before, after);

            // then
            assertThat(route).containsExactlyInAnyOrder(
                    new Offset(0, 1),
                    new Offset(0, 1),
                    new Offset(0, 1),
                    new Offset(0, 1),
                    new Offset(0, 1)
            );
        }

        @DisplayName("포가 허용가능한 경로상 장애물 갯수인지 확인한다.")
        @Test
        void isObstacleCountAllowed() {
            // given
            Cannon cannon = new Cannon(Team.RED);

            // when & then
            assertAll(
                    () -> assertThat(cannon.isObstacleCountAllowed(0)).isFalse(),
                    () -> assertThat(cannon.isObstacleCountAllowed(3)).isFalse(),
                    () -> assertThat(cannon.isObstacleCountAllowed(1)).isTrue()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("포를 같은 위치로 이동시키면 예외가 발생한다.")
        @Test
        void validateNotMove() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            BoardPosition before = new BoardPosition(0 ,0);
            BoardPosition after = new BoardPosition(0 ,0);

            // when & then
            assertThatThrownBy(() -> cannon.findMovementRule(before, after))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("기물을 같은 위치로 이동시킬 수 없습니다.");
        }

        @DisplayName("포를 수직이나 수평으로 이동하지 않으면 예외가 발생한다.")
        @ParameterizedTest
        @MethodSource("provideInvalidBeforeAndAfterPosition")
        void validateOffset(BoardPosition before, BoardPosition after) {
            // given
            Cannon cannon = new Cannon(Team.RED);

            // when & then
            assertThatThrownBy(() -> cannon.findMovementRule(before, after))
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
