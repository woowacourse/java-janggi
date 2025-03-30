import dao.DaoConfiguration;
import dao.GameDao;
import dao.PieceDao;
import dao.ProdDaoConfiguration;
import java.util.Optional;
import java.util.function.Supplier;
import model.JanggiGame;
import model.piece.Piece;
import model.position.Position;
import model.Team;
import model.position.Score;
import view.InputView;
import view.OutputView;

public class Application {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final DaoConfiguration daoConfiguration = new ProdDaoConfiguration();
    private static final GameDao gameDao = new GameDao(daoConfiguration);
    private static final PieceDao pieceDao = new PieceDao(daoConfiguration);
    private static final JanggiGame janggiGame = JanggiGame.initPiecesFrom(gameDao, pieceDao);

    public static void main(String[] args) {
        outputView.printJanggiStart();
        while (true) {
            outputView.showCurrentPositionOfPieces(janggiGame.getPieces());
            Team currentTurn = janggiGame.getCurrentTurn();
            outputView.printCurrentTurnOfTeam(currentTurn);
            Optional<Position> departureOfNullable = createDeparture();
            if (departureOfNullable.isEmpty()) {
                janggiGame.removeGameInfo();
                break;
            }
            Position departure = departureOfNullable.get();
            createArrivalAndMove(departure);
            if (janggiGame.isEnd()) {
                janggiGame.removeGameInfo();
                outputView.printGeneralDie(currentTurn);
                break;
            }
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

    private static Optional<Position> createDeparture() {
        return retryOnInvalidInput(() -> {
            String choiceDeparture = inputView.choiceDeparture();
            if (choiceDeparture.equals("종료")) {
                Score score = janggiGame.showGameResult();
                outputView.printGameResult(score);
                return Optional.empty();
            }
            return Optional.ofNullable(janggiGame.createPositionAndCheckTurn(choiceDeparture));
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
