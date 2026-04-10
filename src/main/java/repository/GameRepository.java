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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public void save(long gameId, Game game, Position from, Position to) {
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Optional<Long> capturedId = pieceDao.findIdByPosition(connection, gameId, to.x(), to.y());
                if (capturedId.isPresent()) {
                    pieceDao.deleteById(connection, capturedId.get());
                }

                long movedId = pieceDao.findIdByPosition(connection, gameId, from.x(), from.y())
                        .orElseThrow(() -> new IllegalStateException("이동할 기물을 찾을 수 없습니다."));

                pieceDao.updatePosition(connection, movedId, to.x(), to.y());

                gameDao.updateTurn(connection, gameId, game.getTurn().name());
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
            GameRow gameRow = gameDao.findById(connection, gameId);
            Team turn = Team.valueOf(gameRow.turn());
            Map<Position, Piece> pieces = toPieces(pieceDao.findByGameId(connection, gameId));
            return new Game(new Board(pieces), turn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Long, String> findAll() {
        try (Connection connection = dbConnection.getConnection()) {
            Map<Long, String> result = new LinkedHashMap<>();
            for (GameRow row : gameDao.findAll(connection)) {
                result.put(row.gameId(), row.turn());
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
