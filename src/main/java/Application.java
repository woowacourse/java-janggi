import janggiGame.Board;
import janggiGame.Position;
import janggiGame.arrangement.ArrangementOption;
import janggiGame.piece.Dynasty;
import java.util.List;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        Board board = new Board();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        Dynasty[] dynasties = Dynasty.values();
        int turn = 0;

        int option = inputView.readHanArrangement();
        board.arrangeHanPieces(ArrangementOption.findBy(option).getArrangementStrategy());

        option = inputView.readChoArrangement();
        board.arrangeChoPieces(ArrangementOption.findBy(option).getArrangementStrategy());

        while (true) {
            outputView.printBoard(board.getSurvivedPieces());
            try {
                Dynasty currentDynasty = dynasties[turn % 2];

                List<Position> movement = inputView.readPieceMovement(currentDynasty);

                board.processTurn(currentDynasty, movement.getFirst(), movement.getLast());

                turn++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
