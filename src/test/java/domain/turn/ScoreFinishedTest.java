package domain.turn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.TeamType;
import domain.piece.Board;
import domain.piece.Chariot;
import domain.piece.King;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ScoreFinishedTest {

    @Test
    @DisplayName("왕이 죽어서 게임이 끝났는지 여부를 반환한다")
    void isFinishedByCheckmateTest() {
        // given
        Board board = new Board(Map.of());
        Finished finished = new ScoreFinished(board, new TurnState(false, TeamType.HAN));

        // when
        boolean actual = finished.isFinishedByCheckmate();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("게임 종료 상태를 반환한다")
    void isFinishedTest() {
        // given
        Board board = new Board(Map.of());
        Turn turn = new ScoreFinished(board, new TurnState(true, TeamType.HAN));

        // when
        boolean actual = turn.isFinished();

        // then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "true, HAN",
            "true, CHO"
    })
    @DisplayName("무르기를 하는 경우 예외가 발생한다")
    void undoException(boolean undoLast, TeamType teamType) {
        // given
        Board board = new Board(Map.of());
        Turn turn = new ScoreFinished(board, new TurnState(undoLast, teamType));

        // when & then
        assertThatThrownBy(turn::undo)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임이 종료된 상태에서는 무르기를 요청할 수 없습니다.");
    }

    @Test
    @DisplayName("기물을 이동하는 경우 예외가 발생한다")
    void undoException() {
        // given
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 3), new King(TeamType.HAN),
                Position.of(6, 3), new King(TeamType.CHO),
                Position.of(4, 3), new Chariot(TeamType.HAN)
        );

        Board board = new Board(pieces);
        Turn turn = new ScoreFinished(board, new TurnState(true, TeamType.HAN));
        Position moveFrom = Position.of(4, 3);
        Position moveTo = Position.of(6, 3);

        // when & then
        assertThatThrownBy(() -> turn.movePiece(moveFrom, moveTo))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임이 종료된 상태에서는 기물을 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("팀별 점수를 반환한다")
    void calculateScoreTest() {
        // given
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 3), new King(TeamType.HAN),
                Position.of(6, 3), new King(TeamType.CHO),
                Position.of(4, 3), new Chariot(TeamType.HAN)
        );

        Board board = new Board(pieces);
        Turn turn = new ScoreFinished(board, new TurnState(true, TeamType.HAN));

        // when
        Map<TeamType, Double> teamScore = turn.calculateTeamScore();

        // then
        double hanScore = 14.5;
        double choScore = 0;
        assertThat(teamScore).containsEntry(TeamType.HAN, hanScore);
        assertThat(teamScore).containsEntry(TeamType.CHO, choScore);
    }

    @Test
    @DisplayName("우승한 팀을 반환한다")
    void findWinTeamTest() {
        // given
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 3), new King(TeamType.HAN),
                Position.of(6, 3), new King(TeamType.CHO),
                Position.of(4, 3), new Chariot(TeamType.HAN)
        );

        Board board = new Board(pieces);
        Turn turn = new ScoreFinished(board, new TurnState(true, TeamType.HAN));

        // when
        TeamType actual = turn.findWinTeam();

        // then
        assertThat(actual).isEqualTo(TeamType.HAN);
    }
}
