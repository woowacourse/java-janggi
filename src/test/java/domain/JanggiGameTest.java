package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Soldier;
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
                Position.of(1, 1), new Horse(TeamType.CHO),
                Position.of(1, 2), new Soldier(TeamType.HAN),
                Position.of(0, 1), new Soldier(TeamType.CHO),
                Position.of(0, 2), new Horse(TeamType.HAN),
                Position.of(4, 3), new King(TeamType.CHO),
                Position.of(3, 2), new King(TeamType.HAN)
        );
        janggiGame = new JanggiGame(players, pieces);
    }

    @Test
    @DisplayName("승자가 없으면 예외가 발생한다.")
    void findWinnerException() {
        // when & then
        assertThatThrownBy(() -> janggiGame.findWinner())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임이 종료되지 않아 우승을 판별할 수 없습니다.");
    }

    @Test
    @DisplayName("승자를 반환한다.")
    void findWinner() {
        // given
        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(3, 2);

        janggiGame.movePiece(startPosition, endPosition, TeamType.CHO);

        // when
        Player winner = janggiGame.findWinner();

        // then
        assertThat(winner).isEqualTo(new Player("a", TeamType.CHO));
    }
}
