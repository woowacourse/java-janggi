package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceDirection;
import domain.piece.PieceInit;
import domain.piece.Pieces;
import domain.piece.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 플레이어의_기물을_이동한다() {
        // given
        Position startPosition = Position.of(1, 4);
        Position targetPosition = Position.of(1, 5);

        Piece expected = new Pawn(1, 5, PieceDirection.HAN_PAWN.get());

        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        List<Piece> hanPieces = PieceInit.initHanPieces();
        List<Piece> choPieces = PieceInit.initChoPieces();

        Map<Player, Pieces> boardElements = new HashMap<>();
        boardElements.put(han, new Pieces(hanPieces));
        boardElements.put(cho, new Pieces(choPieces));

        Board board = new Board(boardElements);

        // when
        board.move(han, startPosition, targetPosition);

        // then
        assertThat(hanPieces).contains(expected);
    }
}
