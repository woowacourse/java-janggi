import dao.DaoConfiguration;
import dao.GameDao;
import dao.PieceDao;
import dao.ProdDaoConfiguration;
import java.util.Optional;
import java.util.function.Supplier;
import model.JanggiGame;
import model.piece.Piece;
import model.position.Position;
import model.piece.Team;
import model.piece.Score;
import view.GameProgressView;
import view.GameResultView;
import view.JanggiPiecesView;

public class Application {

    private static final GameProgressView gameProgressView = new GameProgressView();
    private static final JanggiPiecesView janggiPiecesView = new JanggiPiecesView();
    private static final GameResultView gameResultView = new GameResultView();
    private static final DaoConfiguration daoConfiguration = new ProdDaoConfiguration();
    private static final PieceDao pieceDao = new PieceDao(daoConfiguration);
    private static final GameDao gameDao = new GameDao(daoConfiguration);
    private static final JanggiGame janggiGame = JanggiGame.initPiecesFrom(pieceDao.getAllPieces(), gameDao.getTurn());

    public static void main(String[] args) {
        pieceDao.addPieces(janggiGame.getPieces());
        gameDao.addTurn(janggiGame.getCurrentTurn());
        janggiPiecesView.printJanggiStart();
        while (true) {
            janggiPiecesView.showCurrentPositionOfPieces(janggiGame.getPieces());
            Team currentTurn = janggiGame.getCurrentTurn();
            gameProgressView.printCurrentTurnOfTeam(currentTurn);
            Optional<Position> departureOfNullable = createDeparture();
            if (departureOfNullable.isEmpty()) {
                removeGameInfo();
                break;
            }
            Position departure = departureOfNullable.get();
            createArrivalAndMove(departure);
            if (janggiGame.isEnd()) {
                removeGameInfo();
                gameResultView.printGeneralDie(currentTurn);
                break;
            }
        }
    }

    private static void removeGameInfo() {
        pieceDao.deletePieces();
        gameDao.deleteTurn();
    }

    private static void createArrivalAndMove(Position departure) {
        retryOnInvalidInput(() -> {
            Piece pieceOfDeparture = janggiGame.findPieceBy(departure);
            Position arrival = gameProgressView.choiceArrivalOf(pieceOfDeparture);
            move(departure, arrival);
            return null;
        });
    }

    private static void move(Position departure, Position arrival) {
        janggiGame.move(departure, arrival);
        pieceDao.deletePiece(arrival);
        pieceDao.updatePiece(departure, arrival);
        gameDao.updateTurn(janggiGame.getCurrentTurn());
    }

    private static Optional<Position> createDeparture() {
        return retryOnInvalidInput(() -> {
            Optional<Position> departure = gameProgressView.choiceDeparture();
            if (departure.isEmpty()) {
                Score score = janggiGame.showGameResult();
                gameResultView.printGameResult(score);
                return departure;
            }
            janggiGame.findPieceBy(departure.get()); // 해당 위치에 기물이 있는지 검증한다.
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
