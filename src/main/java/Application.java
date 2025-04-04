import db.JanggiGameRepository;
import db.PositionRepository;
import db.connector.Connector;
import db.connector.MySqlConnector;
import db.mysql.JanggiGameMysqlRepository;
import db.mysql.PositionMysqlRepository;
import domain.Board;
import domain.BoardFactory;
import domain.GameState;
import domain.Team;
import domain.position.Position;
import java.util.List;

public class Application {

    public static void main(final String[] args) {
        final PositionMysqlRepository positionRepository = new PositionMysqlRepository();
        final JanggiGameMysqlRepository gameRepository = new JanggiGameMysqlRepository();
        final Connector mySqlConnector = new MySqlConnector();
        final GameState gameState = gameRepository.getGameState(mySqlConnector.getConnection());
        if (gameState.isRunning()) {
            final JanggiGame janggiGame = new JanggiGame(
                    new Board(positionRepository.getPositions(mySqlConnector.getConnection())),
                    gameRepository.getTurn(mySqlConnector.getConnection()));
            processGame(janggiGame, positionRepository, gameRepository, mySqlConnector);
            return;
        }
        gameRepository.updateGameState(mySqlConnector.getConnection(), GameState.RUNNING);
        final List<Position> positions = BoardFactory.create();
        positions.forEach(p -> positionRepository.savePosition(mySqlConnector.getConnection(), p));
        final JanggiGame janggiGame = new JanggiGame(new Board(positions), Team.GREEN);
        processGame(janggiGame, positionRepository, gameRepository, mySqlConnector);
    }

    private static void processGame(final JanggiGame janggiGame,
                                    final PositionRepository positionRepository,
                                    final JanggiGameRepository janggiGameRepository,
                                    final Connector mySqlConnector) {
        janggiGame.start();
        janggiGameRepository.updateGameState(mySqlConnector.getConnection(), GameState.END);
        janggiGameRepository.changeTurn(mySqlConnector.getConnection(), Team.GREEN);
        positionRepository.deleteAllPosition(mySqlConnector.getConnection());
    }
}
