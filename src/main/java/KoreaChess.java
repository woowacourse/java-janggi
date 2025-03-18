import domain.Board;
import domain.PieceColor;
import domain.Player;
import domain.piece.Piece;
import domain.piece.PieceInit;
import domain.piece.Pieces;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class KoreaChess {

    private final OutputView outputView;
    private final InputView inputView;

    public KoreaChess(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        Board board = createBoard();
        outputView.printBoard(board);
    }

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
