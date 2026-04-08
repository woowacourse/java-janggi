package janggi.service;

import janggi.config.DatabaseManager;
import janggi.domain.board.Board;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.game.GameState;
import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import janggi.entity.TurnEntity;
import janggi.repository.game.GameRepository;
import janggi.repository.movement.MovementRepository;
import janggi.repository.piece.PieceRepository;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;
    private final MovementRepository movementRepository;

    public JanggiService(
            GameRepository gameRepository,
            PieceRepository pieceRepository,
            MovementRepository movementRepository
    ) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
        this.movementRepository = movementRepository;
    }

    public Long makeGame(Map<Dynasty, HorseElephantPosition> horseElephantPositions) {
        Game game = Game.initGame(horseElephantPositions);

        return DatabaseManager.withTransaction(connection -> {
            Long gameId = gameRepository.save(connection, TurnEntity.toEntity(game));
            pieceRepository.saveAll(connection, gameId, PieceEntity.toEntities(game.pieces()));
            return gameId;
        });
    }

    public Game findGame(Long gameId) {
        TurnEntity currentTurn = gameRepository.findByCurrentTurnById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("gameId가 %s인 게임이 존재하지 않습니다.", gameId)));
        List<PieceEntity> pieces = pieceRepository.findAllByGameId(gameId);

        return Game.restore(
                Board.restore(PieceEntity.toDomains(pieces)),
                TurnEntity.toDomain(currentTurn)
        );
    }

    // TODO: turn과 state 합쳐서 game이라고 말하기
    public void movePiece(Long gameId, Position from, Position to) {
        Game game = findGame(gameId);
        GameState gameState = game.movePiece(from, to);

        DatabaseManager.withTransaction(connection -> {
            pieceRepository.updatePiece(connection, gameId, from, to);
            gameRepository.updateTurn(connection, gameId, TurnEntity.toEntity(game));
            gameRepository.updateState(connection, gameId, gameState);
            return null;
        });
    }

    public void saveMovement(Long gameId, Position from, Position to) {
        movementRepository.save(gameId, from, to);
    }

    public List<Long> findPlayableGameIds() {
        List<Long> gameIds = gameRepository.findAllByState(GameState.PLAYING);
        if (gameIds.isEmpty()) {
            throw new IllegalStateException("진행할 수 있는 게임이 없습니다.");
        }
        return gameIds;
    }

    public Long validatePlayableGameId(Long gameId) {
        List<Long> gameIds = gameRepository.findAllByState(GameState.PLAYING);
        if (!gameIds.contains(gameId)) {
            throw new IllegalArgumentException(String.format("gameId가 %s인 진행 중 게임이 존재하지 않습니다.", gameId));
        }
        return gameId;
    }

    public boolean isFinishedGame(Long gameId) {
        GameState gameState = gameRepository.findGameStateById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("gameId가 %s인 게임이 존재하지 않습니다.", gameId)));
        return gameState.isFinished();
    }

    public Dynasty findWinner(Long gameId) {
        GameState gameState = gameRepository.findGameStateById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("gameId가 %s인 게임이 존재하지 않습니다.", gameId)));
        return gameState.winner();
    }

}
