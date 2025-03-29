import db.JanggiDao;
import domain.Board;
import domain.BoardFactory;
import domain.GameState;
import domain.JanggiGame;
import domain.Team;
import domain.position.Position;
import java.util.List;

public class Application {

    public static void main(final String[] args) {
        final JanggiDao janggiDao = new JanggiDao();
        final GameState gameState = janggiDao.getGameState();
        if (gameState.isRunning()) {
            final JanggiGame janggiGame = new JanggiGame(new Board(janggiDao.getPositions()), janggiDao.getTurn());
            janggiGame.start();
            janggiDao.updateGameState(GameState.END);
            janggiDao.changeTurn(Team.GREEN);
            janggiDao.deleteAllPosition();
            return;
        }
        janggiDao.updateGameState(GameState.RUNNING);
        final List<Position> positions = BoardFactory.create();
        positions.forEach(janggiDao::savePosition);
        final JanggiGame janggiGame = new JanggiGame(new Board(positions), Team.GREEN);
        janggiGame.start();
        janggiDao.updateGameState(GameState.END);
        janggiDao.changeTurn(Team.GREEN);
        janggiDao.deleteAllPosition();
    }
}
