import board.SangSetupType;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseInitializer;
import db.jdbc.JdbcBoardPieceDao;
import db.jdbc.JdbcGameDao;
import db.repository.JanggiGameRepository;
import db.repository.JdbcJanggiGameRepository;
import pieces.Side;
import position.Position;
import util.Retry;
import view.DisplayBoard;
import view.JanggiView;

public class Application {

    public static void main(String[] args) {
        ConnectionManager connectionManager = new ConnectionManager();
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionManager);
        databaseInitializer.initialize();

        JanggiView view = new JanggiView();

        GameDao gameDao = new JdbcGameDao(connectionManager);
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao(connectionManager);
        JanggiGameRepository repository = new JdbcJanggiGameRepository(gameDao, boardPieceDao);

        JanggiGame game = repository.findLatest()
            .orElseGet(() -> createNewGame(view));

        new Application(game, view, repository).run();
    }

    private static JanggiGame createNewGame(final JanggiView view) {
        SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        return JanggiGame.of(choSangSetupType, hanSangSetupType);
    }

    private JanggiGame game;
    private final JanggiView view;
    private final JanggiGameRepository repository;

    public Application(JanggiGame game, JanggiView view, JanggiGameRepository repository) {
        this.game = game;
        this.view = view;
        this.repository = repository;
    }

    public void run() {
        while (!game.isOver()) {
            moveUntilSuccess();
        }
        view.printGameIsOver(game.getWinnerSide());
    }

    private void moveUntilSuccess() {
        game = Retry.untilSuccess(() -> {
            printGameStatus();

            Position departure = view.askDeparture();
            Position destination = view.askDestination();
            JanggiGame updatedGame = game.move(departure, destination);
            repository.saveLatest(updatedGame);

            return updatedGame;
        });
    }

    private void printGameStatus() {
        view.printBoard(DisplayBoard.of(game.getBoard()));

        final Side turnSide = game.getTurnSide();
        final Side otherTurnSide = turnSide.other();

        view.printTurnSide(turnSide);
        view.printScore(turnSide, game.calculateScoreOf(turnSide));
        view.printScore(otherTurnSide, game.calculateScoreOf(otherTurnSide));
    }
}
