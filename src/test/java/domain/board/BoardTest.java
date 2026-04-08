package domain.board;

import static domain.piece.PieceType.CANNON;
import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.GENERAL;
import static domain.piece.PieceType.GUARD;
import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.SOLDIER;
import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import controller.response.BoardView;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드 초기화 테스트")
    public void boardInitTest() {
        // given
        final Board board = BoardInitializer.initialize(ElephantSetup.INNER_ELEPHANT_SETUP,
                ElephantSetup.INNER_ELEPHANT_SETUP);

        // when
        final BoardView view = BoardView.from(board);

        // then
        assertThat(view.pieces()).hasSize(32);

        assertPiece(view, 10, 1, CHARIOT, CHO);
        assertPiece(view, 10, 9, CHARIOT, CHO);
        assertPiece(view, 10, 2, HORSE, CHO);
        assertPiece(view, 10, 3, ELEPHANT, CHO);
        assertPiece(view, 10, 4, GUARD, CHO);
        assertPiece(view, 9, 5, GENERAL, CHO);
        assertPiece(view, 10, 6, GUARD, CHO);
        assertPiece(view, 10, 7, ELEPHANT, CHO);
        assertPiece(view, 10, 8, HORSE, CHO);
        assertPiece(view, 8, 2, CANNON, CHO);
        assertPiece(view, 8, 8, CANNON, CHO);
        assertPiece(view, 7, 1, SOLDIER, CHO);
        assertPiece(view, 7, 3, SOLDIER, CHO);
        assertPiece(view, 7, 5, SOLDIER, CHO);
        assertPiece(view, 7, 7, SOLDIER, CHO);
        assertPiece(view, 7, 9, SOLDIER, CHO);

        assertPiece(view, 1, 1, CHARIOT, HAN);
        assertPiece(view, 1, 2, HORSE, HAN);
        assertPiece(view, 1, 3, ELEPHANT, HAN);
        assertPiece(view, 1, 4, GUARD, HAN);
        assertPiece(view, 2, 5, GENERAL, HAN);
        assertPiece(view, 1, 6, GUARD, HAN);
        assertPiece(view, 1, 7, ELEPHANT, HAN);
        assertPiece(view, 1, 8, HORSE, HAN);
        assertPiece(view, 1, 9, CHARIOT, HAN);
        assertPiece(view, 3, 2, CANNON, HAN);
        assertPiece(view, 3, 8, CANNON, HAN);
        assertPiece(view, 4, 1, SOLDIER, HAN);
        assertPiece(view, 4, 3, SOLDIER, HAN);
        assertPiece(view, 4, 5, SOLDIER, HAN);
        assertPiece(view, 4, 7, SOLDIER, HAN);
        assertPiece(view, 4, 9, SOLDIER, HAN);
    }

    private void assertPiece(final BoardView view, final int row, final int col, final PieceType pieceType,
                             final Team team) {
        final Piece piece = view.findPiece(Position.of(row, col)).orElseThrow();

        assertThat(piece.getPieceType()).isEqualTo(pieceType);
        assertThat(piece.getTeam()).isEqualTo(team);
    }
}
