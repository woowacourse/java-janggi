package janggi.service;

import janggi.config.DatabaseManager;
import janggi.domain.board.Board;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.CurrentTurn;
import janggi.domain.game.Game;
import janggi.domain.game.GameState;
import janggi.domain.position.Position;
import janggi.entity.GameEntity;
import janggi.entity.PieceEntity;
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
            Long gameId = gameRepository.save(
                    connection, GameEntity.toEntity(game.currentDynasty(), GameState.PLAYING));
            pieceRepository.saveAll(connection, gameId, PieceEntity.toEntities(game.pieces()));
            return gameId;
        });
    }

    public Game findGame(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("gameId가 %s인 게임이 존재하지 않습니다.", gameId)));
        List<PieceEntity> pieces = pieceRepository.findAllByGameId(gameId);

        return Game.restore(
                Board.restore(PieceEntity.toDomains(pieces)),
                CurrentTurn.from(gameEntity.currentTurn())
        );
    }

    public void movePiece(Long gameId, Position from, Position to) {
        Game game = findGame(gameId);
        PieceEntity capturedPiece = findCapturedPiece(game, to);
        GameState gameState = game.movePiece(from, to);

        DatabaseManager.withTransaction(connection -> {
            pieceRepository.update(connection, gameId, from, to);
            gameRepository.update(connection, gameId, GameEntity.toEntity(game.currentDynasty(), gameState));
            movementRepository.save(gameId, from, to, capturedPiece);
            return null;
        });
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
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("gameId가 %s인 게임이 존재하지 않습니다.", gameId)));
        return GameState.valueOf(gameEntity.gameState()).isFinished();
    }

    public Dynasty findWinner(Long gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("gameId가 %s인 게임이 존재하지 않습니다.", gameId)));
        return GameState.valueOf(gameEntity.gameState()).winner();
    }

    private PieceEntity findCapturedPiece(Game game, Position to) {
        return game.pieces().entrySet().stream()
                .filter(entry -> entry.getKey().equals(to))
                .findFirst()
                .map(entry -> PieceEntity.toEntity(entry.getKey(), entry.getValue()))
                .orElse(null);
    }

}
