import chessPiece.Board;
import chessPiece.BoardPosition;
import chessPiece.ChessPieceInitializer;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {

        ChessPieceInitializer chessPieceInitializer = new ChessPieceInitializer();
        Board board = new Board(chessPieceInitializer.hanInit(), chessPieceInitializer.choInit());
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        while (true) {
            try {
                outputView.printJanggipan(board.getJanggiPan());
                BoardPosition presentPosition = inputView.readPresentPick();
                BoardPosition futurePosition = inputView.readFuturePick();

                board.updateBoard(presentPosition, futurePosition);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}

