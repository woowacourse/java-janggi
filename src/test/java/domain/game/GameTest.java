package domain.game;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void 게임_상태가_진행중인지_확인할_수_있다() {
        Game game = new Game(
                new Board(Map.of(Position.of(0, 0), Piece.of(TeamColor.CHO, PieceType.KING))),
                new TurnManager(),
                GameStatus.IN_PROGRESS
        );

        assertThat(game.isInProgress()).isTrue();
    }

    @Test
    void 게임을_종료_상태로_변경할_수_있다() {
        Game game = new Game(
                new Board(Map.of(Position.of(0, 0), Piece.of(TeamColor.CHO, PieceType.KING))),
                new TurnManager(),
                GameStatus.IN_PROGRESS
        );

        game.finish();

        assertThat(game.status()).isEqualTo(GameStatus.FINISHED);
        assertThat(game.isInProgress()).isFalse();
    }
}
