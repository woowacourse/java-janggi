import game.Dot;
import game.JanggiGame;
import game.arrangement.OuterElephantStrategy;
import piece.Dynasty;
import view.InputView;
import view.OutputView;

import java.util.List;

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

            Dynasty currentDynasty = dynasties[turn % 2];

            List<Dot> movement = inputView.readPieceMovement(currentDynasty);

            janggiGame.movePiece(currentDynasty, movement.getFirst(), movement.getLast());

            turn++;
        }
    }

}
