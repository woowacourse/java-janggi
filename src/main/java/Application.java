import java.util.function.Supplier;
import model.JanggiGame;
import model.piece.Piece;
import model.position.Position;
import model.Team;
import view.InputView;
import view.OutputView;

public class Application {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final JanggiGame janggiGame = new JanggiGame();

    public static void main(String[] args) {
        outputView.printJanggiStart();
        while (true) {
            outputView.showCurrentPositionOfPieces(janggiGame.getPieces());
            Team currentTurn = janggiGame.getCurrentTurn();
            outputView.printCurrentTurnOfTeam(currentTurn);
            Position departure = createDeparture();
            createArrivalAndMove(departure);
        }
    }

    private static void createArrivalAndMove(Position departure) {
        retryOnInvalidInput(() -> {
            Piece pieceOfDeparture = janggiGame.findPieceBy(departure);
            String choiceArrival = inputView.choiceArrivalOf(pieceOfDeparture);
            Position arrival = janggiGame.createPositionFrom(choiceArrival);
            janggiGame.move(departure, arrival);
            return null;
        });
    }

    private static Position createDeparture() {
        return retryOnInvalidInput(() -> {
            String choiceDeparture = inputView.choiceDeparture();
            return janggiGame.createPositionAndCheckTurn(choiceDeparture);
        });
    }

    private static <T> T retryOnInvalidInput(Supplier<T> input) {
        while (true) {
            try {
                return input.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
