package repository;

import domain.Game;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameRepository {
    private final DBConnection dbConnection;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public GameRepository(DBConnection dbConnection, GameDao gameDao, PieceDao pieceDao) {
        this.dbConnection = dbConnection;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public long create(Game game) {
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long gameId = gameDao.create(connection, game.getTurn().name());
                pieceDao.saveAll(connection, gameId, toPieceRows(game.getPieces()));
                connection.commit();
                return gameId;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PieceRow> toPieceRows(Map<Position, Piece> pieces) {
        return pieces.entrySet().stream()
                .map(e -> new PieceRow(
                        e.getKey().x(),
                        e.getKey().y(),
                        e.getValue().getPieceType().name(),
                        e.getValue().getTeam().name()
                ))
                .toList();
    }

    private Map<Position, Piece> toPieces(List<PieceRow> rows) {
        return rows.stream()
                .collect(Collectors.toMap(
                        r -> new Position(r.x(), r.y()),
                        r -> BoardFactory.createPiece(PieceType.valueOf(r.pieceType()), Team.valueOf(r.team()))
                ));
    }

    public void save(long gameId, Game game) {
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                gameDao.updateTurn(connection, gameId, game.getTurn().name());
                pieceDao.saveAll(connection, gameId, toPieceRows(game.getPieces()));
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Game findById(long gameId) {
        try (Connection connection = dbConnection.getConnection()) {
            Team turn = Team.valueOf(gameDao.findTurn(connection, gameId));
            Map<Position, Piece> pieces = toPieces(pieceDao.findByGameId(connection, gameId));
            return new Game(new Board(pieces), turn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Long, String> findAll() {
        try (Connection connection = dbConnection.getConnection()) {
            return gameDao.findAll(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
