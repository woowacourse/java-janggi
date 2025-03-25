import janggiGame.Board;
import janggiGame.Position;
import janggiGame.arrangement.ArrangementOption;
import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.piece.Dynasty;
import java.util.List;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        Board board = settingJanggiGame(inputView);

        Dynasty[] dynasties = Dynasty.values();
        int turn = 0;

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

    private static Board settingJanggiGame(InputView inputView) {
        ArrangementStrategy hanStrategy = choiceArrangementStrategy(Dynasty.HAN, inputView);
        ArrangementStrategy choStrategy = choiceArrangementStrategy(Dynasty.CHO, inputView);
        return new Board(hanStrategy, choStrategy);
    }

    private static ArrangementStrategy choiceArrangementStrategy(Dynasty dynasty, InputView inputView) {
        int option = inputView.readArrangementStrategyByDynasty(dynasty);
        return ArrangementOption.findBy(option).getArrangementStrategy();
    }
}
