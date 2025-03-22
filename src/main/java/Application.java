import piece.Board;
import piece.Position;
import piece.ChessPieceInitializer;
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
                Position presentPosition = inputView.readPresentPick();
                Position futurePosition = inputView.readFuturePick();

                board.updateBoard(presentPosition, futurePosition);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}

