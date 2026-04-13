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

    @Test
    void 왕을_잡으면_게임을_종료하고_승자를_반환한다() {
        final Piece choRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
        Game game = new Game(
                new Board(Map.of(
                        Position.of(4, 4), choRook,
                        Position.of(1, 4), Piece.of(TeamColor.HAN, PieceType.KING)
                )),
                new TurnManager(TeamColor.CHO),
                GameStatus.IN_PROGRESS
        );

        TurnResult result = game.move(choRook, Position.of(1, 4));

        assertThat(game.status()).isEqualTo(GameStatus.FINISHED);
        assertThat(result.moveResult().capturedKing()).isTrue();
        assertThat(result.winner()).contains(TeamColor.CHO);
    }

    @Test
    void 왕을_잡지_않으면_턴을_넘긴다() {
        final Piece choPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
        Game game = new Game(
                new Board(Map.of(Position.of(4, 4), choPawn)),
                new TurnManager(TeamColor.CHO),
                GameStatus.IN_PROGRESS
        );

        TurnResult result = game.move(choPawn, Position.of(3, 4));

        assertThat(game.status()).isEqualTo(GameStatus.IN_PROGRESS);
        assertThat(game.currentTurn()).isEqualTo(TeamColor.HAN);
        assertThat(result.winner()).isEmpty();
    }
}
