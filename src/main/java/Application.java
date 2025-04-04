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

    private static final PositionRepository positionRepository = new PositionMysqlRepository();
    private static final JanggiGameRepository janggiGameRepository = new JanggiGameMysqlRepository();
    private static final Connector mySqlConnector = new MySqlConnector();

    public static void main(final String[] args) {
        final GameState gameState = janggiGameRepository.getGameState(mySqlConnector.getConnection());

        if (gameState.isRunning()) {
            processGame(createNewJanggiGame());
            return;
        }
        processGame(loadSavedJanggiGame());
    }

    private static JanggiGame createNewJanggiGame() {
        return new JanggiGame(
                new Board(positionRepository.getPositions(mySqlConnector.getConnection())),
                janggiGameRepository.getTurn(mySqlConnector.getConnection())
        );
    }

    private static JanggiGame loadSavedJanggiGame() {
        janggiGameRepository.updateGameState(mySqlConnector.getConnection(), GameState.RUNNING);
        final List<Position> positions = BoardFactory.create();
        positions.forEach(position -> positionRepository.savePosition(mySqlConnector.getConnection(), position));
        return new JanggiGame(new Board(positions), Team.GREEN);
    }

    private static void processGame(final JanggiGame janggiGame) {
        janggiGame.start();
        janggiGameRepository.updateGameState(mySqlConnector.getConnection(), GameState.END);
        janggiGameRepository.changeTurn(mySqlConnector.getConnection(), Team.GREEN);
        positionRepository.deleteAllPosition(mySqlConnector.getConnection());
    }
}
