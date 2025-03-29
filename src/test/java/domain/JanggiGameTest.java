package domain;

import static fixtures.PositionFixture.B0;
import static fixtures.PositionFixture.B1;
import static fixtures.PositionFixture.C0;
import static fixtures.PositionFixture.C1;
import static fixtures.PositionFixture.C3;
import static fixtures.PositionFixture.D4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.participants.Player;
import domain.participants.Players;
import domain.participants.Usernames;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private JanggiGame janggiGame;

    @BeforeEach
    void beforeEach() {
        Usernames usernames = new Usernames("a", "b");
        String startUsername = "a";
        Players players = Players.createFrom(usernames, startUsername);
        Map<Position, Piece> pieces = Map.of(
                B1, new Horse(TeamType.CHO),
                C1, new Soldier(TeamType.HAN),
                B0, new Soldier(TeamType.CHO),
                C0, new Horse(TeamType.HAN),
                D4, new King(TeamType.CHO),
                C3, new King(TeamType.HAN)
        );
        janggiGame = new JanggiGame(players, pieces, new GameStatus("a"));
    }

    @Test
    @DisplayName("승자를 없으면 예외가 발생한다.")
    void findWinnerException() {
        assertThatThrownBy(() -> janggiGame.findWinner())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아직 게임이 끝나지 않았습니다.");
    }

    @Test
    @DisplayName("승자를 반환한다.")
    void findWinner() {
        Position startPosition = B1;
        Position endPosition = C3;

        janggiGame.movePiece(startPosition, endPosition);

        Player winner = janggiGame.findWinner();

        assertThat(winner).isEqualTo(new Player("a", TeamType.CHO));
    }
}
