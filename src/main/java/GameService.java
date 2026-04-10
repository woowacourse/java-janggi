import config.DatabaseConfig;
import domain.Board;
import domain.Game;
import domain.HorseElephantFormation;
import domain.Position;
import domain.Team;
import domain.piece.Piece;
import dto.MoveCommand;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import repository.GameRepository;
import repository.PieceRepository;
import strategy.InitializeStrategy;

public class GameService {
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;
    private Game game;

    public GameService(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public Game startNewGame(HorseElephantFormation choFormation, HorseElephantFormation hanFormation) {
        InitializeStrategy choStrategy = choFormation.createStrategy();
        InitializeStrategy hanStrategy = hanFormation.createStrategy();

        Board board = new Board(choStrategy, hanStrategy);
        game = new Game(board);

        Connection connection = DatabaseConfig.createConnection();

        try {
            connection.setAutoCommit(false);

            gameRepository.save(game, connection);
            pieceRepository.save(game, connection);

            connection.commit();
            return game;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    throw new RuntimeException(rollbackException);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
            throw new RuntimeException();
        }
    }

    public Game resumeGame() {
        try(Connection connection = DatabaseConfig.createConnection()) {
            Map<Position, Piece> pieces = pieceRepository.findByGameId(game.id(), connection);

            this.game = gameRepository.findLatest(connection, pieces);

            return game;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void playTurn(MoveCommand command) {
        Connection connection = DatabaseConfig.createConnection();
        try {
            connection.setAutoCommit(false);

            game.board().move(command.from(), command.to(), command.pieceType(), game.turn());

            game.changeTurn();

            gameRepository.updateGame(game,  connection);
            pieceRepository.deletePiece(game.id(), command.to(), connection);
            pieceRepository.updatePieces(game.id(), command.from(), command.to(), connection);

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    throw new RuntimeException(rollbackException);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
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
