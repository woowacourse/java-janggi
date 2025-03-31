package domain.janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.General;
import domain.piece.Jju;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Score;
import domain.piece.Team;
import domain.turn.Turn;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JanggiTest {

    @Nested
    class ValidCases {

        @DisplayName("장기를 초기화하면 보드와 팀이 초기값으로 설정된다.")
        @Test
        void initialize() {
            // when
            Janggi janggi = Janggi.initialize();

            // then
            Set<PieceType> pieceTypes = janggi.getBoard()
                .getPieces()
                .values()
                .stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toSet());

            assertSoftly(softly -> {
                softly.assertThat(pieceTypes).containsExactlyInAnyOrder(PieceType.values());
                softly.assertThat(janggi.getTurn().getCurrentTeam()).isEqualTo(Team.GREEN);
            });
        }

        @DisplayName("현재 팀의 기물이 선택된 위치에 있다.")
        @Test
        void validateSelectedPiece() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.GREEN)
            ));
            Turn turn = new Turn(Team.GREEN);
            Janggi janggi = new Janggi(board, turn);

            // when & then
            assertThatCode(() -> janggi.validateSelectedPiece(new BoardPosition(0, 0)))
                .doesNotThrowAnyException();
        }

        @DisplayName("기물 이동을 성공하면 턴이 바뀐다.")
        @Test
        void processTurn() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.GREEN)
            ));
            Turn turn = new Turn(Team.GREEN);
            Janggi janggi = new Janggi(board, turn);

            // when
            janggi.processTurn(new BoardPosition(0, 0), new BoardPosition(0, 1));

            // then
            assertThat(janggi.getTurn().getCurrentTeam()).isEqualTo(Team.RED);
        }


        @DisplayName("각 팀의 점수를 계산한다.")
        @Test
        void calculateTeamScores() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.GREEN),
                new BoardPosition(0, 1), new Jju(Team.GREEN),
                new BoardPosition(1, 0), new Jju(Team.RED),
                new BoardPosition(1, 1), new General(Team.RED)
            ));
            Turn turn = new Turn(Team.GREEN);
            Janggi janggi = new Janggi(board, turn);

            // when
            Map<Team, Score> result = janggi.calculateTeamScores();

            // then
            assertSoftly(softly -> {
                softly.assertThat(result.get(Team.GREEN).value()).isEqualTo(4.0f);
                softly.assertThat(result.get(Team.RED).value()).isEqualTo(3.5f);
            });
        }

        @DisplayName("왕이 하나만 남으면 게임은 종료된다.")
        @Test
        void isGameOver() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new General(Team.GREEN)
            ));
            Turn turn = new Turn(Team.RED);
            Janggi janggi = new Janggi(board, turn);

            // when
            boolean isGameOver = janggi.isGameOver();

            // then
            assertThat(isGameOver).isTrue();
        }

        @DisplayName("왕이 하나만 남았을 때 현재 팀의 상대 팀이 승리한다.")
        @Test
        void calculateWinner() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new General(Team.GREEN)
            ));
            Turn turn = new Turn(Team.RED);
            Janggi janggi = new Janggi(board, turn);

            // when
            Team winner = janggi.calculateWinner();

            // then
            assertThat(winner).isEqualTo(Team.GREEN);
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("장기는 보드나 턴을 반드시 가져야 한다.")
        @ParameterizedTest
        @MethodSource("provideNullArguments")
        void validateNotNull(
            Board board,
            Turn turn
        ) {
            // when & then
            assertThatThrownBy(() -> new Janggi(board, turn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기는 보드와 턴을 가져야합니다.");
        }

        static Stream<Arguments> provideNullArguments() {
            // given
            return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, new Turn(Team.GREEN)),
                Arguments.of(new Board(Map.of()), null)
            );
        }

        @DisplayName("해당 위치에 말이 없거나 상대 팀의 말이면 예외가 발생한다.")
        @Test
        void validateSelectedPiece() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.RED)
            ));
            Turn turn = new Turn(Team.GREEN);
            Janggi janggi = new Janggi(board, turn);

            // when & then
            assertThatThrownBy(() -> janggi.validateSelectedPiece(new BoardPosition(0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 말이 없거나 상대팀의 말입니다.");
        }

        @DisplayName("게임이 종료되지 않은 상태에서 승자를 계산하지 못한다.")
        @Test
        void calculateWinner_notYetGameOver() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new General(Team.GREEN),
                new BoardPosition(0, 1), new General(Team.RED)
            ));
            Turn turn = new Turn(Team.RED);
            Janggi janggi = new Janggi(board, turn);

            // when & then
            assertThatThrownBy(janggi::calculateWinner)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임이 종료되지 않았습니다.");
        }
    }
}
