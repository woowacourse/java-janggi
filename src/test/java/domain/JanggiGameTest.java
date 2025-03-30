package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.JanggiGame;
import domain.piece.Board;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.player.Player;
import domain.player.Players;
import domain.player.Username;
import domain.player.Usernames;
import domain.position.Position;
import domain.turn.Playing;
import domain.turn.Turn;
import domain.turn.TurnState;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private JanggiGame janggiGame;

    @BeforeEach
    void beforeEach() {
        Usernames usernames = new Usernames(new Username("a"), new Username("b"));
        Username startUsername = new Username("a");
        Players players = Players.createFrom(usernames, startUsername);
        Map<Position, Piece> pieces = Map.of(
                Position.of(1, 1), new Horse(TeamType.CHO),
                Position.of(1, 2), new Soldier(TeamType.HAN),
                Position.of(0, 1), new Soldier(TeamType.CHO),
                Position.of(0, 2), new Horse(TeamType.HAN),
                Position.of(4, 3), new King(TeamType.CHO),
                Position.of(3, 2), new King(TeamType.HAN)
        );
        Board board = new Board(pieces);
        Turn turn = new Playing(board, new TurnState(false, TeamType.CHO));
        janggiGame = JanggiGame.from(players, turn);
    }

    @Test
    @DisplayName("승자가 없으면 예외가 발생한다.")
    void findWinnerException() {
        // when & then
        assertThatThrownBy(() -> janggiGame.findWinner())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("현재 게임이 진행중입니다. 우승자를 판별할 수 없습니다.");
    }

    @Test
    @DisplayName("승자를 반환한다.")
    void findWinner() {
        // given
        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(3, 2);

        janggiGame.movePiece(startPosition, endPosition);

        // when
        Player winner = janggiGame.findWinner();

        // then
        assertThat(winner).isEqualTo(new Player(new Username("a"), TeamType.CHO));
    }
}
