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
            savePiecePositions(game, gameEntity);
            return gameEntity.id();
        });
        return new GameDto(gameId, game);
    }

    private GameEntity saveGame(Game game) {
        GameEntity gameEntity = new GameEntity(game.roomName(), game.currentTurn(), game.lastPlayedAt());
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

    public List<GameRoomDto> getRecentlyPlayedGames() {
        return gameDAO.findAllOrderByLastPlayedAtDESC().stream()
                .map(gameEntity -> new GameRoomDto(gameEntity.id(), gameEntity.roomName().roomName()))
                .toList();
    }


    public GameDto loadGame(Long gameId) {

        List<PiecePositionEntity> piecePositionEntities = piecePositionDAO.findAllPiecesByGameId(gameId);
        validateGameIsNotExist(piecePositionEntities);

        GameEntity gameEntity = getGameEntity(piecePositionEntities);
        Map<Position, Piece> boardMap = getBoardMap(piecePositionEntities);

        return new GameDto(gameId, Game.loadGame(
                Board.of(boardMap), gameEntity.roomName(), new CurrentTurn(gameEntity.currentTurn()), gameEntity.lastPlayedAt()));
    }

    private static void validateGameIsNotExist(List<PiecePositionEntity> piecePositionEntities) {
        if(piecePositionEntities.isEmpty()) {
            throw new DomainException("게임이 존재하지 않습니다.");
        }
    }

    private static GameEntity getGameEntity(List<PiecePositionEntity> piecePositionEntities) {
        GameEntity gameEntity = piecePositionEntities.getFirst().gameRoomEntity();
        if(gameEntity == null) {
            throw new IllegalStateException();
        }
        return gameEntity;
    }

    private static Map<Position, Piece> getBoardMap(List<PiecePositionEntity> piecePositionEntities) {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (PiecePositionEntity piecePositionEntity : piecePositionEntities) {
            boardMap.put(piecePositionEntity.position(),
                    new Piece(piecePositionEntity.dynasty(), piecePositionEntity.pieceType()));
        }
        return boardMap;
    }


    public void movePiece(Long gameId, Position from, Position to, LocalDateTime playedAt) {
        transactionTemplate.executeWithoutResult(() -> {
            Game game = loadGame(gameId).game();
            game.movePiece(from, to, playedAt);

            gameDAO.updateCurrentTurnAndLastPlayedAt(new GameEntity(gameId, game.roomName(), game.currentTurn(), game.lastPlayedAt()));
            piecePositionDAO.deleteByGameIdAndPosition(gameId, to);
            piecePositionDAO.updatePosition(gameId, from, to);
        });
    }
}
