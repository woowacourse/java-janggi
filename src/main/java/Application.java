import janggiGame.JanggiGame;
import janggiGame.arrangement.OuterElephantStrategy;
import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import java.util.List;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame();

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Dynasty[] dynasties = Dynasty.values();
        int turn = 0;

        janggiGame.arrangeHanPieces(new OuterElephantStrategy());
        janggiGame.arrangeChoPieces(new OuterElephantStrategy());

        while (true) {
            outputView.printBoard(janggiGame.getPieces());
            try {
                Dynasty currentDynasty = dynasties[turn % 2];

                List<Dot> movement = inputView.readPieceMovement(currentDynasty);

                janggiGame.processTurn(currentDynasty, movement.getFirst(), movement.getLast());

                turn++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
