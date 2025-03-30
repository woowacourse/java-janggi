package domain.turn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Board;
import domain.TeamType;
import domain.piece.Chariot;
import domain.piece.King;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayingTest {

    @Test
    @DisplayName("게임이 종료되지 않았다")
    void isFinishedTest() {
        // given
        Board board = new Board(Map.of());
        Turn turn = new Playing(board, new TurnState(false, TeamType.HAN));

        // when
        boolean actual = turn.isFinished();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("킹이 잡힌 경우 Finished 객체를 반환한다")
    void FinishedKingDeadTest() {
        // given
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 3), new King(TeamType.CHO),
                Position.of(6, 6), new King(TeamType.HAN),
                Position.of(3, 6), new Chariot(TeamType.CHO)
        );
        Board board = new Board(pieces);
        Turn turn = new Playing(board, new TurnState(false, TeamType.CHO));

        // when
        Turn nextTurn = turn.movePiece(Position.of(3, 6), Position.of(6, 6));

        // then
        assertThat(nextTurn.isFinished()).isTrue();
    }

    @Test
    @DisplayName("킹이 잡히지 않은 경우 Playing 객체를 반환한다")
    void PlayingKingAliveTest() {
        // given
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 3), new King(TeamType.CHO),
                Position.of(6, 6), new King(TeamType.HAN),
                Position.of(3, 6), new Chariot(TeamType.CHO)
        );
        Board board = new Board(pieces);
        Turn turn = new Playing(board, new TurnState(false, TeamType.CHO));

        // when
        Turn nextTurn = turn.movePiece(Position.of(3, 6), Position.of(4, 6));

        // then
        assertThat(nextTurn.isFinished()).isFalse();
    }

    @Test
    @DisplayName("상대편이 무르기를 하지 않은 상태에서 무르기를 하면 게임이 종료된다")
    void undoGameFinishedTest() {
        // given
        Board board = new Board(Map.of());
        Turn turn = new Playing(board, new TurnState(true, TeamType.CHO));

        // when
        Turn nextTurn = turn.undo();

        // then
        assertThat(nextTurn.isFinished()).isTrue();
    }

    @Test
    @DisplayName("상대편이 무르기를 하지 않은 상태에서 무르기를 하면 게임이 진행된다")
    void undoGamePlayingTest() {
        // given
        Board board = new Board(Map.of());
        Turn turn = new Playing(board, new TurnState(false, TeamType.CHO));

        // when
        Turn nextTurn = turn.undo();

        // then
        TurnState expected = new TurnState(true, TeamType.HAN);
        assertThat(nextTurn.turnState).isEqualTo(expected);
    }

    @Test
    @DisplayName("점수를 계산하면 예외가 발생한다")
    void calculateScoreException() {
        // given
        Board board = new Board(Map.of());
        Turn turn = new Playing(board, new TurnState(false, TeamType.HAN));

        // when & then
        assertThatThrownBy(turn::calculateTeamScore)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("현재 게임이 진행중입니다. 점수를 계산할 수 없습니다.");
    }

    @Test
    @DisplayName("우승자를 판별하면 예외가 발생한다")
    void findWinTeamException() {
        // given
        Board board = new Board(Map.of());
        Turn turn = new Playing(board, new TurnState(false, TeamType.HAN));

        // when & then
        assertThatThrownBy(turn::findWinTeam)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("현재 게임이 진행중입니다. 우승자를 판별할 수 없습니다.");
    }
}
