import domain.Board;
import domain.PieceColor;
import domain.Player;
import domain.piece.Piece;
import domain.piece.PieceInit;
import domain.piece.Pieces;
import domain.piece.Position;
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
        Player han = new Player("한", PieceColor.RED);
        Player cho = new Player("초", PieceColor.BLUE);

        Board board = createBoard(han, cho);
        outputView.printBoard(board);

        while (true) {
            processTurn(han, board);
            processTurn(cho, board);
        }
    }

    private void processTurn(Player player, Board board) {
        Position movingHanPosition = parseToPosition(inputView.readMovingPiecePosition(player));
        Position targetHanPosition = parseToPosition(inputView.readTargetPiecePosition());
        board.move(player, movingHanPosition, targetHanPosition);
        outputView.printBoard(board);
    }

    private Position parseToPosition(final String input) {
        List<String> positionElements = List.of(input.split(","));
        int row = Integer.parseInt(positionElements.getFirst());
        int column = Integer.parseInt(positionElements.getLast());

        return Position.of(row, column);
    }

    private Board createBoard(final Player han, final Player cho) {
        List<Piece> hanPieces = PieceInit.initHanPieces();
        List<Piece> choPieces = PieceInit.initChoPieces();

        Map<Player, Pieces> board = new HashMap<>();
        board.put(han, new Pieces(hanPieces));
        board.put(cho, new Pieces(choPieces));

        return new Board(board);
    }
}
