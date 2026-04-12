package janggi.repository;

import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.SnapshotBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameRepository {

    private final GameStateDao gameStateDao;
    private final GamePieceDao gamePieceDao;

    public GameRepository(GameStateDao gameStateDao, GamePieceDao gamePieceDao) {
        this.gameStateDao = gameStateDao;
        this.gamePieceDao = gamePieceDao;
    }

    public List<Long> findAllIds(Connection connection) {
        return gameStateDao.findAllIds(connection);
    }

    public Optional<Game> findById(Connection connection, long gameId) {
        Optional<String> currentTurn = gameStateDao.findCurrentTurn(connection, gameId);
        if (currentTurn.isEmpty()) {
            return Optional.empty();
        }

        Map<Position, Piece> boardSnapshot = toBoardSnapshot(gamePieceDao.findByGameId(connection, gameId));
        Board board = new Board(new SnapshotBoardInitializer(boardSnapshot));
        return Optional.of(Game.restore(board, Camp.valueOf(currentTurn.orElseThrow())));
    }

    public long create(Connection connection, Game game) {
        long gameId = gameStateDao.create(connection, game.currentTurn().name());

        gamePieceDao.save(
                connection,
                gameId,
                toStoredGamePieces(game.boardSnapshot())
        );
        return gameId;
    }

    public void update(Connection connection, long gameId, Game game) {
        gameStateDao.update(connection, gameId, game.currentTurn().name());
        gamePieceDao.save(
                connection,
                gameId,
                toStoredGamePieces(game.boardSnapshot())
        );
    }

    public void deleteById(Connection connection, long gameId) {
        gamePieceDao.delete(connection, gameId);
        gameStateDao.delete(connection, gameId);
    }

    private Map<Position, Piece> toBoardSnapshot(List<StoredGamePiece> storedGamePieces) {
        Map<Position, Piece> boardSnapshot = new HashMap<>();

        for (StoredGamePiece storedGamePiece : storedGamePieces) {
            Position position = new Position(storedGamePiece.rowPosition(), storedGamePiece.columnPosition());
            Piece piece = new Piece(
                    Camp.valueOf(storedGamePiece.camp()),
                    PieceType.valueOf(storedGamePiece.pieceType())
            );
            boardSnapshot.put(position, piece);
        }

        return boardSnapshot;
    }

    private List<StoredGamePiece> toStoredGamePieces(Map<Position, Piece> boardSnapshot) {
        List<StoredGamePiece> storedGamePieces = new ArrayList<>();

        for (Map.Entry<Position, Piece> entry : boardSnapshot.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            storedGamePieces.add(new StoredGamePiece(
                    position.row(),
                    position.column(),
                    piece.getPieceType().name(),
                    piece.getCamp().name()
            ));
        }

        return storedGamePieces;
    }
}
