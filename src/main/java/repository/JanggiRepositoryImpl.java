package repository;

import model.coordinate.Position;
import model.game.GameStatus;
import model.game.Team;
import model.game.dto.GameDto;
import model.game.dto.PieceDto;
import model.piece.Piece;
import model.piece.PieceType;
import repository.column.GameColumn;
import repository.column.PieceColumn;
import repository.command.MoveCommand;
import repository.mapper.PieceDtoMapper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiRepositoryImpl implements JanggiRepository {

    private final JdbcTemplate jdbcTemplate;

    public JanggiRepositoryImpl(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public long saveGame(Team turn, Map<Position, Piece> board) {
        return jdbcTemplate.executeInTransaction(
                () -> {
                    long gameId = jdbcTemplate.executeAndReturnKey(
                            "INSERT INTO game(" + GameColumn.TURN + ") VALUES (?)",
                            stmt -> stmt.setString(1, turn.name())
                    );
                    insertPieceEntries(gameId, board);
                    return gameId;
                });
    }

    private void insertPieceEntries(long gameId, Map<Position, Piece> board) {
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            insertSinglePieceEntry(gameId, entry.getKey(), entry.getValue());
        }
    }

    private void insertSinglePieceEntry(long gameId, Position position, Piece piece) {
        jdbcTemplate.executeAndReturnKey(
                "INSERT INTO piece(" + PieceColumn.GAME_ID + ", " + PieceColumn.PIECE_TYPE + ", " + PieceColumn.TEAM + ", " + PieceColumn.ROW_IDX + ", " + PieceColumn.COL_IDX + ") values (?, ?, ?, ?, ?)",
                stmt -> {
                    stmt.setLong(1, gameId);
                    stmt.setString(2, piece.getType().name());
                    stmt.setString(3, piece.getTeam().name());
                    stmt.setInt(4, position.row());
                    stmt.setInt(5, position.col());
                }
        );
    }

    @Override
    public void updateGame(long gameId, MoveCommand moveCommand) {
        Position source = moveCommand.source();
        Position destination = moveCommand.destination();
        Team currentTurn = moveCommand.turn();

        jdbcTemplate.executeInTransaction(() -> {
            deletePieceOnDestination(gameId, moveCommand.destination());
            updatePositionOfPiece(gameId, destination, source);
            updateCurrentTurn(gameId, currentTurn);
        });
    }

    private void updateCurrentTurn(long gameId, Team currentTurn) {
        jdbcTemplate.execute(
                "UPDATE game SET " + GameColumn.TURN + " = ? WHERE " + GameColumn.GAME_ID + " = ?",
                stmt -> {
                    stmt.setString(1, currentTurn.name());
                    stmt.setLong(2, gameId);
                }
        );
    }

    private void updatePositionOfPiece(long gameId, Position destination, Position source) {
        jdbcTemplate.execute(
                "UPDATE piece SET " + PieceColumn.ROW_IDX + " = ?, " + PieceColumn.COL_IDX + " = ? WHERE " + PieceColumn.GAME_ID + " = ? AND " + PieceColumn.ROW_IDX + " = ? AND " + PieceColumn.COL_IDX + " = ?",
                stmt -> {
                    stmt.setInt(1, destination.row());
                    stmt.setInt(2, destination.col());
                    stmt.setLong(3, gameId);
                    stmt.setInt(4, source.row());
                    stmt.setInt(5, source.col());
                }
        );
    }

    private void deletePieceOnDestination(long gameId, Position destination) {
        jdbcTemplate.execute(
                "DELETE FROM piece WHERE " + PieceColumn.GAME_ID + " = ? AND " + PieceColumn.ROW_IDX + " = ? AND " + PieceColumn.COL_IDX + " = ?",
                stmt -> {
                    stmt.setLong(1, gameId);
                    stmt.setInt(2, destination.row());
                    stmt.setInt(3, destination.col());
                }
        );
    }

    @Override
    public Optional<GameDto> findRecentGame() {
        return jdbcTemplate.queryForSingleObject(
                "SELECT " + GameColumn.GAME_ID + ", " + GameColumn.TURN + " FROM game " +
                        "WHERE " + GameColumn.STATUS + " = 'PLAYING' " +
                        "ORDER BY " + GameColumn.GAME_ID + " DESC " +
                        "LIMIT 1",
                rs -> new GameDto(rs.getLong(GameColumn.GAME_ID), rs.getString(GameColumn.TURN))
        );
    }

    @Override
    public Map<Position, Piece> findPiecesByGameId(long gameId) {
        List<PieceDto> pieceDaos = jdbcTemplate.query(
                "SELECT " + PieceColumn.PIECE_TYPE + ", " + PieceColumn.TEAM + ", " + PieceColumn.ROW_IDX + ", " + PieceColumn.COL_IDX + " " +
                        "FROM piece " +
                        "WHERE " + PieceColumn.GAME_ID + " = ?",
                stmt -> stmt.setLong(1, gameId),
                new PieceDtoMapper()
        );
        return createBoardMap(pieceDaos);
    }

    private Map<Position, Piece> createBoardMap(List<PieceDto> pieceDaos) {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (PieceDto pieceDao : pieceDaos) {
            Position position = new Position(pieceDao.rowIndex(), pieceDao.colIndex());
            Team currentTurn = Team.fromName(pieceDao.team());
            Piece piece = PieceType.fromName(pieceDao.pieceType()).createPiece(currentTurn);
            boardMap.put(position, piece);
        }
        return Map.copyOf(boardMap);
    }

    @Override
    public void updateCurrentGameStatus(Long gameId, GameStatus gameStatus) {
        jdbcTemplate.execute(
                "UPDATE game SET " + GameColumn.STATUS + " = ? WHERE " + GameColumn.GAME_ID + " = ?",
                stmt -> {
                    stmt.setString(1, gameStatus.name());
                    stmt.setLong(2, gameId);
                }
        );
    }
}
