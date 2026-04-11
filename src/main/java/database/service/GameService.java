package database.service;

import database.dao.GameDao;
import database.dao.PieceDao;
import database.dto.GameDto;
import database.dto.PieceDto;
import database.jdbc.DatabaseConnector;
import domain.board.Board;
import domain.game.Team;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.PieceDefinition;
import domain.position.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameService {
    private final DatabaseConnector connector;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public GameService(DatabaseConnector connector, GameDao gameDao, PieceDao pieceDao) {
        this.connector = connector;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public Optional<GameDto> findLatestGame() {
        return gameDao.findLatestPlaying();
    }

    public int startNewGame(Team initialTurn, Map<Position, Piece> pieces) {
        int gameId = gameDao.createGame(initialTurn);
        pieceDao.saveAll(gameId, toPieceDtos(pieces));
        return gameId;
    }

    public Map<Position, Piece> loadPieces(int gameId) {
        return toPiecesMap(pieceDao.findAll(gameId));
    }

    private List<PieceDto> toPieceDtos(Map<Position, Piece> pieces) {
        return pieces.entrySet().stream()
                .map(e -> new PieceDto(
                        e.getValue().getType().name(),
                        e.getValue().getTeam().name(),
                        e.getKey().getRow(),
                        e.getKey().getColumn()
                ))
                .toList();
    }

    private Map<Position, Piece> toPiecesMap(List<PieceDto> dtos) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceDto dto : dtos) {
            Position position = new Position(dto.rowIdx(), dto.colIdx());
            Piece piece = PieceDefinition.valueOf(dto.type()).createPiece(Team.valueOf(dto.team()));
            pieces.put(position, piece);
        }
        return pieces;
    }

    public Turn executeMove(int gameId, Board board, Turn turn, Position src, Position dest) {
        boolean isCapture = board.hasPieceAt(dest);
        board.move(src, dest, turn.current());
        Turn next = turn.next();
        saveMove(gameId, src, dest, isCapture, next.current());
        return next;
    }

    private void saveMove(int gameId, Position src, Position dest, boolean isCapture, Team nextTurn) {
        try (Connection connection = connector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                if (isCapture) {
                    pieceDao.delete(connection, gameId, dest);
                }
                pieceDao.updatePosition(connection, gameId, src, dest);
                gameDao.updateTurn(connection, gameId, nextTurn);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("이동 저장에 실패했습니다.", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("이동 저장에 실패했습니다.", e);
        }
    }

    public void deleteGame(int gameId) {
        gameDao.deleteById(gameId);
    }
}
