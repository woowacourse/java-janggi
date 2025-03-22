import piece.Board;
import piece.Position;
import piece.JanggiPieceInitializer;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {

        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        Board board = new Board(janggiPieceInitializer.hanInit(), janggiPieceInitializer.choInit());
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

