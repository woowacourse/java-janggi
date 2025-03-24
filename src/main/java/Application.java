import model.JanggiGame;
import model.Piece;
import model.Position;
import view.InputView;
import view.OutputView;

public class Application {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final JanggiGame janggiGame = new JanggiGame();

    public static void main(String[] args) {
        outputView.printJanggiStart();
        while (true) {
            String currentPosition = janggiGame.showCurrentPositionOfPieces();
            outputView.printCurrentPosition(currentPosition);
            String choiceDeparture = inputView.choiceDeparture();
            Position departure = janggiGame.createPositionFrom(choiceDeparture);
            Piece pieceOfDeparture = janggiGame.findPieceBy(departure);
            String choiceArrival = inputView.choiceArrivalOf(pieceOfDeparture);
            Position arrival = janggiGame.createPositionFrom(choiceArrival);
            janggiGame.move(departure, arrival);
        }
    }
}
