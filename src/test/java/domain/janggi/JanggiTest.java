package domain.janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.board.InitialBoardFixture;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Zzu;
import java.util.Map;
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

        @DisplayName("초기 Janggi 객체를 생성한다.")
        @Test
        void initialize() {
            // given & when
            Janggi janggi = Janggi.initialize(1, "title");

            // then
            assertAll(
                    () -> assertThat(janggi.getPieces()).isEqualTo(InitialBoardFixture.rawInitialBoard),
                    () -> assertThat(janggi.getCurrentTeam()).isEqualTo(Team.GREEN),
                    () -> assertThat(janggi.getTitle()).isEqualTo("title"),
                    () -> assertThat(janggi.getId()).isEqualTo(1)
            );
        }

        @DisplayName("기물 이동을 성공하면 턴이 바뀐다.")
        @Test
        void processTurn() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Zzu(Team.GREEN)
            ));
            Janggi janggi = new Janggi(1, "title", board, new Turn(Team.GREEN));

            // when
            janggi.processTurn(new BoardPosition(0, 0), new BoardPosition(0, 1));

            // then
            assertThat(janggi.getCurrentTeam()).isEqualTo(Team.RED);
        }

        @DisplayName("원하는 팀의 점수를 알려준다.")
        @Test
        void findScore() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new General(Team.RED),
                    new BoardPosition(0, 1), new Chariot(Team.RED),
                    new BoardPosition(0, 2), new Horse(Team.RED),
                    new BoardPosition(0, 3), new Zzu(Team.RED),
                    new BoardPosition(1, 0), new General(Team.GREEN),
                    new BoardPosition(1, 1), new Elephant(Team.GREEN),
                    new BoardPosition(1, 2), new Guard(Team.GREEN),
                    new BoardPosition(1, 3), new Cannon(Team.GREEN)
            ));
            Janggi janggi = new Janggi(1, "title", board, new Turn(Team.GREEN));

            // when & then
            assertAll(
                    () -> assertThat(janggi.findScore(Team.RED)).isEqualTo(new Score(21.5f)),
                    () -> assertThat(janggi.findScore(Team.GREEN)).isEqualTo(new Score(13))
            );
        }

        @DisplayName("게임이 종료되었는지 확인한다.")
        @ParameterizedTest
        @MethodSource("provideBoard")
        void isGameFinish(Board board, boolean gameFinishFlag) {
            // given
            Janggi janggi = new Janggi(1, "title", board, new Turn(Team.GREEN));

            // when & then
            assertThat(janggi.isGameFinish()).isEqualTo(gameFinishFlag);
        }

        static Stream<Arguments> provideBoard() {
            return Stream.of(
                    Arguments.of(new Board(
                            Map.of(new BoardPosition(0, 0), new General(Team.RED),
                                    new BoardPosition(5, 5), new General(Team.GREEN))), false),
                    Arguments.of(new Board(
                            Map.of(new BoardPosition(0, 0), new General(Team.RED))), true),
                    Arguments.of(new Board(
                            Map.of(new BoardPosition(0, 0), new General(Team.GREEN))), true)
            );
        }

        @DisplayName("승리 팀을 찾는다.")
        @Test
        void findWinnerTeam() {
            // given
            Board board = new Board(Map.of(new BoardPosition(0, 0), new General(Team.RED)));
            Janggi janggi = new Janggi(1, "title", board, new Turn(Team.GREEN));

            // when & then
            assertThat(janggi.findWinnerTeam()).isEqualTo(Team.RED);
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("승리 팀이 결정되지 않았는데 승리 팀을 찾는다면 예외가 발생한다.")
        @Test
        void findWinnerTeam() {
            // given
            Board board = new Board(
                    Map.of(new BoardPosition(0, 0), new General(Team.RED),
                            new BoardPosition(5, 5), new General(Team.GREEN)));
            Janggi janggi = new Janggi(1, "title", board, new Turn(Team.GREEN));

            // when & then
            assertThatThrownBy(janggi::findWinnerTeam)
                    .isInstanceOf(IllegalCallerException.class)
                    .hasMessage("게임이 종료되지 않았습니다.");
        }
    }
}
