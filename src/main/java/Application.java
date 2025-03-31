import config.JanggiConfig;
import java.util.Optional;
import java.util.function.Supplier;
import model.piece.Piece;
import model.position.Position;
import model.piece.Team;
import model.piece.Score;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final JanggiService janggiService = JanggiConfig.createJanggiService();

    public static void main(String[] args) {
        outputView.printJanggiStart();
        while (true) {
            outputView.showCurrentPositionOfPieces(janggiService.getPieces());
            Team currentTurn = janggiService.getCurrentTurn();
            outputView.printCurrentTurnOfTeam(currentTurn);
            Optional<Position> departureOfNullable = createDeparture();
            if (departureOfNullable.isEmpty()) {
                janggiService.removeGameInfo();
                break;
            }
            Position departure = departureOfNullable.get();
            createArrivalAndMove(departure);
            if (janggiService.isEnd()) {
                janggiService.removeGameInfo();
                outputView.printGeneralDie(currentTurn);
                break;
            }
        }
    }

    private static void createArrivalAndMove(Position departure) {
        retryOnInvalidInput(() -> {
            Piece pieceOfDeparture = janggiService.findPieceBy(departure);
            Position arrival = inputView.choiceArrivalOf(pieceOfDeparture);
            janggiService.move(departure, arrival);
            return null;
        });
    }

    private static Optional<Position> createDeparture() {
        return retryOnInvalidInput(() -> {
            Optional<Position> departure = inputView.choiceDeparture();
            if (departure.isEmpty()) {
                Score score = janggiService.showGameResult();
                outputView.printGameResult(score);
                return departure;
            }
            janggiService.findPieceBy(departure.get()); // 해당 위치에 기물이 있는지 검증한다.
            return departure;
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
