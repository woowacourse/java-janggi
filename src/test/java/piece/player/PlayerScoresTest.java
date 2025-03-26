package piece.player;

import java.util.Map;
import move.JolMoveBehavior;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.position.JanggiPosition;

class PlayerScoresTest {

    @Test
    void 죽은피스로_점수를_계산할_수_있다() {
        PlayerScores playerScores = new PlayerScores();
        Piece bluePiece = new Piece(new JanggiPosition(0, 0), new JolMoveBehavior(), Team.BLUE);
        playerScores.add(bluePiece);
        Map<Team, Integer> currentPlayersScores = playerScores.getCurrentPlayersScores();
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(currentPlayersScores.get(Team.BLUE)).isEqualTo(0),
                () -> Assertions.assertThat(currentPlayersScores.get(Team.RED)).isEqualTo(2)
        );
    }
}

