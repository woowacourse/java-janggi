import db.DataSourceFactory;
import db.DatabaseConnector;
import db.GameStateDao;
import db.PositionDao;
import db.TurnDao;
import domain.Board;
import domain.BoardFactory;
import domain.GameState;
import domain.Team;
import domain.position.Position;
import java.util.List;

public class Application {

    public static void main(final String[] args) {
        final DatabaseConnector databaseConnector = new DatabaseConnector(DataSourceFactory.getDataSource());
        final PositionDao positionDao = new PositionDao(databaseConnector);
        final TurnDao turnDao = new TurnDao(databaseConnector);
        final GameStateDao gameStateDao = new GameStateDao(databaseConnector);
        final GameState gameState = gameStateDao.getGameState();
        if (gameState.isRunning()) {
            final JanggiGame janggiGame = new JanggiGame(new Board(positionDao.getPositions()), turnDao.getTurn());
            janggiGame.start(positionDao, turnDao);
            gameStateDao.updateGameState(GameState.END);
            turnDao.changeTurn(Team.GREEN);
            positionDao.deleteAllPosition();
            return;
        }
        gameStateDao.updateGameState(GameState.RUNNING);
        final List<Position> positions = BoardFactory.create();
        positions.forEach(positionDao::savePosition);
        final JanggiGame janggiGame = new JanggiGame(new Board(positions), Team.GREEN);
        janggiGame.start(positionDao, turnDao);
        gameStateDao.updateGameState(GameState.END);
        turnDao.changeTurn(Team.GREEN);
        positionDao.deleteAllPosition();
    }
}
