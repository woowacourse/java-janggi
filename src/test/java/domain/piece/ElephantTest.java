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

class ElephantTest {

    @Nested
    class ValidCases {

        @DisplayName("상은 정해진 경로로 이동할 수 있다.")
        @Test
        void findMovementRule() {
            // given
            Elephant elephant = new Elephant(Team.RED);
            BoardPosition from = new BoardPosition(4, 4);
            BoardPosition to = new BoardPosition(7, 6);
            Movement movement = new Movement(from, to);

            // when
            List<Offset> result = elephant.findMovementRule(movement);

            // then
            assertThat(result).isEqualTo(List.of(
                new Offset(1, 0),
                new Offset(1, 1),
                new Offset(1, 1)
            ));
        }

        @DisplayName("상은 장애물이 없으면 이동할 수 있다.")
        @Test
        void validateMovementConditions() {
            // given
            Elephant elephant = new Elephant(Team.RED);
            List<Piece> obstacles = List.of();
            Piece destination = new Jju(Team.GREEN);

            // when & then
            assertThatCode(() -> elephant.validateMovementConditions(obstacles, destination))
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
            Elephant elephant = new Elephant(Team.RED);

            // when
            boolean result = elephant.isSamePieceType(comparedType);

            // then
            assertThat(result).isEqualTo(expected);
        }

        static Stream<Arguments> providePieceTypeMatchCases() {
            // given
            return Stream.of(
                Arguments.of(PieceType.ELEPHANT, true),
                Arguments.of(PieceType.JJU, false),
                Arguments.of(PieceType.GENERAL, false)
            );
        }

        @DisplayName("팀이 같거나 다른지를 반환한다.")
        @ParameterizedTest
        @MethodSource("provideTeamMatchCases")
        void isSameTeam(
            Team elephantTeam,
            Team comparedTeam,
            boolean expected
        ) {
            // given
            Elephant elephant = new Elephant(elephantTeam);

            // when
            boolean result = elephant.isSameTeam(comparedTeam);

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

        @DisplayName("상은 팀을 반드시 가져야 한다.")
        @Test
        void validateNotNull() {
            // given
            Team nullTeam = null;

            // when & then
            assertThatThrownBy(() -> new Elephant(nullTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물은 타입과 팀을 반드시 가져야 합니다.");
        }

        @DisplayName("상은 정의되지 않은 방향으로 이동할 수 없다.")
        @Test
        void findMovementRule() {
            // given
            Elephant elephant = new Elephant(Team.RED);
            BoardPosition from = new BoardPosition(0, 0);
            BoardPosition to = new BoardPosition(1, 1);
            Movement movement = new Movement(from, to);

            // when & then
            assertThatThrownBy(() -> elephant.findMovementRule(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 이동할 수 없습니다.");
        }

        @DisplayName("상은 장애물을 넘을 수 없다.")
        @Test
        void validateMovementConditions() {
            // given
            Elephant elephant = new Elephant(Team.RED);
            List<Piece> obstacles = List.of(new Jju(Team.GREEN));
            Piece destination = new Jju(Team.GREEN);

            // when & then
            assertThatThrownBy(() -> elephant.validateMovementConditions(obstacles, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 장애물을 넘을 수 없습니다.");
        }
    }
}
