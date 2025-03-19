package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    private JanggiGame janggiGame;

    @BeforeEach
    void beforeEach() {
        Players players = new Players(new Player("a", TeamType.CHO), new Player("b", TeamType.HAN));
        List<Piece> pieces = List.of(
                new Horse(Position.of(1, 1), TeamType.CHO),
                new Soldier(Position.of(1, 2), TeamType.HAN),
                new Soldier(Position.of(0, 1), TeamType.CHO),
                new Horse(Position.of(0, 2), TeamType.HAN),
                new King(Position.of(4, 3), TeamType.CHO),
                new King(Position.of(3, 2), TeamType.HAN)
        );
        janggiGame = new JanggiGame(players, pieces);
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
        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(3, 2);

        janggiGame.movePiece(startPosition, endPosition, TeamType.CHO);

        Player winner = janggiGame.findWinner();

        assertThat(winner).isEqualTo(new Player("a", TeamType.CHO));
    }
}
