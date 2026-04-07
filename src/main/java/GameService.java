import config.DatabaseConfig;
import domain.Board;
import domain.Game;
import domain.HorseElephantFormation;
import domain.Team;
import dto.MoveCommand;
import java.sql.Connection;
import java.sql.SQLException;
import repository.GameRepository;
import strategy.InitializeStrategy;

public class GameService {
    private final GameRepository gameRepository;
    private Game game;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game startNewGame(HorseElephantFormation choFormation, HorseElephantFormation hanFormation) {
        InitializeStrategy choStrategy = choFormation.createStrategy();
        InitializeStrategy hanStrategy = hanFormation.createStrategy();

        Board board = new Board(choStrategy, hanStrategy);
        game = new Game(board);

        try (Connection connection = DatabaseConfig.createConnection()) {
            connection.setAutoCommit(false);

            gameRepository.save(game, connection);

            connection.commit();
            return game;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Game resumeGame() {
        try(Connection connection = DatabaseConfig.createConnection()) {

            this.game = gameRepository.findLatest(connection);

            return game;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void playTurn(MoveCommand command) {
        try (Connection connection = DatabaseConfig.createConnection()) {
            connection.setAutoCommit(false);

            game.board().move(command.from(), command.to(), command.pieceType(), game.turn());

            game.changeTurn();

            gameRepository.update(game, command.from(), command.to(), connection);

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Board getBoard() {
        return game.board();
    }

    public Team getCurrentTeam() {
        return game.turn();
    }

    public boolean isGameOver() {
        return !game.board().canNextTurn();
    }
}
