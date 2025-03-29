package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JjuTest {

    @Nested
    class ValidCases {

        @DisplayName("쭈는 팀에 따라 정해진 방향으로만 이동할 수 있다.")
        @ParameterizedTest
        @MethodSource("provideMovementCases")
        void findMovementRule(
            Team team,
            BoardPosition from,
            BoardPosition to,
            Offset expected
        ) {
            // given
            Jju jju = new Jju(team);

            // when
            List<Offset> result = jju.findMovementRule(from, to);

            // then
            assertThat(result).isEqualTo(List.of(expected));
        }

        static Stream<Arguments> provideMovementCases() {
            // given
            return Stream.of(
                // GREEN
                Arguments.of(Team.GREEN, new BoardPosition(4, 4), new BoardPosition(5, 4),
                    new Offset(1, 0)),
                Arguments.of(Team.GREEN, new BoardPosition(4, 4), new BoardPosition(3, 4),
                    new Offset(-1, 0)),
                Arguments.of(Team.GREEN, new BoardPosition(4, 4), new BoardPosition(4, 5),
                    new Offset(0, 1)),

                // RED
                Arguments.of(Team.RED, new BoardPosition(4, 4), new BoardPosition(5, 4),
                    new Offset(1, 0)),
                Arguments.of(Team.RED, new BoardPosition(4, 4), new BoardPosition(3, 4),
                    new Offset(-1, 0)),
                Arguments.of(Team.RED, new BoardPosition(4, 4), new BoardPosition(4, 3),
                    new Offset(0, -1))
            );
        }

        @DisplayName("기물의 타입이 같은지 여부를 반환한다.")
        @ParameterizedTest
        @MethodSource("providePieceTypeMatchCases")
        void isSamePieceType(
            PieceType comparedType,
            boolean expected
        ) {
            // given
            Jju jju = new Jju(Team.RED);

            // when
            boolean result = jju.isSamePieceType(comparedType);

            // then
            assertThat(result).isEqualTo(expected);
        }

        static Stream<Arguments> providePieceTypeMatchCases() {
            // given
            return Stream.of(
                Arguments.of(PieceType.JJU, true),
                Arguments.of(PieceType.ELEPHANT, false),
                Arguments.of(PieceType.GENERAL, false)
            );
        }

        @DisplayName("팀이 같거나 다른지를 반환한다.")
        @ParameterizedTest
        @MethodSource("provideTeamMatchCases")
        void isSameTeam(
            Team jjuTeam,
            Team comparedTeam,
            boolean expected
        ) {
            // given
            Jju jju = new Jju(jjuTeam);

            // when
            boolean result = jju.isSameTeam(comparedTeam);

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

        @DisplayName("쭈는 팀을 반드시 가져야 한다.")
        @Test
        void validateNotNull() {
            // given
            Team nullTeam = null;

            // when & then
            assertThatThrownBy(() -> new Jju(nullTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물은 타입과 팀을 반드시 가져야 합니다.");
        }

        @DisplayName("쭈는 정의되지 않은 방향으로 이동할 수 없다.")
        @Test
        void findMovementRule() {
            // given
            Jju jju = new Jju(Team.RED);

            BoardPosition from = new BoardPosition(0, 0);
            BoardPosition to = new BoardPosition(1, 1);

            // when & then
            assertThatThrownBy(() -> jju.findMovementRule(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 이동할 수 없습니다.");
        }

        @DisplayName("쭈는 장애물을 넘을 수 없다.")
        @Test
        void validateMoveRule() {
            // given
            Jju jju = new Jju(Team.RED);
            List<Piece> obstacles = List.of(new Guard(Team.GREEN));
            Piece destination = new Guard(Team.GREEN);

            // when & then
            assertThatThrownBy(() -> jju.validateMoveRule(obstacles, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 장애물을 넘을 수 앖습니다.");
        }
    }
}
