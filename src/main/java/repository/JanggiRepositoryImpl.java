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
                            "INSERT INTO game(turn) VALUES (?)",
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
                "INSERT INTO piece(game_id, piece_type, team, row_idx, col_idx) VALUES (?, ?, ?, ?, ?)",
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
    public void updateGame(long gameId, MoveCommand moveCommand, GameStatus status) {
        Position source = moveCommand.source();
        Position destination = moveCommand.destination();
        Team currentTurn = moveCommand.turn();

        jdbcTemplate.executeInTransaction(() -> {
            deletePieceOnDestination(gameId, moveCommand.destination());
            updatePositionOfPiece(gameId, destination, source);
            updateCurrentTurn(gameId, currentTurn);
            updateCurrentGameStatus(gameId, status);
        });
    }

    private void updateCurrentTurn(long gameId, Team currentTurn) {
        jdbcTemplate.execute(
                "UPDATE game SET turn = ? WHERE game_id = ?",
                stmt -> {
                    stmt.setString(1, currentTurn.name());
                    stmt.setLong(2, gameId);
                }
        );
    }

    private void updatePositionOfPiece(long gameId, Position destination, Position source) {
        jdbcTemplate.execute(
                "UPDATE piece SET row_idx = ?, col_idx = ? WHERE game_id = ? AND row_idx = ? AND col_idx = ?",
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
                "DELETE FROM piece WHERE game_id = ? AND row_idx = ? AND col_idx = ?",
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
                "SELECT game_id, turn FROM game WHERE status = 'PLAYING' ORDER BY game_id DESC LIMIT 1",
                rs -> new GameDto(rs.getLong(GameColumn.GAME_ID), rs.getString(GameColumn.TURN))
        );
    }

    @Override
    public Map<Position, Piece> findPiecesByGameId(long gameId) {
        List<PieceDto> pieceDtos = jdbcTemplate.query(
                "SELECT piece_type, team, row_idx, col_idx FROM piece WHERE game_id = ?",
                stmt -> stmt.setLong(1, gameId),
                new PieceDtoMapper()
        );
        return createBoardMap(pieceDtos);
    }

    private Map<Position, Piece> createBoardMap(List<PieceDto> pieceDtos) {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (PieceDto pieceDto : pieceDtos) {
            Position position = new Position(pieceDto.rowIndex(), pieceDto.colIndex());
            Team currentTurn = Team.fromName(pieceDto.team());
            Piece piece = PieceType.fromName(pieceDto.pieceType()).createPiece(currentTurn);
            boardMap.put(position, piece);
        }
        return Map.copyOf(boardMap);
    }

    @Override
    public void updateCurrentGameStatus(Long gameId, GameStatus gameStatus) {
        jdbcTemplate.execute(
                "UPDATE game SET status = ? WHERE game_id = ?",
                stmt -> {
                    stmt.setString(1, gameStatus.name());
                    stmt.setLong(2, gameId);
                }
        );
    }
}
