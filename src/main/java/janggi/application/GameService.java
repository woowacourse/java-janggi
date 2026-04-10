package janggi.application;

import janggi.application.dto.GameDto;
import janggi.application.dto.GameRoomDto;
import janggi.domain.exception.DomainException;
import janggi.domain.board.Board;
import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.game.CurrentTurn;
import janggi.domain.game.Game;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.infra.dao.GameDAO;
import janggi.infra.dao.PiecePositionDAO;
import janggi.infra.entity.GameEntity;
import janggi.infra.entity.PiecePositionEntity;
import janggi.infra.transaction.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    public GameDto createGame(BoardDesignPolicy boardDesignPolicy, String roomName, LocalDateTime lastPlayedAt) {
        Game game = Game.initGame(boardDesignPolicy, roomName, lastPlayedAt);
        Long gameId = transactionTemplate.execute(() -> {
            GameEntity gameEntity = saveGame(game);
            Long createdGameId = gameEntity.id();
            savePiecePositions(game.boardMap(), createdGameId);
            return createdGameId;
        });
        return new GameDto(gameId, game);
    }

    private GameEntity saveGame(Game game) {
        GameEntity gameEntity = new GameEntity(game.roomName(), game.currentTurn(), game.lastPlayedAt());
        Long gameRoomId = gameDAO.save(gameEntity);
        gameEntity.bindId(gameRoomId);
        return gameEntity;
    }

    private void savePiecePositions(Map<Position, Piece> board, Long gameId) {
        List<PiecePositionEntity> piecePositionEntities = new ArrayList<>();
        for (Position position : board.keySet()) {
            Piece piece = board.get(position);
            piecePositionEntities.add(
                    new PiecePositionEntity(position, piece.pieceType(), piece.dynasty(), gameId));
        }
        piecePositionDAO.saveAll(piecePositionEntities);
    }

    public List<GameRoomDto> getRecentlyPlayedGames() {
        return gameDAO.findAllOrderByLastPlayedAtDesc().stream()
                .map(gameEntity -> new GameRoomDto(gameEntity.id(), gameEntity.roomName().roomName()))
                .toList();
    }


    public GameDto loadGame(Long gameId) {
        GameEntity gameEntity = gameDAO.findById(gameId)
                .orElseThrow(() -> new DomainException("존재하지 않는 게임입니다."));

        List<PiecePositionEntity> piecePositionEntities = piecePositionDAO.findAllPiecesByGameId(gameId);

        Map<Position, Piece> boardMap = getBoardMap(piecePositionEntities);

        return new GameDto(gameId, Game.loadGame(
                Board.of(boardMap), gameEntity.roomName(), new CurrentTurn(gameEntity.currentTurn()), gameEntity.lastPlayedAt()));
    }

    private static Map<Position, Piece> getBoardMap(List<PiecePositionEntity> piecePositionEntities) {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (PiecePositionEntity piecePositionEntity : piecePositionEntities) {
            boardMap.put(piecePositionEntity.position(),
                    new Piece(piecePositionEntity.dynasty(), piecePositionEntity.pieceType()));
        }
        return boardMap;
    }


    public GameDto movePiece(Long gameId, Position from, Position to, LocalDateTime playedAt) {
        return transactionTemplate.execute(() -> {
            Game game = loadGame(gameId).game();
            game.movePiece(from, to, playedAt);

            gameDAO.updateCurrentTurnAndLastPlayedAt(new GameEntity(gameId, game.roomName(), game.currentTurn(), game.lastPlayedAt()));
            piecePositionDAO.deleteByGameIdAndPosition(gameId, to);
            piecePositionDAO.updatePosition(gameId, from, to);

            return new GameDto(gameId, game);
        });
    }
}
