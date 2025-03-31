import config.JanggiConfig;
import java.util.Optional;
import java.util.function.Supplier;
import model.piece.Piece;
import model.position.Position;
import model.piece.Team;
import model.piece.Score;
import service.JanggiService;
import view.GameProgressView;
import view.GameResultView;
import view.JanggiPiecesView;

public class Application {

    private static final GameProgressView gameProgressView = new GameProgressView();
    private static final JanggiPiecesView janggiPiecesView = new JanggiPiecesView();
    private static final GameResultView gameResultView = new GameResultView();
    private static final JanggiService janggiService = JanggiConfig.createJanggiService();

    public static void main(String[] args) {
        janggiPiecesView.printJanggiStart();
        while (true) {
            janggiPiecesView.showCurrentPositionOfPieces(janggiService.getPieces());
            Team currentTurn = janggiService.getCurrentTurn();
            gameProgressView.printCurrentTurnOfTeam(currentTurn);
            Optional<Position> departureOfNullable = createDeparture();
            if (departureOfNullable.isEmpty()) {
                janggiService.removeGameInfo();
                break;
            }
            Position departure = departureOfNullable.get();
            createArrivalAndMove(departure);
            if (janggiService.isEnd()) {
                janggiService.removeGameInfo();
                gameResultView.printGeneralDie(currentTurn);
                break;
            }
        }
    }

    private static void createArrivalAndMove(Position departure) {
        retryOnInvalidInput(() -> {
            Piece pieceOfDeparture = janggiService.findPieceBy(departure);
            Position arrival = gameProgressView.choiceArrivalOf(pieceOfDeparture);
            janggiService.move(departure, arrival);
            return null;
        });
    }

    private static Optional<Position> createDeparture() {
        return retryOnInvalidInput(() -> {
            Optional<Position> departure = gameProgressView.choiceDeparture();
            if (departure.isEmpty()) {
                Score score = janggiService.showGameResult();
                gameResultView.printGameResult(score);
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
                gameProgressView.printErrorMessage(e.getMessage());
            }
        }
    }
}
