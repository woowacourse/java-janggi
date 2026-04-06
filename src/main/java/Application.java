import board.SangSetupType;
import core.JanggiGame;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.jdbc.ProductionConnectionManager;
import db.jdbc.DatabaseMigrator;
import db.jdbc.JdbcBoardPieceDao;
import db.jdbc.JdbcGameDao;
import db.model.GameEntity;
import db.repository.JanggiGameRepository;
import db.repository.JdbcJanggiGameRepository;
import java.util.List;
import pieces.Side;
import position.Position;
import util.Retry;
import view.DisplayBoard;
import view.JanggiView;

public class Application {

    public static void main(String[] args) {
        ProductionConnectionManager connectionManager = getConnectionManager();
        JanggiGameRepository repository = getRepository(connectionManager);

        JanggiView view = new JanggiView();

        Application.withRepositoryGame(view, repository).run();
    }

    private static JanggiGameRepository getRepository(ProductionConnectionManager connectionManager) {
        GameDao gameDao = new JdbcGameDao(connectionManager);
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao(connectionManager);
        return new JdbcJanggiGameRepository(gameDao, boardPieceDao);
    }

    private static ProductionConnectionManager getConnectionManager() {
        ProductionConnectionManager connectionManager = new ProductionConnectionManager();
        DatabaseMigrator databaseMigrator = new DatabaseMigrator(connectionManager);
        databaseMigrator.initialize();
        return connectionManager;
    }

    private static JanggiGame createNewGame(final JanggiView view) {
        SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        return JanggiGame.of(choSangSetupType, hanSangSetupType);
    }

    private final Long gameId;
    private JanggiGame game;
    private final JanggiView view;
    private final JanggiGameRepository repository;

    public Application(Long gameId, JanggiGame game, JanggiView view, JanggiGameRepository repository) {
        this.gameId = gameId;
        this.game = game;
        this.view = view;
        this.repository = repository;
    }

    public static Application withRepositoryGame(JanggiView view, JanggiGameRepository repository) {
        List<GameEntity> savedGames = repository.findTop10GameRoomsOrderByCreatedAtAsc();

        if (savedGames.isEmpty()) {
            JanggiGame game = createNewGame(view);
            Long gameId = repository.save(game);
            return new Application(gameId, game, view, repository);
        }

        Long selectedGameId = view.askGameId(savedGames);
        if (selectedGameId == 0) {
            JanggiGame game = createNewGame(view);
            Long gameId = repository.save(game);
            return new Application(gameId, game, view, repository);
        }
        JanggiGame game = repository.findById(selectedGameId)
            .orElseThrow(() -> new IllegalArgumentException("선택한 게임이 존재하지 않습니다."));

        return new Application(selectedGameId, game, view, repository);
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

            repository.updatePiecePosition(gameId, departure, destination);
            repository.updateGameState(gameId, updatedGame.getTurn(), updatedGame.getStatus());

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
