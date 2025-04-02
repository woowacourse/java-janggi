package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.MysqlConnection;
import janggi.dao.PieceDao;
import janggi.domain.game.Board;
import janggi.domain.game.Game;
import janggi.domain.game.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class GameService {

    private final MysqlConnection mysqlConnection = new MysqlConnection();
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public GameService(final GameDao gameDao, final PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public Game loadGameByGameId(final int gameId) {
        return executeWithConnection((connection -> {
            GameDto gameDto = gameDao.findGameById(connection, gameId);
            Team turn = Team.valueOf(gameDto.turn());
            List<PieceDto> pieceDtos = pieceDao.findPiecesByGameId(connection, gameId);
            Board board = createBoardFrom(pieceDtos);
            return new Game(turn, board);
        }));
    }

    public List<GameDto> getAllGames() {
        return executeWithConnection(gameDao::findAllGames);
    }

    public void saveGame(final Game game) {
        executeInTransaction(connection -> {
            int gameId = gameDao.addGame(connection, game.getTurn());
            pieceDao.addPieces(connection, gameId, toPieceDtos(game));
        });
    }

    public void updateGame(final int gameId, final Game game) {
        executeInTransaction(connection -> {
            gameDao.updateGameById(connection, gameId, game.getTurn());
            pieceDao.deletePiecesByGameId(connection, gameId);
            pieceDao.addPieces(connection, gameId, toPieceDtos(game));
        });
    }

    public void deleteGame(final int gameId) {
        executeInTransaction(connection -> {
            pieceDao.deletePiecesByGameId(connection, gameId);
            gameDao.deleteGameById(connection, gameId);
        });
    }

    private <T> T executeWithConnection(Function<Connection, T> block) {
        try (Connection connection = mysqlConnection.getConnection()) {
            return block.apply(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    private interface TransactionBlock {
        void execute(Connection connection) throws SQLException;
    }

    private void executeInTransaction(final TransactionBlock transactionBlock) {
        try (Connection connection = mysqlConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                transactionBlock.execute(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Board createBoardFrom(final List<PieceDto> pieceDtos) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceDto pieceDto : pieceDtos) {
            Position position = createPositionFrom(pieceDto);
            Piece piece = createPieceFrom(pieceDto);
            pieces.put(position, piece);
        }
        return new Board(pieces);
    }

    private Position createPositionFrom(final PieceDto pieceDto) {
        int columnValue = pieceDto.colNum();
        int rowValue = pieceDto.rowNum();
        return new Position(Column.of(columnValue), Row.of(rowValue));
    }

    private Piece createPieceFrom(final PieceDto pieceDto) {
        Team pieceTeam = Team.valueOf(pieceDto.team());
        Type type = Type.valueOf(pieceDto.pieceType());
        return type.getConstructor().apply(pieceTeam);
    }

    private List<PieceDto> toPieceDtos(Game game) {
        return allPositions().stream()
                .filter(game::hasPieceAt)
                .map(position -> createPieceDto(game, position))
                .toList();
    }

    private List<Position> allPositions() {
        return Arrays.stream(Column.values())
                .flatMap(column -> Arrays.stream(Row.values())
                        .map(row -> new Position(column, row)))
                .toList();
    }

    private PieceDto createPieceDto(final Game game, final Position position) {
        Piece piece = game.getPieceAt(position);
        return new PieceDto(piece.type().name(),
                piece.team().name(),
                position.column().getValue(),
                position.row().getValue());
    }
}
