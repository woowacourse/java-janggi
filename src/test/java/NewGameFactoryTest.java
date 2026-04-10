import domain.board.Position;
import domain.game.FormationType;
import domain.game.Game;
import domain.game.GameStatus;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NewGameFactoryTest {

    @Test
    void 새_게임은_상차림으로만_초기_보드를_생성한다() {
        NewGameFactory factory = new NewGameFactory();

        Game game = factory.create(FormationType.INNER, FormationType.OUTER);

        assertThat(game.status()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(game.currentTurn()).isEqualTo(TeamColor.CHO);
        assertThat(game.board().findPiece(Position.of(0, 1)).orElseThrow().getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(game.board().findPiece(Position.of(9, 1)).orElseThrow().getPieceType()).isEqualTo(PieceType.HORSE);
    }
}
