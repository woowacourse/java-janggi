import domain.Board;
import domain.PieceColor;
import domain.Player;
import domain.piece.Piece;
import domain.piece.PieceInit;
import domain.piece.Pieces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KoreaChess {

    public Board createBoard() {
        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        List<Piece> hanPieces = PieceInit.initHanPieces();
        List<Piece> choPieces = PieceInit.initChoPieces();

        Map<Player, Pieces> board = new HashMap<>();
        board.put(han, new Pieces(hanPieces));
        board.put(cho, new Pieces(choPieces));

        return new Board(board);
    }
}
