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

class GeneralTest {

    @Nested
    class ValidCases {

        @DisplayName("왕은 궁성 내부에서 선을 타고 이동할 수 있다.")
        @Test
        void findMovementRule() {
            // given
            General general = new General(Team.RED);
            BoardPosition from = BoardPosition.RED_PALACE_MIDDLE_CENTER;
            BoardPosition to = BoardPosition.RED_PALACE_NORTH_CENTER;
            Movement movement = new Movement(from, to);

            // when
            List<Offset> result = general.findMovementRule(movement);

            // then
            assertThat(result).isEqualTo(List.of(new Offset(0, 1)));
        }

        @DisplayName("왕은 장애물이 없으면 이동할 수 있다.")
        @Test
        void validateMovementConditions() {
            // given
            General general = new General(Team.RED);
            List<Piece> obstacles = List.of();
            Piece destination = new Jju(Team.GREEN);

            // when & then
            assertThatCode(() -> general.validateMovementConditions(obstacles, destination))
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
            General general = new General(Team.RED);

            // when
            boolean result = general.isSamePieceType(comparedType);

            // then
            assertThat(result).isEqualTo(expected);
        }

        static Stream<Arguments> providePieceTypeMatchCases() {
            // given
            return Stream.of(
                Arguments.of(PieceType.GENERAL, true),
                Arguments.of(PieceType.JJU, false),
                Arguments.of(PieceType.ELEPHANT, false)
            );
        }

        @DisplayName("팀이 같거나 다른지를 반환한다.")
        @ParameterizedTest
        @MethodSource("provideTeamMatchCases")
        void isSameTeam(
            Team generalTeam,
            Team comparedTeam,
            boolean expected
        ) {
            // given
            General general = new General(generalTeam);

            // when
            boolean result = general.isSameTeam(comparedTeam);

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

        @DisplayName("왕은 팀을 반드시 가져야 한다.")
        @Test
        void validateNotNull() {
            // given
            Team nullTeam = null;

            // when & then
            assertThatThrownBy(() -> new General(nullTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물은 타입과 팀을 반드시 가져야 합니다.");
        }

        @DisplayName("왕은 궁성 내부에서 정의되지 않은 방향으로 이동할 수 없다.")
        @Test
        void findMovementRule_inPalace() {
            // given
            General general = new General(Team.RED);
            BoardPosition from = BoardPosition.RED_PALACE_NORTH_CENTER;
            BoardPosition to = BoardPosition.RED_PALACE_MIDDLE_WEST;
            Movement movement = new Movement(from, to);

            // when & then
            assertThatThrownBy(() -> general.findMovementRule(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 이동할 수 없습니다.");
        }

        @DisplayName("왕은 궁성 외부에서는 이동할 수 없다.")
        @Test
        void findMovementRule_outPalace() {
            // given
            General general = new General(Team.RED);
            BoardPosition from = new BoardPosition(1, 1);
            BoardPosition to = new BoardPosition(1, 2);
            Movement movement = new Movement(from, to);

            // when & then
            assertThatThrownBy(() -> general.findMovementRule(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 이동할 수 없습니다.");
        }

        @DisplayName("왕은 장애물을 넘을 수 없다.")
        @Test
        void validateMovementConditions() {
            // given
            General general = new General(Team.RED);
            List<Piece> obstacles = List.of(new Jju(Team.GREEN));
            Piece destination = new Jju(Team.GREEN);

            // when & then
            assertThatThrownBy(() -> general.validateMovementConditions(obstacles, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 장애물을 넘을 수 없습니다.");
        }
    }
}
