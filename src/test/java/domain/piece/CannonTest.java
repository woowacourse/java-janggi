package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.BoardPosition;
import domain.board.Movement;
import domain.board.Offset;
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

        @DisplayName("포는 직선 경로로 이동할 수 있다.")
        @Test
        void findMovementRule_outPalace() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            BoardPosition from = new BoardPosition(0, 0);
            BoardPosition to = new BoardPosition(0, 4);
            Movement movement = new Movement(from, to);

            // when
            List<Offset> result = cannon.findMovementRule(movement);

            // then
            assertThat(result).isEqualTo(List.of(
                new Offset(0, 1),
                new Offset(0, 1),
                new Offset(0, 1),
                new Offset(0, 1)
            ));
        }

        @DisplayName("포는 궁성 내부에서 대각선으로 이동할 수 있다.")
        @Test
        void findMovementRule_inPalace() {
            // given
            Cannon cannon = new Cannon(Team.GREEN);
            BoardPosition from = BoardPosition.GREEN_PALACE_SOUTH_WEST;
            BoardPosition to = BoardPosition.GREEN_PALACE_NORTH_EAST;
            Movement movement = new Movement(from, to);

            // when
            List<Offset> result = cannon.findMovementRule(movement);

            // then
            assertThat(result).isEqualTo(List.of(
                new Offset(1, 1),
                new Offset(1, 1)
            ));
        }

        @DisplayName("포는 장애물을 하나 넘어 이동할 수 있다.")
        @Test
        void validateMovementConditions() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            List<Piece> obstacles = List.of(new Jju(Team.GREEN));
            Piece destination = new Jju(Team.GREEN);

            // when & then
            assertThatCode(() -> cannon.validateMovementConditions(obstacles, destination))
                .doesNotThrowAnyException();
        }

        @DisplayName("기물의 타입이 같은지 여부를 반환한다.")
        @ParameterizedTest
        @MethodSource("providePieceTypeMatchCases")
        void isSamePieceType(
            PieceType comparedType,
            boolean expected
        ) {
            // given
            Cannon cannon = new Cannon(Team.RED);

            // when
            boolean result = cannon.isSamePieceType(comparedType);

            // then
            assertThat(result).isEqualTo(expected);
        }

        static Stream<Arguments> providePieceTypeMatchCases() {
            // given
            return Stream.of(
                Arguments.of(PieceType.CANNON, true),
                Arguments.of(PieceType.JJU, false),
                Arguments.of(PieceType.GENERAL, false)
            );
        }

        @DisplayName("팀이 같거나 다른지를 반환한다.")
        @ParameterizedTest
        @MethodSource("provideTeamMatchCases")
        void isSameTeam(
            Team cannonTeam,
            Team comparedTeam,
            boolean expected
        ) {
            // given
            Cannon cannon = new Cannon(cannonTeam);

            // when
            boolean result = cannon.isSameTeam(comparedTeam);

            // then
            assertThat(result).isEqualTo(expected);
        }

        static Stream<Arguments> provideTeamMatchCases() {
            // given
            return Stream.of(
                Arguments.of(Team.RED, Team.RED, true),
                Arguments.of(Team.RED, Team.GREEN, false),
                Arguments.of(Team.GREEN, Team.GREEN, true),
                Arguments.of(Team.GREEN, Team.RED, false)
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("포는 팀을 반드시 가져야 한다.")
        @Test
        void validateNotNull() {
            // given
            Team nullTeam = null;

            // when & then
            assertThatThrownBy(() -> new Cannon(nullTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물은 타입과 팀을 반드시 가져야 합니다.");
        }

        @DisplayName("포는 직선이 아니면 이동할 수 없다.")
        @Test
        void findMovementRule() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            BoardPosition from = new BoardPosition(0, 0);
            BoardPosition to = new BoardPosition(2, 2);
            Movement movement = new Movement(from, to);

            // when & then
            assertThatThrownBy(() -> cannon.findMovementRule(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 이동할 수 없습니다.");
        }

        @DisplayName("포는 장애물이 하나가 아니라면 넘을 수 없다.")
        @Test
        void validateMovementConditions_manyObstacles() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            List<Piece> obstacles = List.of(new Jju(Team.RED), new Jju(Team.GREEN));

            // when & then
            assertThatThrownBy(
                () -> cannon.validateMovementConditions(obstacles, new Jju(Team.GREEN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 장애물을 정확히 하나 넘어야 합니다.");
        }

        @DisplayName("포는 포를 넘을 수 없다.")
        @Test
        void validateMovementConditions_overCannon() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            List<Piece> obstacles = List.of(new Cannon(Team.GREEN));

            // when & then
            assertThatThrownBy(
                () -> cannon.validateMovementConditions(obstacles, new Jju(Team.GREEN)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
        }

        @DisplayName("포는 포를 잡을 수 없다.")
        @Test
        void validateMovementConditions_captureCannon() {
            // given
            Cannon cannon = new Cannon(Team.RED);
            List<Piece> obstacles = List.of(new Jju(Team.GREEN));
            Piece destination = new Cannon(Team.GREEN);

            // when & then
            assertThatThrownBy(() -> cannon.validateMovementConditions(obstacles, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
        }
    }
}
