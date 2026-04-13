package repository;

import model.board.Board;
import model.board.Country;
import model.game.JanggiGame;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameSnapshotTest {
    @Test
    void 현재_게임_상태를_savedGame_으로_변환한다() {
        Board board = new Board();
        JanggiGame game = new JanggiGame(board);

        board.place(Position.of(2, 5), new Piece(Country.HAN, PieceType.GENERAL));
        board.place(Position.of(1, 10), new Piece(Country.CHO, PieceType.CHARIOT));

        SavedGame savedGame = GameSnapshot.from(game, board);

        assertThat(savedGame.turn()).isEqualTo(Country.CHO);
        assertThat(savedGame.finished()).isFalse();
        assertThat(savedGame.winner()).isNull();
        assertThat(savedGame.pieces()).containsExactlyInAnyOrder(
                new SavedPiece(2, 5, Country.HAN, PieceType.GENERAL),
                new SavedPiece(1, 10, Country.CHO, PieceType.CHARIOT)
        );
    }
}
