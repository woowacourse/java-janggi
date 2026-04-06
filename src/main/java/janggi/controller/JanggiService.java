package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import janggi.entity.TurnEntity;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;

    public JanggiService(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public Long makeGame(Map<Dynasty, HorseElephantPosition> horseElephantPositions) {
        Game game = Game.initGame(horseElephantPositions);

        Long gameId = gameRepository.save(TurnEntity.toEntity(game));
        pieceRepository.saveAll(gameId, PieceEntity.toEntities(game.pieces()));
        return gameId;
    }

    public Game findGame(Long gameId) {
        TurnEntity currentTurn = gameRepository.findByCurrentTurnById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("게임이 존재하지 않습니다."));
        List<PieceEntity> pieces = pieceRepository.findAllByGameId(gameId);

        return Game.restore(Board.restore(PieceEntity.toDomains(pieces)), TurnEntity.toDomain(currentTurn));
    }

    public void movePiece(Long gameId, Position from, Position to) {
        Game game = findGame(gameId);
        game.movePiece(from, to);

        pieceRepository.updatePiece(gameId, from, to);
        gameRepository.updateTurn(gameId, TurnEntity.toEntity(game));
    }

}
