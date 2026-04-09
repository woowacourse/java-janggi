package repository;

import model.board.Board;
import model.board.Country;
import model.game.GameSession;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GameRestorerTest {

    @Test
    void savedGame으로부터_board를_복원할_수_있다() {
        SavedGame savedGame = new SavedGame(
                Country.CHO,
                false,
                null,
                List.of(
                        new SavedPiece(10, 1, Country.CHO, PieceType.CHARIOT),
                        new SavedPiece(2, 5, Country.HAN, PieceType.GENERAL)
                )
        );

        Board board = GameRestorer.restoreBoard(savedGame);

        assertThat(board.findPiece(Position.of(10, 1)))
                .isEqualTo(new Piece(Country.CHO, PieceType.CHARIOT));
        assertThat(board.findPiece(Position.of(2, 5)))
                .isEqualTo(new Piece(Country.HAN, PieceType.GENERAL));
    }

    @Test
    void savedGame으로부터_gameSession을_복원할_수_있다() {
        SavedGame savedGame = new SavedGame(
                Country.CHO,
                true,
                Country.CHO,
                List.of(
                        new SavedPiece(10, 1, Country.CHO, PieceType.CHARIOT),
                        new SavedPiece(2, 5, Country.HAN, PieceType.GENERAL)
                )
        );

        GameSession session = GameRestorer.restore(savedGame);

        assertThat(session.game().turn()).isEqualTo(Country.CHO);
        assertThat(session.game().isFinished()).isTrue();
        assertThat(session.game().winner()).isEqualTo(Country.CHO);

        assertThat(session.board().findPiece(Position.of(10, 1)))
                .isEqualTo(new Piece(Country.CHO, PieceType.CHARIOT));
        assertThat(session.board().findPiece(Position.of(2, 5)))
                .isEqualTo(new Piece(Country.HAN, PieceType.GENERAL));
    }
}
