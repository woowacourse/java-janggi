package janggi.application;

import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.game.Game;
import janggi.domain.game.RoomName;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.infra.dao.GameDAO;
import janggi.infra.dao.PiecePositionDAO;
import janggi.infra.entity.GameEntity;
import janggi.infra.entity.PiecePositionEntity;
import janggi.infra.transaction.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameService {

    private final GameDAO gameDAO;
    private final PiecePositionDAO piecePositionDAO;
    private final TransactionTemplate transactionTemplate;

    public GameService(GameDAO gameDAO, PiecePositionDAO piecePositionDAO, TransactionTemplate transactionTemplate) {
        this.gameDAO = gameDAO;
        this.piecePositionDAO = piecePositionDAO;
        this.transactionTemplate = transactionTemplate;
    }

    public Game createGame(BoardDesignPolicy boardDesignPolicy, String roomName, LocalDateTime lastPlayedAt) {
        Game game = Game.initGame(boardDesignPolicy, roomName, lastPlayedAt);
        transactionTemplate.executeWithoutResult(() -> {
            GameEntity gameEntity = saveGameRoom(roomName, lastPlayedAt, game);
            savePiecePositions(game, gameEntity);
        });
        return game;
    }

    private GameEntity saveGameRoom(String roomName, LocalDateTime lastPlayedAt, Game game) {
        GameEntity gameEntity = new GameEntity(new RoomName(roomName), game.currentTurn(), lastPlayedAt);
        Long gameRoomId = gameDAO.save(gameEntity);
        gameEntity.bindId(gameRoomId);
        return gameEntity;
    }

    private void savePiecePositions(Game game, GameEntity gameEntity) {
        Map<Position, Piece> board = game.boardMap();
        List<PiecePositionEntity> piecePositionEntities = new ArrayList<>();
        for (Position position : board.keySet()) {
            Piece piece = board.get(position);
            piecePositionEntities.add(
                    new PiecePositionEntity(position, piece.pieceType(), piece.dynasty(), gameEntity));
        }
        piecePositionDAO.saveAll(piecePositionEntities);
    }
}
